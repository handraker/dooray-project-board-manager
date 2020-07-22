<template>
  <div>
    <parent-issue-board
      v-for="module in modules"
      :key="module.id"
      :module-id="module.id"
    />
  </div>
</template>

<script>
import { mapState } from 'vuex';

import ParentIssueBoard from '@/components/board/ParentIssueBoard.vue';

export default {
  components: { ParentIssueBoard },
  computed: {
    ...mapState('project', ['projectId', 'modules']),
  },
  methods: {},
};
</script>

<style></style>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
