<template>
  <div
    v-b-popover.hover.top="detailText"
    class="progress-bar"
    role="progressbar"
    :style="{
      ...style,
      width: `${ratio + 1}%`,
    }"
    :aria-valuenow="ratio"
    aria-valuemin="0"
    aria-valuemax="100"
  >
    <a v-if="showDetail" @click.prevent="click">{{ detailText }}</a>
    <a v-else @click.prevent="click">{{ value }}{{ postfix }}</a>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  props: {
    workflowId: {
      required: true,
      type: String,
    },
    value: {
      required: true,
      type: Number,
    },
    ratio: {
      required: true,
      type: Number,
    },
    postfix: {
      required: true,
      type: String,
    },
    showDetail: {
      required: false,
      type: Boolean,
      _default: false,
    },
  },
  computed: {
    ...mapState('project', ['projectId']),
    ...mapState('dooray', ['workflows']),
    workflow() {
      return this.workflows.find((workflow) => this.workflowId === workflow.id);
    },
    detailText() {
      return `${this.workflow?.name} - ${this.value}${this.postfix} (${this.ratio}%)`;
    },
    style() {
      switch (this.workflow?.class) {
        case 'backlog':
          return {
            'background-color': '#d35400',
            color: 'white',
          };
        case 'registered':
          return {
            'background-color': '#27ae60',
            color: 'white',
          };
        case 'working':
          return {
            'background-color': '#2980b9',
            color: 'white',
          };
        case 'closed':
        default:
          return {
            'background-color': '#bdc3c7',
            color: 'black',
          };
      }
    },
  },
  methods: {
    click() {
      this.$emit('workflowClick', this.workflowId);
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>

<style scoped>
.progress-bar a {
  color: inherit;
}
</style>
