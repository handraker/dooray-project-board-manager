import rxios from '@/common/rxios';
import { DPBM_HOST } from '@/common/constant';

import { map } from 'rxjs/operators';

class IssueService {
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
}

export const issueService = new IssueService();
