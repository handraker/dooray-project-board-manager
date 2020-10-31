<template>
  <div class="parent-issue-board">
    <div
      class="module-board"
      :style="{ 'border-bottom': `3px solid ${moduleColor}` }"
    >
      <h4 :style="{ color: moduleColor }">
        {{ moduleName }}
      </h4>
    </div>
    <div class="content">
      <table class="table table-bordered gantt">
        <thead>
          <tr>
            <th rowspan="2" style="min-width: 500px;">이슈</th>
            <th
              v-for="(month, no) in months"
              :key="no"
              :colspan="month.weekCount"
              class="text-center"
            >
              {{ month.year }}년 {{ month.month }}월
            </th>
          </tr>
          <tr>
            <th v-for="(week, no) in weeks" :key="no" class="week">
              {{ week.weekOfMonth }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="boardItem in boardItems" :key="boardItem.id">
            <td class="issue">
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
            <td
              class="week"
              v-for="(week, no) in weeks"
              :key="no"
              :style="getWeekStyle(week, boardItem)"
            ></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import moment from 'moment';
import { mapGetters, mapState } from 'vuex';

import { boardService } from '@/service/board-service';

export default {
  props: {
    moduleId: {
      required: true,
      type: String,
    },
  },
  data() {
    return {
      ganntStartDate: moment().add(-1, 'month'),
      ganntEndDate: moment().add(1, 'year'),
      boardItems: [],
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
    moduleColor() {
      return `#${this.getModule(this.moduleId).color}`;
    },
    moduleName() {
      const name = this.getModule(this.moduleId).name;
      const idx = name.lastIndexOf(':');
      return name.substr(idx + 1).trim();
    },
    weeks() {
      let weeks = [];
      for (
        let day = this.ganntStartDate.day(1);
        day.isSameOrBefore(this.ganntEndDate);
        day = day.add(7, 'd')
      ) {
        const start = moment(day);
        const end = moment(start).day(5);
        weeks.push({
          start,
          end,
          year: start.year(),
          month: start.month() + 1,
          weekOfMonth: this.weekOfMonth(start),
        });
      }
      return weeks;
    },
    months() {
      let months = [];
      let month = {
        year: -1,
        month: -1,
        weekCount: 1,
      };
      for (let i = 0; i < this.weeks.length; i++) {
        if (i == 0) {
          month = {
            year: this.weeks[i].year,
            month: this.weeks[i].month,
            weekCount: 1,
          };
        } else if (this.weeks[i - 1].month != this.weeks[i].month) {
          months.push(month);
          month = {
            year: this.weeks[i].year,
            month: this.weeks[i].month,
            weekCount: 1,
          };
          month.month = this.weeks[i].month;
        } else {
          month.weekCount++;
        }
      }
      months.push(month);
      return months;
    },
  },
  created() {
    if (this.ganntStartDate.day() != 1) {
      this.ganntStartDate = this.ganntStartDate.add(7, 'd').day(1);
    }

    this.getParentIssueBoard();
  },
  methods: {
    isParentIssueInWeek(week, boardItem) {
      if (
        boardItem.devDateProgress.from == null ||
        boardItem.deployDateProgress.to == null
      ) {
        return false;
      }

      if (
        moment(boardItem.devDateProgress.from).isAfter(week.end) ||
        moment(boardItem.deployDateProgress.to).isBefore(week.start)
      ) {
        return false;
      }
      return true;
    },
    getWeekStyle(week, boardItem) {
      if (this.isParentIssueInWeek(week, boardItem)) {
        return {
          'background-color': this.moduleColor,
        };
      } else {
        return {};
      }
    },
    weekOfMonth(date) {
      let week = 0;
      for (
        let d = moment(date).date(1);
        d.isSameOrBefore(date);
        d = d.add(1, 'day')
      ) {
        if (d.day() == 1) {
          week++;
        }
      }
      return week;
    },
    getParentIssueBoard() {
      boardService
        .getParentIssueBoard$({
          projectId: this.projectId,
          moduleId: this.moduleId,
          showInProgress: true,
          withStatistics: false,
        })
        .subscribe((response) => (this.boardItems = response.items));
    },
    moveIssue() {
      document
        .querySelector(
          '#main-wrapper > section > section > section > project-contents-layout > project-contents-header > div > project-contents-type-selector > div > button:nth-child(3)'
        )
        .click();
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
table {
  overflow-y: scroll;
}

th:first-child,
td:first-child {
  position: sticky;
  left: 0px;
}

table.gantt td {
  padding-top: 0;
  padding-bottom: 0;
}

td.issue {
  background-color: white;
}
</style>
