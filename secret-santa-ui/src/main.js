import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import store from './store'
import loadingMixin from "@/mixins/loadingMixin";

Vue.config.productionTip = false

Vue.mixin(loadingMixin);

export const baseUrl = process.env.NODE_ENV === "production" ? 'https://' + location.host + '/api/' : 'http://localhost:8084/';

Vue.directive('focus', {
  inserted: function (el) {
    el.focus();
  }
})

new Vue({
  router,
  vuetify,
  store,
  render: h => h(App)
}).$mount('#app')
