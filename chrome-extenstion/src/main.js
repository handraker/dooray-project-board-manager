import Vue from 'vue';
import App from './App.vue';

import store from '@/store';
import '@/config/axios';
import '@/config/bootstrap';
import '@/config/plugins';
import '@/assets/main.css';
import eventBus from '@/EventBus';
import clockifyHandler from '@/clockify';

Vue.config.productionTip = false;

let originalSection = null;
let vueSection = null;
let vueApp = null;

setInterval(() => {
  clockifyHandler();

  let topHeaderDiv = document.querySelector(
    '#main-wrapper > section > section > section > project-contents-layout > project-contents-header > div > project-contents-type-selector > div'
  );

  if (topHeaderDiv === null) {
    return;
  }

  if (originalSection === null) {
    originalSection = document.querySelector('.main-contents-body');
  }

  if (originalSection === null) {
    return;
  }

  if (vueApp === null) {
    vueSection = document.createElement('section');
    vueSection.id = 'dooray-project-board-manager';
    vueSection.className = 'main-contents-body layout-row';
    vueSection.style.display = 'none';
    vueSection.style.height = '100%';

    let app = document.createElement('div');
    vueSection.appendChild(app);

    let parentSection = originalSection.parentElement;
    parentSection.appendChild(vueSection);

    vueApp = new Vue({
      el: app,
      store,
      render: (h) => h(App),
    });
  }

  if (document.getElementById('dooray-project-board-manager-btn')) {
    return;
  }

  let divider = document.createElement('div');
  divider.className = 'divide-bar';
  topHeaderDiv.appendChild(divider);

  let button = document.createElement('button');
  button.id = 'dooray-project-board-manager-btn';
  button.innerText = '특수보드';
  button.addEventListener('click', () => {
    topHeaderDiv
      .getElementsByTagName('button')
      .forEach((child) => (child.className = ''));
    button.className = 'active';

    vueSection.style.display = 'flex';
    originalSection.style.display = 'none';

    eventBus.$emit('show-board');
  });
  topHeaderDiv.appendChild(button);

  topHeaderDiv.addEventListener('click', (e) => {
    const btn = e.path.find((elem) => elem.tagName === 'BUTTON');
    if (e.target !== button && btn !== undefined) {
      button.className = '';
      vueSection.style.display = 'none';
      originalSection.style.display = 'flex';

      btn.className = 'active';
    }
  });
}, 1000);
