<template>
  <div>
    <parent-issue-gannt
      v-for="module in modules"
      :key="module.id"
      :module-id="module.id"
    />
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex';

import ParentIssueGannt from '@/components/board/ParentIssueGannt';

export default {
  components: {
    ParentIssueGannt,
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
