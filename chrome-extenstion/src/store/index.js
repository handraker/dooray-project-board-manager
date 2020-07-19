import Vue from 'vue';
import Vuex from 'vuex';

import { doorayStore } from '@/store/modules/dooray-store';
import { projectStore } from '@/store/modules/project-store';

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    dooray: {
      ...doorayStore,
      namespaced: true,
    },
    project: {
      ...projectStore,
      namespaced: true,
    },
  },
});

export default store;
