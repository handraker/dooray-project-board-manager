<template>
  <table class="table" :class="tableClass">
    <colgroup>
      <col class="marker" />
      <col />
    </colgroup>
    <thead>
      <th class="marker"></th>
      <th>{{ title }}</th>
    </thead>
    <tbody>
      <tr v-for="issue in issueList" :key="issue.issueId">
        <td class="marker"></td>
        <td>
          <div>
            <span>{{ issue.issueNo }} : </span>
            <a
              :href="`/project/${projectId}/${issue.issueId}`"
              @click="moveIssue"
              >{{ issue.title }}</a
            >
            <button
              type="button"
              class="d-toolbar-white-icon-btn open-new-popup-btn ng-scope"
              tooltip-class="icon-tooltip in-button"
              @click="openIssuePopup(issue.issueId)"
            >
              <span class="v2-icons-open-new"></span>
            </button>
            <button
              v-if="issue.inProgress"
              type="button"
              class="d-toolbar-white-icon-btn open-new-popup-btn ng-scope"
              style="color: #e74c3c;"
              @click="stopTimer(issue)"
            >
              ■ 중지 {{ getTimeProgress(issue) }}
            </button>
            <button
              v-else
              type="button"
              class="d-toolbar-white-icon-btn open-new-popup-btn ng-scope"
              @click="startTimer(issue)"
            >
              ▶ 시작
            </button>
          </div>
          <div class="mt-1 row">
            <div class="col">
              갱신일 :
              {{ issue.updatedAt | moment('YYYY-MM-DD hh:mm') }}
            </div>
            <div class="col text-right">
              {{ getMilestone(issue.milestoneId).name }}
            </div>
          </div>
          <div class="mt-1 inline-box" title="태그">
            <span class="tag-group">
              <span v-for="tagId in issue.tagIdList" :key="tagId"
                ><span
                  v-if="tags[tagId]"
                  class="tag-badge"
                  :style="getTagStyle(tagId)"
                  >{{ tags[tagId].name }}</span
                ></span
              >
            </span>
          </div>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script>
import { mapGetters, mapState } from 'vuex';
import moment from 'moment';
import { issueService } from '@/service/issue-service';

export default {
  props: {
    title: {
      required: true,
      type: String,
    },
    issueList: {
      required: true,
      type: Array,
    },
    tableClass: {
      required: true,
      type: String,
    },
  },
  data() {
    return {
      currentTime: moment(),
      timerId: null,
    };
  },
  computed: {
    ...mapState('project', ['projectId']),
    ...mapState('dooray', ['tags']),
    ...mapGetters('dooray', ['getMilestone', 'getTagStyle']),
    ...mapGetters('project', ['getModule']),
  },
  watch: {
    currentTime: {
      handler() {
        this.timerId = setTimeout(() => {
          this.currentTime = moment();
        }, 1000);
      },
      immediate: true, // This ensures the watcher is triggered upon creation
    },
  },
  destroyed() {
    if (this.timerId !== null) {
      clearTimeout(this.timerId);
      nhn;
    }
  },
  methods: {
    moveIssue() {
      document
        .querySelector(
          'project-contents-type-selector > div > button:nth-child(3)'
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
    getTimeProgress(issue) {
      if (!issue.inProgress) {
        return '';
      } else {
        return moment
          .utc(moment(this.currentTime).diff(issue.startedAt))
          .format('HH:mm:ss');
      }
    },
    startTimer(issue) {
      const name = this.getModule(issue.moduleId).name;
      const idx = name.lastIndexOf(':');
      issueService
        .startTimer({
          issueId: issue.issueId,
          moduleName: name.substr(idx + 1).trim(),
        })
        .subscribe(() => {
          issue.inProgress = true;
          issue.startedAt = moment();
        });
    },
    stopTimer(issue) {
      issueService.stopTimer().subscribe(() => {
        issue.inProgress = false;
      });
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
