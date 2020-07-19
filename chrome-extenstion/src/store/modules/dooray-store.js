import { from } from 'rxjs';
import { map, reduce } from 'rxjs/operators';

import { doorayService } from '@/service/dooray-service';

const getDefaultState = () => {
  return {
    tags: {},
    tagPrefixs: {},
    workflows: {},
    milestones: {},
  };
};

const state = getDefaultState();

const mutations = {
  setTags(state, { tagPrefixMap, tags }) {
    state.tagPrefixs = tagPrefixMap;
    from(tags)
      .pipe(
        map((tag) => {
          return {
            ...tag,
            prefix: tagPrefixMap[tag.tagPrefixId],
          };
        }),
        reduce((acc, value) => {
          return {
            ...acc,
            [value.id]: value,
          };
        }, {})
      )
      .subscribe((tags) => (state.tags = tags));
  },
  setWorkflows(state, { workflows }) {
    from(workflows)
      .pipe(
        reduce((acc, value) => {
          return {
            ...acc,
            [value.id]: value,
          };
        }, {})
      )
      .subscribe((workflows) => (state.workflows = workflows));
    console.log(state);
  },
  setMilestones(state, { milestones }) {
    from(milestones)
      .pipe(
        reduce((acc, value) => {
          return {
            ...acc,
            [value.id]: value,
          };
        }, {})
      )
      .subscribe((milestones) => (state.milestones = milestones));
    console.log(state);
  },
};

const getters = {
  getMilestone: (state) => (id) => {
    const milestone = state.milestones[id];
    if (milestone === undefined) {
      return {};
    } else {
      return milestone;
    }
  },
  getNotClosedWorkflowIds: (state) => {
    return Object.keys(state.workflows).filter(
      (id) => state.workflows[id].class != 'closed'
    );
  },
};

const actions = {
  getTags({ rootState, commit }) {
    doorayService
      .getTags$({ projectId: rootState.project.projectId })
      .subscribe((response) => {
        commit('setTags', {
          tagPrefixMap: response.result.references.tagPrefixMap,
          tags: response.result.contents,
        });
        commit(
          'project/setModules',
          { tags: response.result.contents },
          { root: true }
        );
      });
  },
  getWorkflows({ rootState, commit }) {
    doorayService
      .getWorkflows$({ projectId: rootState.project.projectId })
      .subscribe((response) =>
        commit('setWorkflows', {
          workflows: response.result.contents,
        })
      );
  },
  getMilestones({ rootState, commit }) {
    doorayService
      .getMilestones$({ projectId: rootState.project.projectId })
      .subscribe((response) =>
        commit('setMilestones', {
          milestones: response.result.contents,
        })
      );
  },
};

export const doorayStore = {
  state,
  mutations,
  getters,
  actions,
};
