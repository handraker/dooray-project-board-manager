<template>
  <div v-if="member" class="member-board">
    <div class="row profile">
      <div class="col">
        <img :src="member.profileImage.url" />
        <strong>{{ member.name }} {{ member.position }}</strong>
      </div>
    </div>
    <div v-if="viewMode === 'HORIZONTAL'">
      <div class="row">
        <div class="col">
          <member-board-vertical-table
            title="할일"
            :issue-list="registeredIssueList"
            table-class="registered"
          />
        </div>
        <div class="col">
          <member-board-vertical-table
            title="진행 중"
            :issue-list="workingIssueList"
            table-class="working"
          />
        </div>
        <div class="col">
          <member-board-vertical-table
            title="완료"
            :issue-list="closedIssueList"
            table-class="closed"
          />
        </div>
      </div>
    </div>
    <div v-else>
      <div class="row">
        <div class="col">
          <member-board-horizontal-table
            title="할일"
            :issue-list="registeredIssueList"
            table-class="registered"
          />
        </div>
      </div>
      <div class="row">
        <div class="col">
          <member-board-horizontal-table
            title="진행 중"
            :issue-list="workingIssueList"
            table-class="working"
          />
        </div>
      </div>
      <div class="row">
        <div class="col">
          <member-board-horizontal-table
            title="완료"
            :issue-list="closedIssueList"
            table-class="closed"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

import { issueService } from '@/service/issue-service';
import MemberBoardHorizontalTable from '@/components/board/MemberBoardHorizontalTable.vue';
import MemberBoardVerticalTable from '@/components/board/MemberBoardVerticalTable.vue';

export default {
  components: { MemberBoardHorizontalTable, MemberBoardVerticalTable },
  props: {
    member: {
      type: Object,
      required: true,
    },
    from: {
      type: String,
      required: true,
    },
    to: {
      type: String,
      required: true,
    },
    viewMode: {
      type: String,
      required: true,
    },
  },
  computed: {
    ...mapState('project', ['projectId']),
  },
  data() {
    return {
      registeredIssueList: [],
      workingIssueList: [],
      closedIssueList: [],
    };
  },
  watch: {
    from: {
      immediate: true,
      handler() {
        this.getIssueList();
      },
    },
    to: {
      immediate: true,
      handler() {
        this.getIssueList();
      },
    },
  },
  methods: {
    getIssueList() {
      issueService
        .getIssues$({
          projectId: this.projectId,
          memberId: this.member.id,
          from: '2000-01-01',
          to: '9999-12-31',
          workflowTypeCode: 'REGISTERED',
        })
        .subscribe((issueList) => (this.registeredIssueList = issueList));
      issueService
        .getIssues$({
          projectId: this.projectId,
          memberId: this.member.id,
          from: this.from,
          to: this.to,
          workflowTypeCode: 'WORKING',
        })
        .subscribe((issueList) => (this.workingIssueList = issueList));
      issueService
        .getIssues$({
          projectId: this.projectId,
          memberId: this.member.id,
          from: this.from,
          to: this.to,
          workflowTypeCode: 'CLOSED',
        })
        .subscribe((issueList) => (this.closedIssueList = issueList));
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>

<style>
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
  padding: 0;
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
  padding: 0;
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
  padding: 0;
  background-color: #bdc3c7;
}

.board .member-board .tag-badge {
  margin: 4px 4px 0 0;
}
</style>
