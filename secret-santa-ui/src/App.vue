<template>

  <v-app class="grey lighten-4" >
    <v-app-bar flat app :style="backColour">
      <Header/>
    </v-app-bar>
    <v-main>
      <router-view></router-view>
    </v-main>
    <v-footer app flat :style="backColour">
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
  computed: {
    backColour() {
      return this.$vuetify.theme.dark === true ? 'background-color: ' : 'background-color: white';
    }
  }
};
</script>
