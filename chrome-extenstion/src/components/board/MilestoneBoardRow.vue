<template>
  <tr v-if="countStatistics.items.length > 0">
    <td>
      <div v-if="module" class="inline-box" title="태그">
        <span class="tag-group">
          <span class="tag-badge" :style="getTagStyle(module)">{{
            moduleName
          }}</span>
        </span>
      </div>
    </td>
    <td>
      <issue-progress-bar
        :total-value="mandayStatistics.totalValue"
        :items="mandayStatistics.items"
        postfix="MD"
        @workflowClick="searchIssue"
      />
      <div class="mt-1" />
      <issue-progress-bar
        :total-value="countStatistics.totalValue"
        :items="countStatistics.items"
        postfix="개"
        @workflowClick="searchIssue"
      />
    </td>
    <td v-for="workflow in workflows" :key="workflow.id" class="text-right">
      <span
        v-if="isExists(workflow.id)"
        class="workflow"
        :style="workflowStyle(workflow)"
      >
        <div>
          {{ `${getMandays(workflow.id)}` }}<span class="unit">MD</span>
        </div>
        <div>{{ `${getCount(workflow.id)}` }}<span class="unit">개</span></div>
      </span>
    </td>
  </tr>
</template>

<script>
import qs from 'qs';
import { mapState } from 'vuex';

import { getContrast } from '@/common/utils';
import { boardService } from '@/service/board-service';
import IssueProgressBar from '@/components/progress-bar/IssueProgressBar.vue';

export default {
  components: { IssueProgressBar },
  props: {
    milestoneId: {
      required: true,
      type: String,
    },
    module: {
      required: false,
      type: Object,
      default: null,
    },
  },
  data() {
    return {
      mandayStatistics: {
        totalValue: 0,
        items: [],
      },
      countStatistics: {
        totalValue: 0,
        items: [],
      },
    };
  },
  computed: {
    ...mapState('project', ['projectId']),
    ...mapState('dooray', ['workflows']),
    moduleName() {
      const name = this.module.name;
      const idx = name.lastIndexOf(':');
      return name.substr(idx + 1).trim();
    },
    moduleColor() {
      return `#${this.module.color}`;
    },
  },
  mounted() {
    const moduleId = this.module != null ? this.module.id : null;

    boardService
      .getMilestoneBoard$({
        projectId: this.projectId,
        milestoneId: this.milestoneId,
        moduleId,
      })
      .subscribe((response) => {
        this.mandayStatistics = response.mandayStatistics;
        this.countStatistics = response.countStatistics;
      });
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
    searchIssue(workflowId) {
      const params = qs.stringify(
        {
          workflowIds: [workflowId],
          tags: ['and', this.module != null ? this.module.id : ''],
          milestone: this.milestoneId,
          hasParent: true,
        },
        { arrayFormat: 'comma' }
      );
      history.pushState(null, null, `/project/${this.projectId}?${params}`);
      document
        .querySelector(
          '#main-wrapper > section > section > section > project-contents-layout > project-contents-header > div > project-contents-type-selector > div > button:nth-child(3)'
        )
        .click();
    },
    getTagStyle(tag) {
      return {
        'background-color': `#${tag.color}`,
        'border-color': `#${tag.color}`,
        color: getContrast(tag.color),
      };
    },
    isExists(workflowId) {
      return (
        this.mandayStatistics.items.find(
          (item) => item.workflowId == workflowId
        ) !== undefined
      );
    },
    getMandays(workflowId) {
      return this.mandayStatistics.items.find(
        (item) => item.workflowId == workflowId
      )?.value;
    },
    getCount(workflowId) {
      return this.countStatistics.items.find(
        (item) => item.workflowId == workflowId
      )?.value;
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>

<style scoped>
.milestone-board .workflow {
  font-weight: bold;
}

.milestone-board .unit {
  width: 20px;
  display: inline-block;
  text-align: left;
  margin-left: 3px;
}
</style>
