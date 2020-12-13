<template>

  <v-app class="grey lighten-4">
    <v-app-bar flat app color="white">
      <Header/>
    </v-app-bar>
    <v-main>
      <router-view></router-view>
    </v-main>
    <v-footer app flat color="white">
      <Footer />
    </v-footer>
  </v-app>
</template>

<script>

import Header from "@/components/common/Header";
import Footer from "@/components/common/Footer";

export default {

  name: 'App',

  components: {
    Footer,
    Header
  },
};
</script>
