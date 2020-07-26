<template>
  <div class="member-board">
    <div class="row profile">
      <div class="col">
        <img :src="member.profileImage.url" />
        <strong>{{ member.name }} {{ member.position }}</strong>
      </div>
    </div>
    <div class="row">
      <div class="col">
        <table class="table registered">
          <colgroup>
            <col class="marker" />
            <col />
            <col style="width: 150px;" />
            <col style="width: 150px;" />
          </colgroup>
          <thead>
            <th></th>
            <th>할일</th>
            <th>마일스톤</th>
            <th>갱신일</th>
          </thead>
          <tbody>
            <tr v-for="issue in registeredIssueList" :key="issue.issueId">
              <td></td>
              <td>
                <div>{{ issue.title }}</div>
                <div class="inline-box" title="태그">
                  <span class="tag-group">
                    <span v-for="tagId in issue.tagIdList" :key="tagId"
                      ><span class="tag-badge" :style="getTagStyle(tagId)">{{
                        tags[tagId].name
                      }}</span></span
                    >
                  </span>
                </div>
              </td>
              <td>{{ getMilestone(issue.milestoneId).name }}</td>
              <td>{{ issue.updatedAt | moment('YYYY-MM-DD hh:mm') }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="row">
      <div class="col">
        <table class="table working">
          <colgroup>
            <col class="marker" />
            <col />
            <col style="width: 150px;" />
            <col style="width: 150px;" />
          </colgroup>
          <thead>
            <th></th>
            <th>진행 중</th>
            <th>마일스톤</th>
            <th>갱신일</th>
          </thead>
          <tbody>
            <tr v-for="issue in workingIssueList" :key="issue.issueId">
              <td></td>
              <td>
                <div>{{ issue.title }}</div>
                <div class="inline-box" title="태그">
                  <span class="tag-group">
                    <span v-for="tagId in issue.tagIdList" :key="tagId"
                      ><span class="tag-badge" :style="getTagStyle(tagId)">{{
                        tags[tagId].name
                      }}</span></span
                    >
                  </span>
                </div>
              </td>
              <td>{{ getMilestone(issue.milestoneId).name }}</td>
              <td>{{ issue.updatedAt | moment('YYYY-MM-DD hh:mm') }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="row">
      <div class="col">
        <table class="table closed">
          <colgroup>
            <col class="marker" />
            <col />
            <col style="width: 150px;" />
            <col style="width: 150px;" />
          </colgroup>
          <thead>
            <th></th>
            <th>완료</th>
            <th>마일스톤</th>
            <th>갱신일</th>
          </thead>
          <tbody>
            <tr v-for="issue in closedIssueList" :key="issue.issueId">
              <td></td>
              <td>
                <div>{{ issue.title }}</div>
                <div class="inline-box" title="태그">
                  <span class="tag-group">
                    <span v-for="tagId in issue.tagIdList" :key="tagId"
                      ><span class="tag-badge" :style="getTagStyle(tagId)">{{
                        tags[tagId].name
                      }}</span></span
                    >
                  </span>
                </div>
              </td>
              <td>{{ getMilestone(issue.milestoneId).name }}</td>
              <td>{{ issue.updatedAt | moment('YYYY-MM-DD hh:mm') }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment';
import { mapGetters, mapState } from 'vuex';

import { issueService } from '@/service/issue-service';

export default {
  props: {
    member: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      issueList: [],
    };
  },
  computed: {
    ...mapState('dooray', ['tags']),
    ...mapGetters('dooray', ['getMilestone', 'getTagStyle']),
    registeredIssueList() {
      return this.issueList.filter(
        (issue) => issue.workflowTypeCode == 'REGISTERED'
      );
    },
    workingIssueList() {
      return this.issueList.filter(
        (issue) => issue.workflowTypeCode == 'WORKING'
      );
    },
    closedIssueList() {
      return this.issueList.filter(
        (issue) => issue.workflowTypeCode == 'CLOSED'
      );
    },
  },
  mounted() {
    issueService
      .getIssues$({
        memberId: this.member.id,
        from: moment().subtract(10, 'd').format('YYYY-MM-DD'),
        to: moment().format('YYYY-MM-DD'),
      })
      .subscribe((issueList) => (this.issueList = issueList));
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>

<style scoped>
.board .member-board img {
  border-radius: 50%;
  border: 1px solid rgba(51, 51, 51, 0.15);
}

.board .member-board .profile strong {
  margin-left: 5px;
}

.board .member-board .profile {
  margin-bottom: 10px;
}

.board .member-board .table.registered {
  border-top: 3px solid #27ae60;
}

.board .member-board .table.registered th {
  color: #27ae60;
}

.board .member-board .table.registered .marker {
  width: 10px;
  background-color: #27ae60;
}

.board .member-board .table.working {
  border-top: 3px solid #2980b9;
}

.board .member-board .table.working th {
  color: #2980b9;
}

.board .member-board .table.working .marker {
  width: 10px;
  background-color: #2980b9;
}

.board .member-board .table.closed {
  border-top: 3px solid #bdc3c7;
}

.board .member-board .table.closed th {
  color: #bdc3c7;
}

.board .member-board .table.closed .marker {
  width: 10px;
  background-color: #bdc3c7;
}

.board .member-board .inline-box {
  margin-top: 8px;
}

.board .member-board .tag-badge {
  margin: 4px 4px 0 0;
}
</style>
