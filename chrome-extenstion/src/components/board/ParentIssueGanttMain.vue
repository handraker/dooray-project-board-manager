<template>
  <div>
    <div style="margin-top: 10px;">
      <div
        class="form-check form-check-inline align-middle"
        style="margin-left: 5px;"
      >
        <input
          id="viewMode_EDIT"
          v-model="editable"
          class="form-check-input"
          type="radio"
          name="editable"
          :value="true"
        />
        <label class="form-check-label" for="viewMode_EDIT">편집모드</label>
      </div>
      <div class="form-check form-check-inline align-middle">
        <input
          id="viewMode_READ"
          v-model="editable"
          class="form-check-input"
          type="radio"
          name="editable"
          :value="false"
        />
        <label class="form-check-label" for="viewMode_READ">읽기모드</label>
      </div>
    </div>
    <parent-issue-gantt
      v-for="module in modules"
      :key="module.id"
      :module-id="module.id"
      :editable="editable"
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
    return {
      editable: true,
    };
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
