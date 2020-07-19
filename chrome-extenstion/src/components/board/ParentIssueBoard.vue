<template>
  <div>
    <parent-issue-module-board
      v-for="module in modules"
      :key="module.id"
      :module-id="module.id"
    />
  </div>
</template>

<script>
import { mapState } from 'vuex';

import ParentIssueModuleBoard from '@/components/board/ParentIssueModuleBoard.vue';

export default {
  components: { ParentIssueModuleBoard },
  computed: {
    ...mapState('project', ['projectId', 'modules']),
  },
  methods: {},
};
</script>

<style></style>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
