<template>
  <div class="date-progress-bar">
    <template v-if="isEditMode">
      <div>
        <input v-model="fromDate" type="date" class="form-control" />
        <input v-model="toDate" type="date" class="form-control mt-1" />
      </div>
      <div class="row mt-1">
        <div class="col">
          <button
            class="float-right btn btn-sm btn-secondary ml-1"
            @click="cancel"
          >
            취소
          </button>
          <button
            class="float-right btn btn-sm btn-primary"
            :disabled="!isValidRange"
            @click="apply"
          >
            적용
          </button>
        </div>
      </div>
    </template>
    <div v-else class="progress" @click="edit">
      <div
        class="progress-bar"
        role="progressbar"
        :style="{
          width: `${progress}%`,
          'background-color': progressBarColor,
        }"
        :aria-valuenow="progress"
        aria-valuemin="0"
        aria-valuemax="100"
      ></div>
      <div class="progress-bar-title">{{ from }} ~ {{ to }}</div>
    </div>
  </div>
</template>

<script>
import moment from 'moment';

export default {
  props: {
    from: {
      required: false,
      type: String,
      default: null,
    },
    to: {
      required: false,
      type: String,
      default: null,
    },
    totalWorkingDays: {
      required: true,
      type: Number,
      default: null,
    },
    remainingWorkingDays: {
      required: true,
      type: Number,
    },
  },
  data() {
    return {
      isEditMode: false,
      fromDate: null,
      toDate: null,
    };
  },
  computed: {
    progressBarColor() {
      if (this.progress >= 100) {
        return '#2ecc71';
      } else if (this.progress >= 80) {
        return '#e74c3c';
      } else {
        return '#2980b9';
      }
    },
    progress() {
      if (this.remainingWorkingDays === 0 && this.totalWorkingDays === 0) {
        return 0;
      }

      if (this.remainingWorkingDays === 0 && this.totalWorkingDays > 0) {
        return 100;
      }

      return Math.round(
        ((this.totalWorkingDays - this.remainingWorkingDays) /
          this.totalWorkingDays) *
          100
      );
    },
    isValidRange() {
      return moment(this.fromDate).isSameOrBefore(moment(this.toDate));
    },
  },
  created() {
    this.fromDate = this.from;
    this.toDate = this.to;
  },
  methods: {
    edit() {
      this.isEditMode = true;
    },
    apply() {
      this.$emit('change', {
        from: this.fromDate,
        to: this.toDate,
      });
      this.isEditMode = false;
    },
    cancel() {
      this.fromDate = this.from;
      this.toDate = this.to;
      this.isEditMode = false;
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>

<style>
.date-progress-bar {
  min-width: 200px;
  font-family: 'Nanum Gothic', Meiryo, 'Noto Sans JP', sans-serif,
    'Lucida Sans Unicode', arial;
}
.date-progress-bar * {
  font-size: 12px !important;
}
.date-progress-bar .progress {
  cursor: pointer;
}
</style>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
