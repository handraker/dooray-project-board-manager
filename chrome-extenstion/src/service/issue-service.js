import rxios from '@/common/rxios';
import store from '@/store';
import { DPBM_HOST } from '@/common/constant';

import { map, mergeMap } from 'rxjs/operators';
import { doorayService } from '@/service/dooray-service';

class IssueService {
  importIssue$({ projectId, issueId }) {
    function getMemberId(users) {
      const members = users.to.filter((to) => to.type === 'member');
      if (members.length > 0) {
        return members[0].member.organizationMemberId;
      } else {
        return null;
      }
    }

    return doorayService.getIssue$({ issueId }).pipe(
      mergeMap((issue) =>
        doorayService.getPost$({ projectId, number: issue.number })
      ),
      map((doorayIssue) => {
        let moduleId = store.getters['project/getModuleId'](
          doorayIssue.tagIdList
        );

        return {
          issueId: doorayIssue.id,
          parentIssueId:
            doorayIssue.parent !== null ? doorayIssue.parent.id : null,
          projectId: projectId,
          title: doorayIssue.subject,
          issueNo: doorayIssue.number,
          memberId: getMemberId(doorayIssue.users),
          moduleId,
          workingTypeId: store.getters['project/getWorkingTypeId'](
            doorayIssue.tagIdList
          ),
          mandays: store.getters['project/getMandays'](doorayIssue.tagIdList),
          workflowId: doorayIssue.workflowId,
          workflowTypeCode: doorayIssue.workflowClass.toUpperCase(),
          milestoneId: doorayIssue.milestoneId,
          tagIdList: doorayIssue.tagIdList,
          updatedAt: doorayIssue.updatedAt,
        };
      }),
      mergeMap((issue) => issueService.create$([issue]))
    );
  }

  getIssues$({ projectId, memberId, workflowTypeCode, from, to }) {
    return rxios
      .get(`${DPBM_HOST}/dpbm/issue`, {
        params: {
          projectId,
          memberId,
          workflowTypeCode,
          from,
          to,
        },
      })
      .pipe(map((response) => response.data));
  }

  create$(issueList) {
    return rxios
      .post(`${DPBM_HOST}/dpbm/issue`, {
        issueList,
      })
      .pipe(map((response) => response.data));
  }

  modifyProgress$({
    issueId,
    devStatusCode,
    devStartDate,
    devEndDate,
    deployStatusCode,
    deployStartDate,
    deployEndDate,
  }) {
    return rxios
      .put(`${DPBM_HOST}/dpbm/issue/${issueId}/progress`, {
        devStatusCode,
        devStartDate,
        devEndDate,
        deployStatusCode,
        deployStartDate,
        deployEndDate,
      })
      .pipe(map((response) => response.data));
  }

  deleteIssue$({ issueId }) {
    return rxios
      .delete(`${DPBM_HOST}/dpbm/issue/${issueId}`)
      .pipe(map((response) => response.data));
  }

  deleteChildIssue$({ issueId }) {
    return rxios
      .delete(`${DPBM_HOST}/dpbm/issue/${issueId}/child-issue`)
      .pipe(map((response) => response.data));
  }

  startTimer({ issueId, moduleName }) {
    return rxios
      .post(`${DPBM_HOST}//dpbm/issue/${issueId}/start-timer`, {
        moduleName,
      })
      .pipe(map((response) => response.data));
  }

  stopTimer() {
    return rxios
      .post(`${DPBM_HOST}//dpbm/issue/stop-timer`)
      .pipe(map((response) => response.data));
  }
}

export const issueService = new IssueService();
