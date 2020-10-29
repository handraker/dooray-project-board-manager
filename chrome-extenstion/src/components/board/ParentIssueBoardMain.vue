<template>
  <div>
    <div style="margin-top: 10px;">
      <div
        class="form-check form-check-inline align-middle"
        style="margin-left: 5px;"
      >
        <input
          id="showInProgress_TRUE"
          v-model="showInProgress"
          class="form-check-input"
          type="radio"
          name="showInProgress"
          :value="true"
        />
        <label class="form-check-label" for="showInProgress_TRUE"
          >진행 중인 이슈만 보기</label
        >
      </div>
      <div class="form-check form-check-inline align-middle">
        <input
          id="showInProgress_FALSE"
          v-model="showInProgress"
          class="form-check-input"
          type="radio"
          name="showInProgress"
          :value="false"
        />
        <label class="form-check-label" for="showInProgress_FALSE"
          >모든 이슈 보기</label
        >
      </div>
    </div>
    <parent-issue-board
      v-for="module in modules"
      :key="module.id"
      :module-id="module.id"
      :show-in-progress="showInProgress"
    />
  </div>
</template>

<script>
import { mapState } from 'vuex';

import ParentIssueBoard from '@/components/board/ParentIssueBoard.vue';

export default {
  components: { ParentIssueBoard },
  data() {
    return {
      showInProgress: true,
    };
  },
  computed: {
    ...mapState('project', ['projectId', 'modules']),
  },
  methods: {},
};
</script>

<style></style>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
