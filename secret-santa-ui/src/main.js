import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import axios from "axios";
import store from './store'
import loadingMixin from "@/mixins/loadingMixin";

Vue.config.productionTip = false

axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
Vue.prototype.$axios = axios;

Vue.mixin(loadingMixin);

Vue.prototype.$eventBus =  new Vue();
export const bus = new Vue();

export const baseUrl = process.env.NODE_ENV === "production" ? 'https://' + location.host + '/api/' : 'http://localhost:8084/';

new Vue({
  router,
  vuetify,
  store,
  render: h => h(App)
}).$mount('#app')
