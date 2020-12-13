<template>

  <v-container>
    <v-toolbar flat class="mainHeader">
      <router-link v-bind:to="{name:'Home'}">
        <v-toolbar-title class="text-uppercase grey--text">
          Secret<b>Santa</b>
        </v-toolbar-title>
      </router-link>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-xs-only">
        <v-btn text v-bind:to="{name:'Home'}">Home</v-btn>
        <v-btn text v-bind:to="{name:'CreateGroup'}">Start</v-btn>
      </v-toolbar-items>

      <div class="hidden-sm-and-up">
        <v-menu offset-y>
          <template v-slot:activator="{ on }">
            <v-app-bar-nav-icon v-on="on"></v-app-bar-nav-icon>
          </template>
          <v-list class="responsiveMenu">
            <v-list-item>
              <v-btn text v-bind:to="{name:'Home'}">Home</v-btn>
            </v-list-item>
            <v-list-item>
              <v-btn text v-bind:to="{name:'CreateGroup'}">Start</v-btn>
            </v-list-item>
          </v-list>
        </v-menu>
      </div>
    </v-toolbar>
  </v-container>
</template>

<script>
export default {
  name: "Header"
}
</script>

<style scoped>
a {  text-decoration: none;}
</style>