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
      <issue-progress-bar
        v-if="totalMandayStatistics != null"
        :total-value="totalMandayStatistics.totalValue"
        :items="totalMandayStatistics.items"
        :show-detail="true"
        postfix="MD"
        @workflowClick="searchIssue($event, boardItem)"
      />
      <issue-progress-bar
        v-if="totalCountStatistics != null"
        :total-value="totalCountStatistics.totalValue"
        :items="totalCountStatistics.items"
        :show-detail="true"
        postfix="개"
        @workflowClick="searchIssue($event, boardItem)"
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
            <th scope="col">모듈</th>
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
                :href="`/project/${projectId}/${boardItem.parentIssue.issueId}`"
                @click="moveIssue"
                >{{ boardItem.parentIssue.title }}</a
              >
              <button
                type="button"
                class="d-toolbar-white-icon-btn open-new-popup-btn ng-scope"
                tooltip-class="icon-tooltip in-button"
                @click="openIssuePopup(boardItem.parentIssue.issueId)"
              >
                <span class="v2-icons-open-new"></span>
              </button>
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
import { mapGetters, mapState } from 'vuex';
import { reduce } from 'lodash-es';
import { from } from 'rxjs';
import { concatMap, mergeMap, toArray } from 'rxjs/operators';

import { getContrast, getWorkingDays } from '@/common/utils';
import { boardService } from '@/service/board-service';
import { issueService } from '@/service/issue-service';
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
      totalMandayStatistics: null,
      totalCountStatistics: null,
      importedCount: 0,
      totalCount: 0,
    };
  },
  computed: {
    ...mapState('project', ['projectId', 'modules']),
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
    moveIssue() {
      document
        .querySelector(
          '#main-wrapper > section > section > section > project-contents-layout > project-contents-header > div > project-contents-type-selector > div > button:nth-child(3)'
        )
        .click();
    },
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
        .getMilestoneBoard$({
          projectId: this.projectId,
          milestoneId: this.milestone.id,
        })
        .subscribe((response) => {
          this.totalMandayStatistics = response.mandayStatistics;
          this.totalCountStatistics = response.countStatistics;
        });

      from(this.modules)
        .pipe(
          concatMap((module) =>
            boardService.getParentIssueBoard$({
              projectId: this.projectId,
              milestoneId: this.milestone.id,
              moduleId: module.id,
            })
          ),
          mergeMap((response) => response.items),
          toArray()
        )
        .subscribe((items) => (this.boardItems = items));
    },
    changeDevStatusCode(devStatusCode, parentIssue) {
      issueService
        .modifyProgress$({
          ...parentIssue,
          devStatusCode,
        })
        .subscribe(() => this.getParentIssueBoard());
    },
    changeDeployStatusCode(deployStatusCode, parentIssue) {
      issueService
        .modifyProgress$({
          ...parentIssue,
          deployStatusCode,
        })
        .subscribe(() => this.getParentIssueBoard());
    },
    changeDevDate(date, parentIssue) {
      issueService
        .modifyProgress$({
          ...parentIssue,
          devStartDate: date.from,
          devEndDate: date.to,
        })
        .subscribe(() => this.getParentIssueBoard());
    },
    changeDeployDate(date, parentIssue) {
      issueService
        .modifyProgress$({
          ...parentIssue,
          deployStartDate: date.from,
          deployEndDate: date.to,
        })
        .subscribe(() => this.getParentIssueBoard());
    },
    openIssuePopup(issueId) {
      const width = 800;
      const height = 800;
      const left = screen.width / 2 - width / 2;
      const top = screen.height / 2 - height / 2;

      window.open(
        `/popup/project/posts/${issueId}`,
        '_blank',
        `width=${width},height=${height},left=${left},top=${top}`
      );
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>

<style scoped>
.board .table .d-toolbar-white-icon-btn {
  padding-left: 5px;
  padding-right: 5px;
  height: 20px;
}
</style>
