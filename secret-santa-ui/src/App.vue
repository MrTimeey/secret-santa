<template>

  <v-app class="grey lighten-4">
    <v-app-bar flat app color="white">
      <Header/>
    </v-app-bar>
    <v-main>
      <router-view></router-view>
    </v-main>
    <!-- Add 'app' for fixed below content -->
    <v-footer flat color="white">
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
