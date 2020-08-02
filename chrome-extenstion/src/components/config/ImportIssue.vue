<template>
  <div class="card">
    <h5 class="card-header">Import Issue</h5>
    <div class="card-body">
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
      <button class="btn btn-primary mt-2" @click="importParentIssue(0)">
        Import Issue
      </button>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex';
import { map, mergeMap, tap, toArray } from 'rxjs/operators';

import { doorayService } from '@/service/dooray-service';
import { issueService } from '@/service/issue-service';

export default {
  data() {
    return {
      importedCount: 0,
      totalCount: 0,
    };
  },
  computed: {
    ...mapState('project', ['projectId']),
    ...mapGetters('project', ['getModuleId', 'getWorkingTypeId', 'getMandays']),
    ...mapGetters('dooray', ['getNotClosedWorkflowIds']),
    progress() {
      if (this.importedCount == 0 || this.totalCount == 0) {
        return 0;
      }

      return Math.round((this.importedCount / this.totalCount) * 100);
    },
  },
  methods: {
    getMemberId(users) {
      const members = users.to.filter((to) => to.type === 'member');
      if (members.length > 0) {
        return members[0].member.organizationMemberId;
      } else {
        return null;
      }
    },
    getIssue(doorayIssue, parentDoorayIssue = undefined) {
      let moduleId = this.getModuleId(doorayIssue.tagIdList);
      if (moduleId == null && parentDoorayIssue !== undefined) {
        moduleId = this.getModuleId(parentDoorayIssue.tagIdList);
      }

      return {
        issueId: doorayIssue.id,
        parentIssueId:
          doorayIssue.parent !== null ? doorayIssue.parent.id : null,
        projectId: this.projectId,
        title: doorayIssue.subject,
        issueNo: doorayIssue.number,
        memberId: this.getMemberId(doorayIssue.users),
        moduleId,
        workingTypeId: this.getWorkingTypeId(doorayIssue.tagIdList),
        mandays: this.getMandays(doorayIssue.tagIdList),
        workflowId: doorayIssue.workflowId,
        workflowTypeCode: doorayIssue.workflowClass.toUpperCase(),
        milestoneId: doorayIssue.milestoneId,
        tagIdList: doorayIssue.tagIdList,
        updatedAt: doorayIssue.updatedAt,
      };
    },
    importParentIssue(page) {
      if (page === 0) {
        this.totalCount = 0;
        this.importedCount = 0;
      }

      doorayService
        .getParentIssues$({
          projectId: this.projectId,
          page,
          workflowIds: this.getNotClosedWorkflowIds,
        })
        .pipe(
          tap((response) => (this.totalCount = response.result.totalCount)),
          mergeMap((response) => response.result.contents),
          mergeMap((content) =>
            issueService.deleteIssue$({ issueId: content.id }).pipe(
              mergeMap(() => issueService.create$([this.getIssue(content)])),
              mergeMap(() =>
                issueService.deleteChildIssue$({ issueId: content.id })
              ),
              mergeMap(() =>
                doorayService.getChildIssue$({ parentIssueId: content.id })
              ),
              mergeMap((response) => response.result.contents),
              map((issueContent) => this.getIssue(issueContent, content)),
              toArray(),
              mergeMap((issueList) => issueService.create$(issueList))
            )
          ),
          tap(() => (this.importedCount += 1))
        )
        .subscribe({
          complete: () => {
            if (this.importedCount < this.totalCount) {
              this.importParentIssue(page + 1);
            }
          },
        });
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>

<style scoped>
.configuration h4 {
  margin-top: 10px;
  font-weight: bold;
}
</style>
