<template>
  <div class="milestone-board jumbotron">
    <h1 class="display-4">{{ milestone.name }}</h1>
    <p v-if="milestone.startedAt" class="lead">
      {{ milestoneRange }}
      <date-progress-bar
        class="mt-2"
        :from="from"
        :to="to"
        :total-working-days="totalWorkingDays"
        :remaining-working-days="remainingWorkingDays"
        :disabled="true"
      />
    </p>
    <hr class="my-4" />
    <div class="content">
      <table class="table">
        <colgroup>
          <col style="width: 300px;" />
          <col />
          <col
            v-for="workflow in workflows"
            :key="workflow.id"
            style="width: 150px;"
          />
        </colgroup>
        <thead>
          <tr>
            <th>모듈</th>
            <th>하위업무</th>
            <th
              v-for="workflow in workflows"
              :key="workflow.id"
              :style="workflowStyle(workflow)"
              class="text-right"
            >
              {{ workflow.name }}
            </th>
          </tr>
        </thead>
        <tbody>
          <milestone-board-row
            v-for="module in modules"
            :key="module.id"
            :milestone-id="milestone.id"
            :module="module"
          />
          <milestone-board-row :milestone-id="milestone.id" />
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import moment from 'moment';

import { getWorkingDays } from '@/common/utils';
import MilestoneBoardRow from '@/components/board/MilestoneBoardRow.vue';
import DateProgressBar from '@/components/progress-bar/DateProgressBar.vue';

export default {
  components: { MilestoneBoardRow, DateProgressBar },
  props: {
    milestone: {
      required: true,
      type: Object,
    },
  },
  computed: {
    ...mapState('project', ['modules']),
    ...mapState('dooray', ['workflows']),
    milestoneRange() {
      return `${moment(this.milestone.startedAt).format(
        'YYYY년 MM월 DD일'
      )} ~ ${moment(this.milestone.endedAt).format('YYYY년 MM월 DD일')}`;
    },
    from() {
      return moment(this.milestone.startedAt).format('YYYY-MM-DD');
    },
    to() {
      return moment(this.milestone.endedAt).format('YYYY-MM-DD');
    },
    totalWorkingDays() {
      return getWorkingDays(this.from, this.to);
    },
    remainingWorkingDays() {
      return getWorkingDays(moment(), this.to);
    },
  },
  methods: {
    workflowStyle(workflow) {
      switch (workflow.class) {
        case 'backlog':
          return {
            color: '#d35400',
          };
        case 'registered':
          return {
            color: '#27ae60',
          };
        case 'working':
          return {
            color: '#2980b9',
          };
        case 'closed':
        default:
          return {
            color: '#bdc3c7',
          };
      }
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
