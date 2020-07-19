import rxios from '@/common/rxios';
import { DPBM_HOST } from '@/common/constant';

import { map } from 'rxjs/operators';

class IssueService {
  create$({
    issueId,
    parentIssueId,
    projectId,
    title,
    moduleId,
    workingTypeId,
    mandays,
    workflowId,
    workflowTypeCode,
    milestoneId,
  }) {
    return rxios
      .post(`${DPBM_HOST}/dpbm/issue`, {
        issueId,
        parentIssueId,
        projectId,
        title,
        moduleId,
        workingTypeId,
        mandays,
        workflowId,
        workflowTypeCode,
        milestoneId,
      })
      .pipe(map((response) => response.data));
  }
}

export const issueService = new IssueService();
