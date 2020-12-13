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

export default {

  name: 'App',

  components: {
    Header
  },
};
</script>
