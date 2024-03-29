import rxios from '@/common/rxios';
import { DPBM_HOST } from '@/common/constant';

import { map } from 'rxjs/operators';

class BoardService {
  getParentIssueBoard$({
    projectId,
    milestoneId,
    moduleId,
    showInProgress,
    withStatistics,
  }) {
    return rxios
      .get(`${DPBM_HOST}/dpbm/projects/${projectId}/boards/parent-issue`, {
        params: {
          moduleId,
          milestoneId,
          showInProgress,
          withStatistics,
        },
      })
      .pipe(map((response) => response.data));
  }

  getMilestoneBoard$({ projectId, milestoneId, moduleId }) {
    return rxios
      .get(`${DPBM_HOST}/dpbm/projects/${projectId}/boards/milestone`, {
        params: {
          milestoneId,
          moduleId,
        },
      })
      .pipe(map((response) => response.data));
  }
}

export const boardService = new BoardService();
