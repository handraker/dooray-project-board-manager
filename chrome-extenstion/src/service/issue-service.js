import rxios from '@/common/rxios';
import { DPBM_HOST } from '@/common/constant';

import { map } from 'rxjs/operators';

class IssueService {
  getIssues$({ memberId, workflowTypeCode, from, to }) {
    return rxios
      .get(`${DPBM_HOST}/dpbm/issue`, {
        params: {
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
}

export const issueService = new IssueService();
