<template>
  <post-list-type-filter v-pre>
    <div
      class="ui-select-container ui-select-dooray-multiple select2-container ng-empty ng-valid"
      ng-class="{'select2-container-disabled': $select.disabled,'select2-allowclear': $select.allowClear &amp;&amp; !$select.isEmpty()}"
      toggle-choice=""
      search-enabled="false"
      close-on-select="false"
      uis-open-close="$ctrl.onOpenClose(isOpen)"
      on-select="$ctrl.applyFilters($item)"
      ng-model="$ctrl.model"
      is-equal-model="value"
      aria-invalid="false"
    >
      <button
        type="button"
        class="filtered-choice ui-select-match outline-none filtered-default layout-align-start-center layout-row"
        ng-class="{'filtered-default': $select.isEmpty(), 'open': $select.open}"
        ng-disabled="$select.disabled"
        ng-click="$select.toggle($event)"
        layout=""
        layout-align="start center"
        aria-label="Select box select"
        ng-transclude=""
        placeholder="전체"
      >
        <div
          layout=""
          layout-align="start center"
          class="ng-scope layout-align-start-center layout-row"
        >
          <span class="filter-label flex-none" flex="none"
            ><translate><span class="ng-scope">유형</span></translate
            >:</span
          >
          <span
            class="select2-chosen ui-select-chosen ellipsis ng-binding"
            ng-class="{'selected': !$ctrl.isDefault}"
            ng-bind="$ctrl.matchLabel"
            >전체</span
          >
          <span class="v2-icons-l-caret-down"></span>
        </div>
      </button>
      <div
        class="ui-select-dropdown select2-drop select2-with-searchbox select2-drop-active select2-display-none"
        ng-class="{'select2-display-none': !$select.open}"
      >
        <div
          class="select2-search ng-hide"
          ng-show="$select.searchEnabled"
          aria-hidden="true"
        >
          <span class="v2-icons-search-14"
            ><span class="blind" translate=""
              ><span class="ng-scope">목록 필터링</span></span
            ></span
          >
          <input
            type="text"
            autocomplete="off"
            autocorrect="false"
            autocapitalize="off"
            spellcheck="false"
            role="combobox"
            aria-expanded="true"
            aria-owns="ui-select-choices-23"
            aria-label="Select box"
            placeholder="전체"
            aria-activedescendant="ui-select-choices-row-23--1"
            class="ui-select-search select2-input ng-pristine ng-untouched ng-valid ng-empty ng-valid-maxlength"
            ng-model="$select.search"
            maxlength="1000000"
            ignore-composition-event=""
            focus-me="$select.open"
            aria-invalid="false"
          />
          <!-- ngIf: $select.search -->
        </div>
        <ul
          class="ui-select-choices ui-select-choices-content select2-results ng-scope"
          repeat="menu.value as menu in $ctrl.MENUS"
        >
          <li
            class="ui-select-choices-group"
            ng-class="{'select2-result-with-children': $select.choiceGrouped($group) }"
          >
            <div
              ng-show="$select.choiceGrouped($group)"
              class="ui-select-choices-group-label ellipsis select2-result-label ng-binding ng-hide"
              ng-bind="$group.name"
              aria-hidden="true"
            ></div>
            <ul
              id="ui-select-choices-23"
              role="listbox"
              ng-class="{'select2-result-sub': $select.choiceGrouped($group), 'select2-result-single': !$select.choiceGrouped($group) }"
              class="select2-result-single"
            >
              <!-- ngRepeat: menu in $select.items --><!-- ngIf: $select.open --><!-- end ngRepeat: menu in $select.items --><!-- ngIf: $select.open --><!-- end ngRepeat: menu in $select.items -->
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </post-list-type-filter>
</template>

<script>
export default {};
</script>
