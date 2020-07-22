<template>
  <div>
    <milestone-board
      v-for="milestone in filteredMilestone"
      :key="milestone.id"
      :milestone="milestone"
    />
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex';

import MilestoneBoard from '@/components/board/MilestoneBoard.vue';

export default {
  components: { MilestoneBoard },
  computed: {
    ...mapState('dooray', ['milestones']),
    ...mapGetters('dooray', ['filteredMilestone']),
  },
};
</script>
