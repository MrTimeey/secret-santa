<template>

  <v-app class="grey lighten-4">
    <v-app-bar flat app color="white">
      <Header/>
    </v-app-bar>
    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>

import Header from "@/components/common/Header";

const url = 'http://' + location.host + '/api/';

export default {

  name: 'App',

  components: {
    Header
  },
  methods: {
    async get() {
      let response = await this.$axios.get(url + "group/abvgcehcjbwrf")
      this.getRes = response.data
    },

    async send() {
      var data = {
        'title': 'Silvester in der Spielegruppe'
      }
      let response = await this.$axios.post(url + "group/", data)
      this.createdId = response.data.id
    }
  },

  data: () => ({
    createdId: "",
    getRes: "",
  }),
};
</script>
