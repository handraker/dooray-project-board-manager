<template>
  <div>
    <milestone-parent-issue-board
      v-for="milestone in filteredMilestone"
      :key="milestone.id"
      :milestone="milestone"
    />
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex';

import MilestoneParentIssueBoard from '@/components/board/MilestoneParentIssueBoard.vue';

export default {
  components: { MilestoneParentIssueBoard },
  computed: {
    ...mapState('dooray', ['milestones']),
    ...mapGetters('dooray', ['filteredMilestone']),
  },
};
</script>
