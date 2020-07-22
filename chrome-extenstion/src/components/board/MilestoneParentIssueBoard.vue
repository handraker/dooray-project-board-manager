<template>
  <div class="milestone-board module-board jumbotron">
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
          <col />
          <col />
          <col style="width: 230px;" />
          <col style="width: 100px;" />
          <col style="width: 230px;" />
          <col style="width: 75px;" />
          <col style="width: 110px;" />
          <col style="width: 230px;" />
          <col style="width: 150px;" />
        </colgroup>
        <thead>
          <tr>
            <th scope="col">상위 업무</th>
            <th scope="col" class="text-center">하위 업무</th>
            <th scope="col" class="text-center">개발 상태</th>
            <th scope="col" class="text-center">개발 기한</th>
            <th scope="col" class="text-right">남은 작업일</th>
            <th scope="col" class="text-center">배포 상태</th>
            <th scope="col" class="text-center">배포 기한</th>
            <th scope="col">마일스톤</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="boardItem in boardItems" :key="boardItem.id">
            <td>
              <div class="inline-box" title="태그">
                <span class="tag-group">
                  <span
                    class="tag-badge"
                    :style="
                      getTagStyle(getModule(boardItem.parentIssue.moduleId))
                    "
                    >{{ getModuleName(boardItem.parentIssue.moduleId) }}</span
                  >
                </span>
              </div>
            </td>
            <td>
              <a
                :href="`/project/${projectId}/${boardItem.parentIssue.parentIssueId}`"
                >{{ boardItem.parentIssue.title }}</a
              >
            </td>
            <td>
              <issue-progress-bar
                :total-value="boardItem.mandayStatistics.totalValue"
                :items="boardItem.mandayStatistics.items"
                postfix="MD"
                @workflowClick="searchIssue($event, boardItem)"
              />
              <div class="mt-1" />
              <issue-progress-bar
                :total-value="boardItem.countStatistics.totalValue"
                :items="boardItem.countStatistics.items"
                postfix="개"
                @workflowClick="searchIssue($event, boardItem)"
              />
            </td>
            <dev-status-box
              :status="boardItem.parentIssue.devStatusCode"
              @change="changeDevStatusCode($event, boardItem.parentIssue)"
            />
            <td>
              <date-progress-bar
                :from="boardItem.devDateProgress.from"
                :to="boardItem.devDateProgress.to"
                :total-working-days="boardItem.devDateProgress.totalWorkingDays"
                :remaining-working-days="
                  boardItem.devDateProgress.remainingWorkingDays
                "
                @change="changeDevDate($event, boardItem.parentIssue)"
              />
            </td>
            <td class="text-right">
              {{ boardItem.devDateProgress.remainingWorkingDays }}일
            </td>
            <deploy-status-box
              :status="boardItem.parentIssue.deployStatusCode"
              @change="changeDeployStatusCode($event, boardItem.parentIssue)"
            />
            <td>
              <date-progress-bar
                :from="boardItem.deployDateProgress.from"
                :to="boardItem.deployDateProgress.to"
                :total-working-days="
                  boardItem.deployDateProgress.totalWorkingDays
                "
                :remaining-working-days="
                  boardItem.deployDateProgress.remainingWorkingDays
                "
                @change="changeDeployDate($event, boardItem.parentIssue)"
              />
            </td>
            <td>{{ getMilestone(boardItem.parentIssue.milestoneId).name }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import qs from 'qs';
import moment from 'moment';
import { mapState, mapGetters } from 'vuex';
import { reduce } from 'lodash-es';

import { getContrast, getWorkingDays } from '@/common/utils';
import { boardService } from '@/service/board-service';
import { parentIssueService } from '@/service/parent-issue-service';
import DateProgressBar from '@/components/progress-bar/DateProgressBar.vue';
import IssueProgressBar from '@/components/progress-bar/IssueProgressBar.vue';
import DevStatusBox from '@/components/status/DevStatusBox.vue';
import DeployStatusBox from '@/components/status/DeployStatusBox.vue';

export default {
  components: {
    DateProgressBar,
    IssueProgressBar,
    DevStatusBox,
    DeployStatusBox,
  },
  props: {
    milestone: {
      required: true,
      type: Object,
    },
  },
  data() {
    return {
      selectedItems: [],
      boardItems: [],
      importedCount: 0,
      totalCount: 0,
    };
  },
  computed: {
    ...mapState('project', ['projectId']),
    ...mapGetters('project', [
      'getModule',
      'getModuleId',
      'getWorkingTypeId',
      'getMandays',
    ]),
    ...mapState('dooray', ['tags', 'milestones']),
    ...mapGetters('dooray', ['getMilestone']),
    milestoneRange() {
      return `${moment(this.milestone.startedAt).format(
        'YYYY년 MM월 DD일'
      )} ~ ${moment(this.milestone.endedAt).format('YYYY년 MM월 DD일')}`;
    },
    progress() {
      if (this.importedCount == 0 || this.totalCount == 0) {
        return 0;
      }

      return Math.round((this.importedCount / this.totalCount) * 100);
    },
    moduleColor() {
      return `#${this.getModule(this.moduleId).color}`;
    },
    totalDevRemainingWorkingDays() {
      return reduce(
        this.boardItems,
        (sum, item) => sum + item.devDateProgress.remainingWorkingDays,
        0
      );
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
  watch: {
    projectId: {
      immediate: true,
      handler(projectId) {
        if (projectId === '') {
          return;
        }

        this.getParentIssueBoard();
      },
    },
  },
  methods: {
    getModuleName(moduleId) {
      const name = this.getModule(moduleId).name;
      const idx = name.lastIndexOf(':');
      return name.substr(idx + 1).trim();
    },
    getTagStyle(tag) {
      return {
        'background-color': `#${tag.color}`,
        'border-color': `#${tag.color}`,
        color: getContrast(tag.color),
      };
    },
    searchIssue(workflowId, boardItem) {
      const params = qs.stringify(
        {
          workflowIds: [workflowId],
          tags: ['and', boardItem.parentIssue.moduleId],
          milestone: boardItem.parentIssue.milestoneId,
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
    getParentIssueBoard() {
      boardService
        .getParentIssueBoard$({
          projectId: this.projectId,
          milestoneId: this.milestone.id,
        })
        .subscribe((response) => (this.boardItems = response.items));
    },
    changeDevStatusCode(devStatusCode, parentIssue) {
      parentIssueService
        .modify$({
          ...parentIssue,
          devStatusCode,
        })
        .subscribe(() => this.getParentIssueBoard());
    },
    changeDeployStatusCode(deployStatusCode, parentIssue) {
      parentIssueService
        .modify$({
          ...parentIssue,
          deployStatusCode,
        })
        .subscribe(() => this.getParentIssueBoard());
    },
    changeDevDate(date, parentIssue) {
      parentIssueService
        .modify$({
          ...parentIssue,
          devStartDate: date.from,
          devEndDate: date.to,
        })
        .subscribe(() => this.getParentIssueBoard());
    },
    changeDeployDate(date, parentIssue) {
      parentIssueService
        .modify$({
          ...parentIssue,
          deployStartDate: date.from,
          deployEndDate: date.to,
        })
        .subscribe(() => this.getParentIssueBoard());
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
