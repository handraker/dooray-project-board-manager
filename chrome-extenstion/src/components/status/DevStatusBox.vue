<template>
  <td class="status-box">
    <template v-if="isEditMode">
      <div
        v-for="code in statusList"
        :key="code.label"
        class="item"
        :class="code.status"
        @click="select(code.status)"
      >
        {{ code.label }}
      </div>
    </template>
    <div v-else class="item" :class="status" @click="edit">
      {{ selectedStatus.label }}
    </div>
  </td>
</template>

<script>
export default {
  props: {
    status: {
      required: true,
      type: String,
    },
  },
  data() {
    return {
      isEditMode: false,
    };
  },
  computed: {
    selectedStatus() {
      return this.statusList.find((item) => this.status === item.status);
    },
    statusList() {
      return [
        {
          status: 'WAITING',
          label: '대기',
        },
        {
          status: 'WORKING',
          label: '개발 중',
        },
        {
          status: 'DONE',
          label: '완료',
        },
      ];
    },
  },
  methods: {
    edit() {
      this.isEditMode = true;
    },
    select(status) {
      this.isEditMode = false;
      if (this.status !== status) {
        this.$emit('change', status);
      }
    },
  },
};
</script>

<style scoped>
.board .status-box {
  text-align: center;
  font-weight: bold;
}
.board .status-box .item {
  padding: 20px 0;
  cursor: pointer;
  min-width: 80px;
}
.board .status-box .item.WAITING {
  background-color: #ecf0f1;
}
.board .status-box .item.WORKING {
  background-color: #f1c40f;
}
.board .status-box .item.DONE {
  background-color: #2ecc71;
}
</style>
