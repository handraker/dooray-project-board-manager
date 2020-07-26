<template>
  <div>
    <div style="margin-top: 10px;">
      <input v-model="from" type="date" style="margin-right: 5px;" />
      <input v-model="to" type="date" />
      <div
        class="form-check form-check-inline align-middle"
        style="margin-left: 5px;"
      >
        <input
          id="viewMode_VERTICAL"
          v-model="viewMode"
          class="form-check-input"
          type="radio"
          name="viewMode"
          value="VERTICAL"
        />
        <label class="form-check-label" for="viewMode_VERTICAL">세로</label>
      </div>
      <div class="form-check form-check-inline align-middle">
        <input
          id="viewMode_HORIZONTAL"
          v-model="viewMode"
          class="form-check-input"
          type="radio"
          name="viewMode"
          value="HORIZONTAL"
        />
        <label class="form-check-label" for="viewMode_HORIZONTAL">가로</label>
      </div>
    </div>
    <member-board
      v-for="member in projectMembers"
      :key="member.id"
      :member="member"
      :from="from"
      :to="to"
      :view-mode="viewMode"
    />
  </div>
</template>

<script>
import moment from 'moment';
import { mapGetters } from 'vuex';

import MemberBoard from '@/components/board/MemberBoard.vue';

export default {
  components: { MemberBoard },
  data() {
    return {
      from: moment().subtract(10, 'd').format('YYYY-MM-DD'),
      to: moment().format('YYYY-MM-DD'),
      viewMode: 'HORIZONTAL',
    };
  },
  computed: {
    ...mapGetters('project', ['projectMembers']),
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
