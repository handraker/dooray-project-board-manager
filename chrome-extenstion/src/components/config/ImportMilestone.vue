<template>
  <div class="card">
    <h5 class="card-header">Milestone Configuration</h5>
    <div class="card-body">
      <table class="table" style="margin-top: 10px;">
        <colgroup>
          <col />
          <col />
        </colgroup>
        <thead>
          <tr>
            <th scope="col">Milestone ID</th>
            <th scope="col">Milestone Name</th>
            <th scope="col">Milestone Code Freeze</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="milestone in importingProjectMilestones"
            :key="milestone.id"
          >
            <td>{{ milestone.id }}</td>
            <td>{{ milestone.name }}</td>
            <td>
              <input
                v-model="milestone.codeFreezeDate"
                type="date"
                class="form-control"
              />
            </td>
          </tr>
        </tbody>
      </table>
      <button class="btn btn-primary mt-2" @click="importMilestones">
        Import Milestones
      </button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex';

import { projectService } from '@/service/project-service';

export default {
  data() {
    return { importingProjectMilestones: [] };
  },
  computed: {
    ...mapState('project', ['projectMilestones', 'projectId']),
    ...mapState('dooray', ['milestones']),
  },
  watch: {
    milestones() {
      this.calculateImportingProjectMilestones();
    },
    projectMilestones() {
      this.calculateImportingProjectMilestones();
    },
  },
  mounted() {
    this.getProjectMilestones(this.projectId);
  },
  methods: {
    ...mapActions('project', ['getProjectMilestones']),
    calculateImportingProjectMilestones() {
      if (this.milestones.length === 0) {
        return;
      }

      this.importingProjectMilestones = this.milestones
        .filter((milestone) => milestone.status === 'open')
        .map((milestone) => {
          const projectMilestone = this.projectMilestones.find(
            (projectMilestone) => projectMilestone.milestoneId === milestone.id
          );
          return {
            ...milestone,
            milestoneId: milestone.id,
            ...projectMilestone,
          };
        });
    },
    importMilestones() {
      projectService
        .createProjectMilestones$({
          projectId: this.projectId,
          projectMilestones: this.importingProjectMilestones,
        })
        .subscribe({
          complete() {
            alert('마일스톤을 불러왔습니다.');
          },
          error() {
            alert('마일스톤 불러오기에 실패했습니다');
          },
        });
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>

<style scoped>
.configuration input {
  font-size: 12px;
}
</style>
