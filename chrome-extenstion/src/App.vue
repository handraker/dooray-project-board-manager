<template>
  <div class="board">
    <ul class="nav nav-tabs">
      <li v-for="(tab, idx) in tabs" :key="idx" class="nav-item">
        <a
          class="nav-link"
          :class="{ active: idx === selectedTabIdx }"
          href
          @click.prevent="selectTab(idx)"
          >{{ tab.title }}</a
        >
      </li>
    </ul>
    <div class="tab-content">
      <component :is="selectedTab.component" />
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

import ParentIssueBoard from '@/components/board/ParentIssueBoard.vue';
import Configuration from '@/components/config/Configuration.vue';

export default {
  data() {
    return {
      selectedTabIdx: 0,
      tabs: [
        {
          title: 'Parent Issue Board',
          component: ParentIssueBoard,
        },
        {
          title: 'Configuration',
          component: Configuration,
        },
      ],
      tabContentHeight: 0,
    };
  },
  computed: {
    selectedTab() {
      return this.tabs[this.selectedTabIdx];
    },
  },
  async mounted() {
    const pathname = window.location.pathname;
    const lastIndex = pathname.lastIndexOf('/project/');
    const projectId = pathname.substr(lastIndex + 9, 19);
    await this.getProject(projectId);

    this.getTags();
    this.getWorkflows();
    this.getMilestones();
  },
  methods: {
    ...mapActions('dooray', ['getTags', 'getWorkflows', 'getMilestones']),
    ...mapActions('project', ['getProject']),
    selectTab(idx) {
      this.selectedTabIdx = idx;
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
