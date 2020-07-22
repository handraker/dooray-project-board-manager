import rxios from '@/common/rxios';
import { DPBM_HOST } from '@/common/constant';

import { map } from 'rxjs/operators';

class ParentIssueService {
  deleteChildIssue$({ parentIssueId }) {
    return rxios
      .delete(`${DPBM_HOST}/dpbm/parent-issue/${parentIssueId}/issue`)
      .pipe(map((response) => response.data));
  }

  create$({
    parentIssueId,
    projectId,
    title,
    moduleId,
    devStatusCode,
    devStartDate,
    devEndDate,
    deployStatusCode,
    deployStartDate,
    deployEndDate,
    milestoneId,
  }) {
    return rxios
      .post(`${DPBM_HOST}/dpbm/parent-issue/`, {
        parentIssueId,
        projectId,
        title,
        moduleId,
        devStatusCode,
        devStartDate,
        devEndDate,
        deployStatusCode,
        deployStartDate,
        deployEndDate,
        milestoneId,
      })
      .pipe(map((response) => response.data));
  }

  modify$({
    parentIssueId,
    projectId,
    title,
    moduleId,
    devStatusCode,
    devStartDate,
    devEndDate,
    deployStatusCode,
    deployStartDate,
    deployEndDate,
    milestoneId,
  }) {
    return rxios
      .put(`${DPBM_HOST}/dpbm/parent-issue/`, {
        parentIssueId,
        projectId,
        title,
        moduleId,
        devStatusCode,
        devStartDate,
        devEndDate,
        deployStatusCode,
        deployStartDate,
        deployEndDate,
        milestoneId,
      })
      .pipe(map((response) => response.data));
  }
}

export const parentIssueService = new ParentIssueService();
