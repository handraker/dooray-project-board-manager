import { from } from 'rxjs';
import { tap, toArray, filter } from 'rxjs/operators';

import { projectService } from '@/service/project-service';

const getDefaultState = () => {
  return {
    projectId: '',
    doorayModuleTagPrefixId: '',
    doorayWorkingTypeTagPrefixId: '',
    doorayMandaysTagPrefixId: '',
    modules: [],
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
    }
  ) {
    state.projectId = projectId;
    state.doorayModuleTagPrefixId = doorayModuleTagPrefixId;
    state.doorayWorkingTypeTagPrefixId = doorayWorkingTypeTagPrefixId;
    state.doorayMandaysTagPrefixId = doorayMandaysTagPrefixId;
  },
  setModules(state, { tags }) {
    from(tags)
      .pipe(
        filter((tag) => tag.tagPrefixId === state.doorayModuleTagPrefixId),
        toArray()
      )
      .subscribe((tags) => (state.modules = tags));
    console.log(state);
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
};

const actions = {
  getProject({ commit }, projectId) {
    return projectService
      .getProject$({ projectId })
      .pipe(tap((project) => commit('setProject', project)))
      .toPromise();
  },
};

export const projectStore = {
  state,
  mutations,
  getters,
  actions,
};
