<template>
  <div>
    <h4>Tag Configuration</h4>
    <table class="table" style="margin-top: 10px;">
      <colgroup>
        <col />
        <col />
      </colgroup>
      <thead>
        <tr>
          <th scope="col">Tag Prefix ID</th>
          <th scope="col">Tags</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="tagPrefix in tagPrefixs" :key="tagPrefix.id">
          <td>{{ tagPrefix.id }}</td>
          <td>
            <div class="inline-box" title="태그">
              <span class="tag-group">
                <span v-for="tag in getTags(tagPrefix.id)" :key="tag.id"
                  ><span class="tag-badge" :style="getTagStyle(tag)">{{
                    tag.name
                  }}</span></span
                >
              </span>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { mapState } from 'vuex';

import { getContrast } from '@/common/utils';

export default {
  computed: {
    ...mapState('dooray', ['tagPrefixs', 'tags']),
  },
  methods: {
    getTags(tagPrefixId) {
      return Object.keys(this.tags)
        .filter((tagId) => this.tags[tagId].tagPrefixId === tagPrefixId)
        .map((tagId) => this.tags[tagId]);
    },
    getTagStyle(tag) {
      return {
        'background-color': `#${tag.color}`,
        'border-color': `#${tag.color}`,
        color: getContrast(tag.color),
      };
    },
  },
};
</script>

<style scoped src="bootstrap/dist/css/bootstrap.min.css"></style>

<style scoped>
.configuration h4 {
  margin-top: 10px;
  font-weight: bold;
}

.tag-badge {
  margin: 0 2px 2px 0;
}
</style>
