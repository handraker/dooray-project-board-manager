<template>
  <div>
    <h4>Import Issue from dooray</h4>
    <div>
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
        Import Parent Issue
      </button>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex';
import { tap, mergeMap, toArray } from 'rxjs/operators';

import { doorayService } from '@/service/dooray-service';
import { issueService } from '@/service/issue-service';
import { parentIssueService } from '@/service/parent-issue-service';

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
            parentIssueService
              .create$({
                parentIssueId: content.id,
                projectId: this.projectId,
                title: content.subject,
                moduleId: this.getModuleId(content.tagIdList),
                devStatusCode: 'WAITING',
                devStartDate: null,
                devEndDate: null,
                deployStatusCode: 'WAITING',
                deployStartDate: null,
                deployEndDate: null,
                milestoneId: content.milestoneId,
              })
              .pipe(
                mergeMap(() =>
                  parentIssueService.deleteChildIssue$({
                    parentIssueId: content.id,
                  })
                ),
                mergeMap(() =>
                  doorayService.getChildIssue$({ parentIssueId: content.id })
                ),
                mergeMap((response) => response.result.contents),
                mergeMap((issueContent) => {
                  let moduleId = this.getModuleId(issueContent.tagIdList);
                  if (moduleId == null) {
                    moduleId = this.getModuleId(content.tagIdList);
                  }
                  return issueService.create$({
                    issueId: issueContent.id,
                    parentIssueId: issueContent.parent.id,
                    projectId: this.projectId,
                    title: issueContent.subject,
                    moduleId,
                    workingTypeId: this.getWorkingTypeId(
                      issueContent.tagIdList
                    ),
                    mandays: this.getMandays(issueContent.tagIdList),
                    workflowId: issueContent.workflowId,
                    workflowTypeCode: issueContent.workflowClass.toUpperCase(),
                    milestoneId: issueContent.milestoneId,
                  });
                }),
                toArray()
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
