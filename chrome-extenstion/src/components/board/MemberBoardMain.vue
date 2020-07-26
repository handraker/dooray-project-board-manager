<template>
  <div>
    <div style="margin-top: 10px;">
      <input v-model="from" type="date" style="margin-right: 5px;" />
      <input v-model="to" type="date" />
    </div>
    <member-board
      v-for="member in projectMembers"
      :key="member.id"
      :member="member"
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
    };
  },
  computed: {
    ...mapGetters('project', ['projectMembers']),
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
