import { from } from 'rxjs';
import { filter, tap, toArray } from 'rxjs/operators';

import { projectService } from '@/service/project-service';

const getDefaultState = () => {
  return {
    projectId: '',
    doorayModuleTagPrefixId: '',
    doorayWorkingTypeTagPrefixId: '',
    doorayMandaysTagPrefixId: '',
    department: '',
    modules: [],
    projectMilestones: [],
  };
};

const state = getDefaultState();

const mutations = {
  setProject(
    state,
    {
      projectId,
      doorayModuleTagPrefixId,
      doorayWorkingTypeTagPrefixId,
      doorayMandaysTagPrefixId,
      department,
    }
  ) {
    state.projectId = projectId;
    state.doorayModuleTagPrefixId = doorayModuleTagPrefixId;
    state.doorayWorkingTypeTagPrefixId = doorayWorkingTypeTagPrefixId;
    state.doorayMandaysTagPrefixId = doorayMandaysTagPrefixId;
    state.department = department;
  },
  setModules(state, { tags }) {
    from(tags)
      .pipe(
        filter((tag) => tag.tagPrefixId === state.doorayModuleTagPrefixId),
        toArray()
      )
      .subscribe((tags) => (state.modules = tags));
  },
  setProjectMilestones(state, projectMilestones) {
    state.projectMilestones = projectMilestones;
  },
};

const getters = {
  getModule: (state) => (moduleId) => {
    const module = state.modules.find((module) => module.id === moduleId);
    if (module === undefined) {
      return {};
    } else {
      return module;
    }
  },
  getModuleId: (state, getters, rootState) => (tagIdList) => {
    const moduleTags = tagIdList
      .filter((id) => rootState.dooray.tags[id] !== undefined)
      .map((id) => rootState.dooray.tags[id])
      .filter((tag) => tag.tagPrefixId === state.doorayModuleTagPrefixId)
      .map((tag) => tag.id);
    if (moduleTags === undefined) {
      return null;
    }

    return moduleTags[0];
  },
  getWorkingTypeId: (state, getters, rootState) => (tagIdList) => {
    const moduleTags = tagIdList
      .filter((id) => rootState.dooray.tags[id] !== undefined)
      .map((id) => rootState.dooray.tags[id])
      .filter((tag) => tag.tagPrefixId === state.doorayWorkingTypeTagPrefixId)
      .map((tag) => tag.id);
    if (moduleTags === undefined) {
      return null;
    }

    return moduleTags[0];
  },
  getMandays: (state, getters, rootState) => (tagIdList) => {
    const moduleTags = tagIdList
      .filter((id) => rootState.dooray.tags[id] !== undefined)
      .map((id) => rootState.dooray.tags[id])
      .filter((tag) => tag.tagPrefixId === state.doorayMandaysTagPrefixId);
    if (moduleTags === undefined) {
      return 0;
    }

    if (moduleTags.length === 0) {
      return 0;
    }

    const name = moduleTags[0].name;
    const idx = name.lastIndexOf(':');
    const mandays = name.substr(idx + 1).trim();
    if (isNaN(mandays)) {
      return 0;
    } else {
      return mandays;
    }
  },
  projectMembers: (state, getters, rootState) => {
    let projectMembers = rootState.dooray.members.filter(
      (member) => member.department === state.department
    );

    function getOrder(member) {
      switch (member.position) {
        case '책임':
          return 10;
        case '선임':
          return 100;
        case '전임':
          return 1000;
        case '사원':
        default:
          return 10000;
      }
    }

    projectMembers.sort((l, r) => {
      const lo = getOrder(l);
      const ro = getOrder(r);

      if (lo == ro) {
        return l.idProviderUserId - r.idProviderUserId;
      } else {
        return lo - ro;
      }
    });

    return projectMembers;
  },
};

const actions = {
  getProject({ commit }, projectId) {
    return projectService
      .getProject$({ projectId })
      .pipe(tap((project) => commit('setProject', project)))
      .toPromise();
  },

  getProjectMilestones({ commit }, projectId) {
    return projectService
      .getProjectMilestones$({ projectId })
      .pipe(
        tap((projectMilestones) =>
          commit('setProjectMilestones', projectMilestones)
        )
      )
      .toPromise();
  },
};

export const projectStore = {
  state,
  mutations,
  getters,
  actions,
};
