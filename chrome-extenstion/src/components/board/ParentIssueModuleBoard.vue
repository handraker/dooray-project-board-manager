<template>
  <div>
    <div
      class="module-board"
      :style="{ 'border-bottom': `3px solid ${moduleColor}` }"
    >
      <h4 :style="{ color: moduleColor }">
        {{ moduleName }}
      </h4>
      <div class="btn-area">
        <div class="progress">
          <div
            class="progress-bar"
            role="progressbar"
            :style="{ width: `${progress}%` }"
            :aria-valuenow="importedCount"
            aria-valuemin="0"
            :aria-valuemax="totalCount"
          ></div>
          <div class="progress-bar-title">{{ progress }}%</div>
        </div>
        <button
          :disabled="selectedItems.length === 0"
          class="btn btn-sm btn-primary mr-1"
          @click="importChildIssues"
        >
          하위 업무 불러오기
        </button>
        <button
          :disabled="selectedItems.length === 0"
          class="btn btn-sm btn-danger"
        >
          삭제
        </button>
      </div>
    </div>
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
            <input v-model="selectedItems" type="checkbox" :value="boardItem" />
          </th>
          <td>{{ boardItem.parentIssue.title }}</td>
          <td>
            <issue-progress-bar
              :total-value="boardItem.mandayStatistics.totalValue"
              :items="boardItem.mandayStatistics.items"
              postfix="MD"
            />
            <div class="mt-1" />
            <issue-progress-bar
              :total-value="boardItem.countStatistics.totalValue"
              :items="boardItem.countStatistics.items"
              postfix="개"
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
</template>

<script>
import { from } from 'rxjs';
import { map, mergeMap, toArray } from 'rxjs/operators';
import { mapState, mapGetters } from 'vuex';
import { reduce } from 'lodash-es';

import { boardService } from '@/service/board-service';
import { doorayService } from '@/service/dooray-service';
import { issueService } from '@/service/issue-service';
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
    moduleId: {
      required: true,
      type: String,
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
    importChildIssues() {
      this.importedCount = 0;
      this.totalCount = 0;

      from(this.selectedItems)
        .pipe(
          map((item) => item.parentIssue.parentIssueId),
          mergeMap((parentIssueId) =>
            doorayService.getChildIssue$({ parentIssueId })
          ),
          mergeMap((response) => response.result.contents),
          toArray(),
          mergeMap((contents) => {
            this.totalCount = contents.length;
            return contents;
          }),
          mergeMap((content) =>
            issueService.create$({
              issueId: content.id,
              parentIssueId: content.parent.id,
              projectId: this.projectId,
              title: content.subject,
              moduleId: this.moduleId,
              workingTypeId: this.getWorkingTypeId(content.tagIdList),
              mandays: this.getMandays(content.tagIdList),
              workflowId: content.workflowId,
              workflowTypeCode: content.workflowClass.toUpperCase(),
              milestoneId: content.milestoneId,
            })
          )
        )
        .subscribe({
          next: () => this.importedCount++,
          complete: () => this.getParentIssueBoard(),
          error: () => this.getParentIssueBoard(),
        });
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

<style scoped>
.board .module-board {
  margin-top: 32px;
  position: relative;
}

.board .module-board .btn-area {
  position: absolute;
  top: -22px;
  right: 0;
}

.board .module-board .btn-area .progress {
  margin-bottom: 2px !important;
}

.board td.dev-status-cell,
.board td.deploy-status-cell {
  text-align: center;
  font-weight: bold;
}

.board td.dev-status-cell.WAITING {
  background-color: #ecf0f1;
}

.board td.dev-status-cell.WORKING {
  background-color: #f1c40f;
}

.board td.dev-status-cell.DONE {
  background-color: #2ecc71;
}

.board td.deploy-status-cell.WAITING {
  background-color: #ecf0f1;
}

.board td.deploy-status-cell.WORKING {
  color: white;
  background-color: #e74c3c;
}

.board td.deploy-status-cell.DONE {
  background-color: #2ecc71;
}
</style>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
