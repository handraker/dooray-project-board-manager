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
        color: this.getContrast(tag.color),
      };
    },
    /*!
     * Get the contrasting color for any hex color
     * (c) 2019 Chris Ferdinandi, MIT License, https://gomakethings.com
     * Derived from work by Brian Suda, https://24ways.org/2010/calculating-color-contrast/
     * @param  {String} A hexcolor value
     * @return {String} The contrasting color (black or white)
     */
    getContrast(hexcolor) {
      // If a leading # is provided, remove it
      if (hexcolor.slice(0, 1) === '#') {
        hexcolor = hexcolor.slice(1);
      }

      // If a three-character hexcode, make six-character
      if (hexcolor.length === 3) {
        hexcolor = hexcolor
          .split('')
          .map(function (hex) {
            return hex + hex;
          })
          .join('');
      }

      // Convert to RGB value
      var r = parseInt(hexcolor.substr(0, 2), 16);
      var g = parseInt(hexcolor.substr(2, 2), 16);
      var b = parseInt(hexcolor.substr(4, 2), 16);

      // Get YIQ ratio
      var yiq = (r * 299 + g * 587 + b * 114) / 1000;

      // Check contrast
      return yiq >= 128 ? '#333333' : 'white';
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
