<template>
  <div class="parent-issue-board">
    <div
      class="module-board"
      :style="{ 'border-bottom': `3px solid ${moduleColor}` }"
    >
      <h4 :style="{ color: moduleColor }">
        {{ moduleName }}
      </h4>
      <div class="btn-area">
        <button
          :disabled="selectedItems.length === 0"
          class="btn btn-sm btn-danger"
          @click="deleteIssue"
        >
          삭제
        </button>
      </div>
    </div>
    <div class="content">
      <table class="table">
        <colgroup>
          <col :style="{ width: '20px', 'background-color': moduleColor }" />
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
            <th scope="col">
              <input type="checkbox" @click="selectAll($event)" />
            </th>
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
            <th scope="row" class="align-middle">
              <input
                v-model="selectedItems"
                type="checkbox"
                :value="boardItem"
              />
            </th>
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
        <tfoot>
          <tr>
            <td style="background-color: white;"></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td class="text-right">{{ totalDevRemainingWorkingDays }}일</td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
        </tfoot>
      </table>
    </div>
  </div>
</template>

<script>
import qs from 'qs';
import { mapGetters, mapState } from 'vuex';
import { reduce } from 'lodash-es';
import { from } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

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
    moduleId: {
      required: true,
      type: String,
    },
    showInProgress: {
      required: true,
      type: Boolean,
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
    progress() {
      if (this.importedCount == 0 || this.totalCount == 0) {
        return 0;
      }

      return Math.round((this.importedCount / this.totalCount) * 100);
    },
    moduleColor() {
      return `#${this.getModule(this.moduleId).color}`;
    },
    moduleName() {
      const name = this.getModule(this.moduleId).name;
      const idx = name.lastIndexOf(':');
      return name.substr(idx + 1).trim();
    },
    totalDevRemainingWorkingDays() {
      return reduce(
        this.boardItems,
        (sum, item) => sum + item.devDateProgress.remainingWorkingDays,
        0
      );
    },
  },
  watch: {
    showInProgress() {
      this.getParentIssueBoard();
    },
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
    deleteIssue() {
      if (confirm('선택된 이슈를 삭제 하시겠습니까?')) {
        from(this.selectedItems)
          .pipe(
            mergeMap((boardItem) =>
              issueService.deleteIssue$({
                issueId: boardItem.parentIssue.issueId,
              })
            )
          )
          .subscribe({
            complete() {
              alert('선택된 이슈를 삭제 하였습니다.');
            },
            error() {
              alert('선택된 이슈를 삭제 하지 못하였습니다.');
            },
          });
      }
    },
    moveIssue() {
      document
        .querySelector(
          '#main-wrapper > section > section > section > project-contents-layout > project-contents-header > div > project-contents-type-selector > div > button:nth-child(3)'
        )
        .click();
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
    getMemberId(users) {
      const members = users.to.filter((to) => to.type === 'member');
      if (members.length > 0) {
        return members[0].member.organizationMemberId;
      } else {
        return null;
      }
    },
    selectAll(event) {
      if (event.target.checked) {
        this.selectedItems = [...this.boardItems];
      } else {
        this.selectedItems = [];
      }
    },
    getParentIssueBoard() {
      boardService
        .getParentIssueBoard$({
          projectId: this.projectId,
          moduleId: this.moduleId,
          showInProgress: this.showInProgress,
        })
        .subscribe((response) => (this.boardItems = response.items));
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
