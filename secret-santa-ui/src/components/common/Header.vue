<template>

  <v-container>
    <v-toolbar flat class="mainHeader">
      <router-link v-bind:to="{name:'Home'}">
        <v-toolbar-title class="text-uppercase grey--text">
          Secret<strong>Santa</strong>
        </v-toolbar-title>
      </router-link>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-xs-only">
        <v-btn class="text-uppercase grey--text" text v-bind:to="{name:'Home'}">{{ homeText }}</v-btn>
        <v-btn class="text-uppercase grey--text" text v-bind:to="{name:'CreateGroup'}">{{ createText }}</v-btn>
        <v-btn class="text-uppercase grey--text"
               text
               v-bind:to="{name:'EditGroup', params: {'groupId': currentGroupId}}"
               :style="!groupLoaded? 'text-decoration: line-through;':''"
               :disabled="!groupLoaded">
          {{ editText }}</v-btn>
        <v-btn class="text-uppercase grey--text" text v-bind:to="{name:'Imprint'}">{{ imprintText }}</v-btn>
      </v-toolbar-items>

      <div class="hidden-sm-and-up">
        <v-menu offset-y>
          <template v-slot:activator="{ on }">
            <v-app-bar-nav-icon class="grey--text" v-on="on"></v-app-bar-nav-icon>
          </template>
          <v-list class="responsiveMenu">
            <v-list-item>
              <v-btn class="text-uppercase grey--text" text v-bind:to="{name:'Home'}">{{ homeText }}</v-btn>
            </v-list-item>
            <v-list-item>
              <v-btn class="text-uppercase grey--text" text v-bind:to="{name:'CreateGroup'}">{{ createText }}</v-btn>
            </v-list-item>
            <v-list-item>
              <v-btn class="text-uppercase grey--text"
                     text
                     v-bind:to="{name:'EditGroup',
                     params: {'groupId': currentGroupId}}"
                     :disabled="groupLoaded">
                {{ editText }}
              </v-btn>
            </v-list-item>
            <v-list-item>
              <v-btn class="text-uppercase grey--text" text v-bind:to="{name:'Imprint'}">{{ imprintText }}</v-btn>
            </v-list-item>
          </v-list>
        </v-menu>
      </div>
    </v-toolbar>
  </v-container>
</template>

<script>
import groupMixin from "@/mixins/groupMixin";

export default {
  name: "Header",
  data: () => ({
    "homeText": "Startseite",
    "createText": "Erstellen",
    "editText": "Bearbeiten",
    "imprintText": "Impressum",
  }),
  mixins: [groupMixin],
}
</script>

<style scoped>
a {  text-decoration: none;}
</style>