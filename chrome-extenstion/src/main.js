import Vue from 'vue';
import App from './App.vue';

import store from '@/store';
import '@/config/axios';
import '@/config/bootstrap';
import '@/config/plugins';
import '@/assets/main.css';

Vue.config.productionTip = false;

let currentPath = '';
let topHeaderDiv = null;
let originalSection = null;
let parentSection = null;
let section = null;
let divider = null;
let button = null;

setInterval(() => {
  topHeaderDiv = document.querySelector(
    '#main-wrapper > section > section > section > project-contents-layout > project-contents-header > div > project-contents-type-selector > div'
  );
  if (topHeaderDiv != null) {
    if (window.location.pathname !== currentPath) {
      currentPath = window.location.pathname;
      if (section != null) {
        parentSection.removeChild(section);
        originalSection.style.display = 'flex';
      }

      if (button != null) {
        topHeaderDiv.removeChild(divider);
        topHeaderDiv.removeChild(button);
      }
    }

    if (document.getElementById('dooray-project-board-manager')) {
      return;
    }

    section = document.createElement('section');
    section.id = 'dooray-project-board-manager';
    section.className = 'main-contents-body layout-row';
    section.style.display = 'none';
    section.style.height = '100%';

    let app = document.createElement('div');
    section.appendChild(app);

    originalSection = document.querySelector('.main-contents-body');
    parentSection = originalSection.parentElement;
    parentSection.appendChild(section);

    new Vue({
      el: app,
      store,
      render: (h) => h(App),
    });

    divider = document.createElement('div');
    divider.className = 'divide-bar';
    topHeaderDiv.appendChild(divider);

    button = document.createElement('button');
    button.innerText = '특수보드';
    button.addEventListener('click', () => {
      topHeaderDiv
        .getElementsByTagName('button')
        .forEach((child) => (child.className = ''));
      button.className = 'active';

      originalSection.style.display = 'none';
      section.style.display = 'flex';
    });
    topHeaderDiv.appendChild(button);

    topHeaderDiv.getElementsByTagName('button').forEach((child) =>
      child.addEventListener('click', () => {
        if (child !== button) {
          child.className = 'active';
          button.className = '';
          section.style.display = 'none';
          originalSection.style.display = 'flex';
        }
      })
    );
  }
}, 1000);
