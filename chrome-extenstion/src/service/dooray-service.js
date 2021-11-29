import rxios from '@/common/rxios';

import { map, mergeMap } from 'rxjs/operators';

class DoorayService {
  getTags$({ projectId }) {
    return rxios
      .get(`/v2/wapi/projects/!${projectId}/tags?size=10000`, {})
      .pipe(map((response) => response.data));
  }

  getMembers$({ projectId }) {
    return rxios
      .get(`/v2/wapi/projects/!${projectId}/members?size=10000`, {})
      .pipe(map((response) => response.data));
  }

  getWorkflows$({ projectId }) {
    return rxios
      .get(`/v2/wapi/projects/!${projectId}/workflows?size=10000`, {})
      .pipe(map((response) => response.data));
  }

  getMilestones$({ projectId }) {
    return rxios
      .get(`/v2/wapi/projects/!${projectId}/milestones?size=10000`, {})
      .pipe(map((response) => response.data));
  }

  getIssue$({ issueId }) {
    return rxios
      .get(`/v2/wapi/posts/${issueId}`)
      .pipe(map((response) => response.data.result.content));
  }

  getPost$({ projectId, number }) {
    return rxios
      .get(`/v2/wapi/projects/!${projectId}/posts/${number}`)
      .pipe(map((response) => response.data.result.content));
  }

  getChildIssue$({ parentIssueId }) {
    return rxios.get(`/v2/wapi/posts/${parentIssueId}`).pipe(
      map((response) => response.data.result.content),
      mergeMap((content) =>
        rxios.get(
          `/v2/wapi/projects/!${content.projectId}/posts/${content.number}/sub-posts`
        )
      ),
      map((response) => response.data)
    );
  }

  getParentIssues$({ projectId, page, workflowIds }) {
    return rxios
      .get(`/v2/wapi/projects/!${projectId}/posts`, {
        params: {
          page,
          hasParent: false,
          size: 30,
          workflowIds,
          order: '-postUpdatedAt',
        },
      })
      .pipe(map((response) => response.data));
  }
}

export const doorayService = new DoorayService();
