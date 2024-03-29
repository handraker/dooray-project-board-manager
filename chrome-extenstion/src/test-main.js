import Vue from 'vue';
import App from './App.vue';

import store from '@/store';
import '@/assets/main.css';
import '@/config/axios';
import '@/config/bootstrap';

Vue.config.productionTip = false;

new Vue({
  store,
  render: (h) => h(App),
}).$mount('#app');
