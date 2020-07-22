<template>
  <div class="issue-progress">
    <div class="progress">
      <issue-progress-bar-item
        v-for="item in items"
        :key="item.workflowId"
        :workflow-id="item.workflowId"
        :value="item.value"
        :ratio="item.ratio"
        :postfix="postfix"
        @workflowClick="workflowClick"
      />
    </div>
  </div>
</template>

<script>
import IssueProgressBarItem from '@/components/progress-bar/IssueProgressBarItem.vue';

export default {
  components: { IssueProgressBarItem },
  props: {
    totalValue: {
      required: true,
      type: Number,
    },
    items: {
      required: true,
      type: Array,
    },
    postfix: {
      required: true,
      type: String,
    },
  },
  methods: {
    workflowClick(workflowId) {
      this.$emit('workflowClick', workflowId);
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>

<style>
.issue-progress {
  min-width: 200px;
  font-family: 'Nanum Gothic', Meiryo, 'Noto Sans JP', sans-serif,
    'Lucida Sans Unicode', arial;
}
.issue-progress * {
  font-size: 12px !important;
}
</style>
