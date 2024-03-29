import moment from 'moment';
import { from } from 'rxjs';
import { map, reduce } from 'rxjs/operators';

import { getContrast } from '@/common/utils';
import { doorayService } from '@/service/dooray-service';

const getDefaultState = () => {
  return {
    tags: {},
    tagPrefixs: {},
    workflows: {},
    milestones: [],
    members: [],
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
    state.workflows = workflows;
  },
  setMilestones(state, { milestones }) {
    state.milestones = milestones;
  },
  setMembers(state, { members, organizationMemberMap }) {
    state.members = members.map(
      (member) => organizationMemberMap[member.organizationMemberId]
    );
  },
};

const getters = {
  getTagStyle: (state) => (tagId) => {
    const tag = state.tags[tagId];
    return {
      'background-color': `#${tag.color}`,
      'border-color': `#${tag.color}`,
      color: getContrast(tag.color),
    };
  },
  filteredMilestone: (state) => {
    let milestones = state.milestones.filter(
      (milestone) => milestone.status == 'open'
    );
    milestones.sort((l, r) => {
      if (l.startedAt == null) {
        return 1;
      }

      if (r.startedAt == null) {
        return -1;
      }

      if (moment(l.startedAt).isBefore(moment(r.startedAt))) {
        return -1;
      } else {
        return 1;
      }
    });
    return milestones;
  },
  getMilestone: (state) => (id) => {
    if (id == null) {
      return { name: '' };
    }

    const milestone = state.milestones.find((milestone) => milestone.id === id);
    if (milestone === undefined) {
      return { name: '' };
    }

    return milestone;
  },
  getNotClosedWorkflowIds: (state) => {
    return state.workflows
      .filter((workflow) => workflow.class != 'closed')
      .map((workflow) => workflow.id);
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
  getMembers({ rootState, commit }) {
    doorayService
      .getMembers$({ projectId: rootState.project.projectId })
      .subscribe((response) =>
        commit('setMembers', {
          members: response.result.contents,
          organizationMemberMap:
            response.result.references.organizationMemberMap,
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
