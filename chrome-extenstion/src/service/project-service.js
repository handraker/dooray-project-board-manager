import rxios from '@/common/rxios';
import { DPBM_HOST } from '@/common/constant';

import { map } from 'rxjs/operators';

class ProjectService {
  getProject$({ projectId }) {
    return rxios
      .get(`${DPBM_HOST}/dpbm/projects/${projectId}`, {})
      .pipe(map((response) => response.data));
  }

  getProjectMilestones$({ projectId }) {
    return rxios
      .get(`${DPBM_HOST}/dpbm/projects/${projectId}/milestones`, {})
      .pipe(map((response) => response.data));
  }

  createProjectMilestones$({ projectId, projectMilestones }) {
    return rxios
      .post(`${DPBM_HOST}/dpbm/projects/${projectId}/milestones`, {
        projectMilestones,
      })
      .pipe(map((response) => response.data));
  }
}

export const projectService = new ProjectService();
