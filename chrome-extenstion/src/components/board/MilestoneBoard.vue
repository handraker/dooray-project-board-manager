<template>
  <div class="milestone-board jumbotron">
    <h1 class="display-4">2020.07.14 (CF.0630)</h1>
    <p class="lead">
      2020년 06월 10일 ~ 2020년 07월 14일
    </p>
    <hr class="my-4" />
    <div class="content">
      <table class="table">
        <thead>
          <tr>
            <th>모듈</th>
            <th>하위업무</th>
            <th v-for="workflow in workflows" :key="workflow.id">
              {{ workflow.name }}
            </th>
          </tr>
        </thead>
      </table>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  computed: {
    ...mapState('dooray', ['workflows']),
  },
};
</script>

<style scoped>
.board .milestone-board {
  margin-top: 20px;
}

.board .milestone-board .content {
  background-color: white;
}
</style>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
