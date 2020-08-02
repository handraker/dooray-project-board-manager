<template>
  <div class="configuration">
    <div class="row" style="margin-top: 20px;">
      <div class="col">
        <import-issue />
      </div>
    </div>
    <div class="row" style="margin-top: 20px;">
      <div class="col">
        <import-milestone />
      </div>
    </div>
    <div class="row" style="margin-top: 20px;">
      <div class="col">
        <tag-configuration />
      </div>
    </div>
  </div>
</template>

<script>
import ImportMilestone from '@/components/config/ImportMilestone.vue';
import ImportIssue from '@/components/config/ImportIssue.vue';
import TagConfiguration from '@/components/config/TagConfiguration.vue';

export default {
  components: { ImportMilestone, ImportIssue, TagConfiguration },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>
