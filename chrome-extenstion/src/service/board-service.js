import rxios from '@/common/rxios';
import { DPBM_HOST } from '@/common/constant';

import { map } from 'rxjs/operators';

class BoardService {
  getParentIssueBoard$({ projectId, moduleId }) {
    return rxios
      .get(`${DPBM_HOST}/dpbm/projects/${projectId}/boards/parent-issue`, {
        params: {
          moduleId,
        },
      })
      .pipe(map((response) => response.data));
  }
}

export const boardService = new BoardService();
