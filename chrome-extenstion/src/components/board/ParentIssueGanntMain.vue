<template>
  <div style="width: 100%; height: 100%;">
    <gantt-elastic :tasks="tasks" :options="options"></gantt-elastic>
  </div>
</template>

<script>
import moment from 'moment';
import GanttElastic from 'gantt-elastic';
import { mapGetters, mapState } from 'vuex';
import { filter, map, mergeMap, toArray } from 'rxjs/operators';

import { boardService } from '@/service/board-service';

export default {
  components: {
    GanttElastic,
  },
  data() {
    return {
      tasks: [],
      options: {
        row: {
          height: 24,
        },
        times: {
          timeZoom: 24,
        },
        calendar: {
          hour: {
            display: false,
          },
          month: {
            format: {
              short(date) {
                return date.format('MM');
              },
              long(date) {
                return date.format('YYYY년 MM월');
              },
            },
          },
        },
        chart: {
          progress: {
            bar: false,
          },
          expander: {
            display: true,
          },
        },
        taskList: {
          expander: {
            straight: false,
          },
          columns: [
            {
              id: 2,
              label: '이슈',
              value: 'label',
              width: 400,
              expander: true,
              html: true,
            },
          ],
        },
      },
    };
  },
  computed: {
    ...mapState('project', ['projectId']),
    ...mapGetters('project', ['getModule']),
  },
  created() {
    this.getParentIssues();
  },
  methods: {
    getParentIssues() {
      boardService
        .getParentIssueBoard$({
          projectId: this.projectId,
          showInProgress: true,
          withStatistics: false,
        })
        .pipe(
          mergeMap((response) => response.items),
          filter(
            (item) =>
              item.devDateProgress !== undefined &&
              item.devDateProgress.from !== null
          ),
          map((item) => {
            const moduleColor = this.getModule(item.parentIssue.moduleId).color;
            return {
              id: item.parentIssue.issueId,
              label: item.parentIssue.title,
              start: moment(item.devDateProgress.from).toDate(),
              end: moment(item.deployDateProgress.to).toDate(),
              duration: moment
                .duration(
                  moment(item.deployDateProgress.to).diff(
                    moment(item.devDateProgress.from)
                  )
                )
                .asMilliseconds(),
              progress: 100,
              style: {
                base: {
                  fill: `#${moduleColor}`,
                  stroke: `#${moduleColor}`,
                },
              },
              type: 'task',
            };
          }),
          toArray()
        )
        .subscribe((tasks) => (this.tasks = tasks));
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
