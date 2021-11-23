import Vue from 'vue';
import App from './App.vue';

import store from '@/store';
import '@/config/axios';
import '@/config/bootstrap';
import '@/config/plugins';
import '@/assets/main.css';

Vue.config.productionTip = false;

let topHeaderDiv = null;
let originalSection = null;
let parentSection = null;
let section = null;
let divider = null;
let button = null;
let app = null;
let vueApp = null;

setInterval(() => {
  topHeaderDiv = document.querySelector(
    '#main-wrapper > section > section > section > project-contents-layout > project-contents-header > div > project-contents-type-selector > div'
  );
  if (topHeaderDiv != null) {
    if (document.getElementById('dooray-project-board-manager-btn')) {
      return;
    }
    originalSection = document.querySelector('.main-contents-body');

    divider = document.createElement('div');
    divider.className = 'divide-bar';
    topHeaderDiv.appendChild(divider);

    button = document.createElement('button');
    button.id = 'dooray-project-board-manager-btn';
    button.innerText = '특수보드';
    button.addEventListener('click', () => {
      topHeaderDiv
        .getElementsByTagName('button')
        .forEach((child) => (child.className = ''));
      button.className = 'active';

      originalSection.style.display = 'none';

      section = document.createElement('section');
      section.id = 'dooray-project-board-manager';
      section.className = 'main-contents-body layout-row';
      section.style.display = 'flex';
      section.style.height = '100%';

      app = document.createElement('div');
      section.appendChild(app);

      parentSection = originalSection.parentElement;
      parentSection.appendChild(section);

      vueApp = new Vue({
        el: app,
        store,
        render: (h) => h(App),
      });
    });
    topHeaderDiv.appendChild(button);

    topHeaderDiv.addEventListener('click', (e) => {
      const btn = e.path.find((elem) => elem.tagName === 'BUTTON');
      if (e.target !== button && btn !== undefined) {
        button.className = '';
        originalSection.style.display = 'flex';

        btn.className = 'active';

        vueApp.$destroy();
        document.getElementById('dooray-project-board-manager').remove();
      }
    });
  }
}, 1000);
