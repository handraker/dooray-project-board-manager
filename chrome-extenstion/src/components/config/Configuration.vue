<template>
  <div class="configuration">
    <import-parent-issue />
    <tag-configuration />
  </div>
</template>

<script>
import ImportParentIssue from '@/components/config/ImportParentIssue.vue';
import TagConfiguration from '@/components/config/TagConfiguration.vue';

export default {
  components: { ImportParentIssue, TagConfiguration },
};
</script>
