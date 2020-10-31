<template>
  <div>
    <parent-issue-gantt
      v-for="module in modules"
      :key="module.id"
      :module-id="module.id"
    />
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex';

import ParentIssueGantt from '@/components/board/ParentIssueGantt';

export default {
  components: {
    ParentIssueGantt,
  },
  data() {
    return {};
  },
  computed: {
    ...mapState('project', ['projectId', 'modules']),
    ...mapGetters('project', ['getModule']),
  },
  created() {},
  methods: {},
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
