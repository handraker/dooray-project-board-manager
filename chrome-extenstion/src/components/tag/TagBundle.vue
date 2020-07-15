<template>
  <ul class="inline-box" title="태그">
    <li
      class="tag-group ng-scope"
      ng-repeat="tagId in ::$ctrl.tagIdList track by tagId"
      ng-init="tag=$ctrl.post._wrap.refMap.tagMap(tagId)"
    >
      <tag-badge tag="tag" class="ng-isolate-scope"
        ><!-- doorayTrackBy: $ctrl.uiVersion --><span
          dooray-track-by="$ctrl.uiVersion"
          class="ng-scope"
          ><!-- ngIf: ::!!$ctrl.onClick -->
          <!-- ngIf: ::!$ctrl.onClick --><span
            ng-if="::!$ctrl.onClick"
            title="0.모듈: MySQL"
            class="tag-badge ng-binding ng-scope"
            ng-style="::$ctrl.tagStyle"
            ng-bind="::$ctrl.getDisplayName()"
            style="
              background-color: rgb(41, 128, 185);
              color: white;
              border-color: rgb(41, 128, 185);
            "
            >0.모듈: MySQL</span
          ><!-- end ngIf: ::!$ctrl.onClick --></span
        ></tag-badge
      >
    </li>
    <!-- end ngRepeat: tagId in ::$ctrl.tagIdList track by tagId -->
    <li
      class="tag-group ng-scope"
      ng-repeat="tagId in ::$ctrl.tagIdList track by tagId"
      ng-init="tag=$ctrl.post._wrap.refMap.tagMap(tagId)"
    >
      <tag-badge tag="tag" class="ng-isolate-scope"
        ><!-- doorayTrackBy: $ctrl.uiVersion --><span
          dooray-track-by="$ctrl.uiVersion"
          class="ng-scope"
          ><!-- ngIf: ::!!$ctrl.onClick -->
          <!-- ngIf: ::!$ctrl.onClick --><span
            ng-if="::!$ctrl.onClick"
            title="0: 배포시나리오"
            class="tag-badge ng-binding ng-scope"
            ng-style="::$ctrl.tagStyle"
            ng-bind="::$ctrl.getDisplayName()"
            style="
              background-color: rgb(142, 68, 173);
              color: white;
              border-color: rgb(142, 68, 173);
            "
            >0: 배포시나리오</span
          ><!-- end ngIf: ::!$ctrl.onClick --></span
        ></tag-badge
      >
    </li>
    <!-- end ngRepeat: tagId in ::$ctrl.tagIdList track by tagId -->
    <li
      class="tag-group ng-scope"
      ng-repeat="tagId in ::$ctrl.tagIdList track by tagId"
      ng-init="tag=$ctrl.post._wrap.refMap.tagMap(tagId)"
    >
      <tag-badge tag="tag" class="ng-isolate-scope"
        ><!-- doorayTrackBy: $ctrl.uiVersion --><span
          dooray-track-by="$ctrl.uiVersion"
          class="ng-scope"
          ><!-- ngIf: ::!!$ctrl.onClick -->
          <!-- ngIf: ::!$ctrl.onClick --><span
            ng-if="::!$ctrl.onClick"
            title="1.작업: 기능배포"
            class="tag-badge ng-binding ng-scope"
            ng-style="::$ctrl.tagStyle"
            ng-bind="::$ctrl.getDisplayName()"
            style="
              background-color: rgb(142, 68, 173);
              color: white;
              border-color: rgb(142, 68, 173);
            "
            >1.작업: 기능배포</span
          ><!-- end ngIf: ::!$ctrl.onClick --></span
        ></tag-badge
      >
    </li>
    <!-- end ngRepeat: tagId in ::$ctrl.tagIdList track by tagId -->
    <li
      class="tag-group ng-scope"
      ng-repeat="tagId in ::$ctrl.tagIdList track by tagId"
      ng-init="tag=$ctrl.post._wrap.refMap.tagMap(tagId)"
    >
      <tag-badge tag="tag" class="ng-isolate-scope"
        ><!-- doorayTrackBy: $ctrl.uiVersion --><span
          dooray-track-by="$ctrl.uiVersion"
          class="ng-scope"
          ><!-- ngIf: ::!!$ctrl.onClick -->
          <!-- ngIf: ::!$ctrl.onClick --><span
            ng-if="::!$ctrl.onClick"
            title="MD: 0.5"
            class="tag-badge ng-binding ng-scope"
            ng-style="::$ctrl.tagStyle"
            ng-bind="::$ctrl.getDisplayName()"
            style="
              background-color: rgb(211, 84, 0);
              color: white;
              border-color: rgb(211, 84, 0);
            "
            >MD: 0.5</span
          ><!-- end ngIf: ::!$ctrl.onClick --></span
        ></tag-badge
      >
    </li>
    <!-- end ngRepeat: tagId in ::$ctrl.tagIdList track by tagId -->
    <li
      class="tag-group ng-scope"
      ng-repeat="tagId in ::$ctrl.tagIdList track by tagId"
      ng-init="tag=$ctrl.post._wrap.refMap.tagMap(tagId)"
    >
      <tag-badge tag="tag" class="ng-isolate-scope"
        ><!-- doorayTrackBy: $ctrl.uiVersion --><span
          dooray-track-by="$ctrl.uiVersion"
          class="ng-scope"
          ><!-- ngIf: ::!!$ctrl.onClick -->
          <!-- ngIf: ::!$ctrl.onClick --><span
            ng-if="::!$ctrl.onClick"
            title="QA: 필요없음"
            class="tag-badge ng-binding ng-scope"
            ng-style="::$ctrl.tagStyle"
            ng-bind="::$ctrl.getDisplayName()"
            style="
              background-color: rgb(44, 62, 80);
              color: white;
              border-color: rgb(44, 62, 80);
            "
            >QA: 필요없음</span
          ><!-- end ngIf: ::!$ctrl.onClick --></span
        ></tag-badge
      >
    </li>
    <!-- end ngRepeat: tagId in ::$ctrl.tagIdList track by tagId -->
  </ul>
</template>

<script>
export default {};
</script>

<style scoped>
ul {
  list-style: none;
}

li {
  display: inline-flex;
  margin: 4px 4px 0 0;
}
</style>
