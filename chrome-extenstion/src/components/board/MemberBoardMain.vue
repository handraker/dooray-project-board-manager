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
      <div class="form-check form-check-inline align-middle">
        <input
          id="showAllRegistered"
          v-model="showAllRegistered"
          class="form-check-input"
          type="checkbox"
          name="showAllRegistered"
        />
        <label class="form-check-label" for="showAllRegistered"
          >모든 할일 보기</label
        >
      </div>
    </div>
    <member-board
      v-for="member in projectMembers"
      :key="member.id"
      :member="member"
      :from="from"
      :to="to"
      :view-mode="viewMode"
      :show-all-registered="showAllRegistered"
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
      from: moment().subtract(5, 'd').format('YYYY-MM-DD'),
      to: moment().add(1, 'd').format('YYYY-MM-DD'),
      viewMode: 'HORIZONTAL',
      showAllRegistered: false,
    };
  },
  computed: {
    ...mapGetters('project', ['projectMembers']),
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
