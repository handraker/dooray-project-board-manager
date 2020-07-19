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
    {{ value }}{{ postfix }}
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
  },
  computed: {
    ...mapState('dooray', ['workflows']),
    detailText() {
      return `${this.workflows[this.workflowId]?.name} - ${this.value}${
        this.postfix
      } (${this.ratio}%)`;
    },
    style() {
      switch (this.workflows[this.workflowId]?.class) {
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
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
