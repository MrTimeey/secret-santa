import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import axios from "axios";

Vue.config.productionTip = false
Vue.prototype.$axios = axios;

export const baseUrl = process.env.NODE_ENV === "production" ? 'https://' + location.host + '/api/' : 'http://localhost:8084/';

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
