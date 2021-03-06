import {baseUrl} from "@/main";
import * as axios from "axios";

let state = {
    currentGroup: {},
    groupError: false
};
let actions = {
    async loadGroup({commit}, id) {
        try {
            let response = await axios.get(baseUrl + 'group/' + id)
            commit('setGroup', response.data);
        } catch (e) {
            commit('setError', true);
        }

    },
    async startGroup({commit}, id) {
        let response = await axios.post(baseUrl + 'group/' + id + '/release', {})
        commit('setGroup', response.data);
    },
    async cancelGroup({commit}, id) {
        let response = await axios.post(baseUrl + 'group/' + id + '/cancel', {})
        commit('setGroup', response.data);
    },
    async resendMail({commit}, id) {
        let response = await axios.post(baseUrl + 'group/' + id + '/resend', {})
        commit('setGroup', response.data);
    },
    async create({commit}, payload) {
        let body = {
            'title': payload
        }
        let config = {
            headers: {
                'Content-Type': 'application/json',
            }
        }
        let response = await axios.post(baseUrl + "group/", body, config)
        commit('setGroup', response.data);
    },
    async addUser({commit}, {name, mail}) {
        let body = {
            "name": name,
            "mail": mail,
            "secretSantaGroupId": state.currentGroup.id
        }
        try {
            let response = await axios.post(baseUrl + 'person', body)
            commit('addUser', response.data)
        } catch (e) {
            console.error(e)
        }
    }
};
let mutations = {
    setGroup(state, group) {
        state.currentGroup = group
    },
    setError(state, status) {
        state.groupError = status
    },
    addUser(state, user) {
        state.currentGroup.participants.push(user);
    }
};
let getters = {
    participants: state => { return state.currentGroup.participants},
    errorParticipants: state => { return state.currentGroup.participants.filter(person => person.mailSend === false)},
};
let modules = {};


export default {
    namespaced: true,
    state,
    actions,
    mutations,
    getters,
    modules
}