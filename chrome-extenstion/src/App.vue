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

import MemberBoardMain from '@/components/board/MemberBoardMain.vue';
import MilestoneBoardMain from '@/components/board/MilestoneBoardMain.vue';
import MilestoneParentIssueBoardMain from '@/components/board/MilestoneParentIssueBoardMain.vue';
import ParentIssueBoardMain from '@/components/board/ParentIssueBoardMain.vue';
import ParentIssueGanntMain from '@/components/board/ParentIssueGanntMain.vue';
import Configuration from '@/components/config/Configuration.vue';

export default {
  data() {
    return {
      selectedTabIdx: 0,
      tabs: [
        {
          title: 'Member Board',
          component: MemberBoardMain,
        },
        {
          title: 'Mileston Parent Issue Board',
          component: MilestoneParentIssueBoardMain,
        },
        {
          title: 'Parent Issue Board',
          component: ParentIssueBoardMain,
        },
        {
          title: 'Parent Issue Gannt',
          component: ParentIssueGanntMain,
        },
        {
          title: 'Milestone Board',
          component: MilestoneBoardMain,
        },
        {
          title: 'Configuration',
          component: Configuration,
        },
      ],
      tabContentHeight: 0,
      projectId: '',
      intervalId: null,
    };
  },
  computed: {
    selectedTab() {
      return this.tabs[this.selectedTabIdx];
    },
  },
  watch: {
    projectId: {
      immediate: true,
      async handler(projectId) {
        if (projectId === '') {
          return;
        }

        await this.getProject(projectId);

        this.getTags();
        this.getWorkflows();
        this.getMilestones();
        this.getMembers();
      },
    },
  },
  created() {
    this.intervalId = setInterval(() => {
      const pathname = window.location.pathname;
      const lastIndex = pathname.lastIndexOf('/project/');
      this.projectId = pathname.substr(lastIndex + 9, 19);
    }, 1000);
  },
  destroyed() {
    clearInterval(this.intervalId);
  },
  methods: {
    ...mapActions('dooray', [
      'getTags',
      'getWorkflows',
      'getMilestones',
      'getMembers',
    ]),
    ...mapActions('project', ['getProject']),
    selectTab(idx) {
      this.selectedTabIdx = idx;
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
