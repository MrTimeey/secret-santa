import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
import group from "./group";

Vue.use(Vuex)
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
Vue.prototype.$axios = axios;

export default new Vuex.Store({
    state: {
        isLoading: false,
    },
    mutations: {
        setLoading(state, newValue) {
            state.isLoading = newValue
        },
    },
    actions: {},
    modules: {
        group
    }
})
