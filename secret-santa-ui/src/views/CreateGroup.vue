<template>
  <v-main>
    <v-container fill-height grid-list-md v-focus>
      <v-row justify="center" align="center">
        <v-col cols="12" sm="4">
          <v-row align="center" justify="space-around">
            <v-text-field
                label="Gruppennamen"
                :rules="rules"
                hide-details="auto"
                v-model="title"
                @keyup.enter="create"
                v-bind:loading="isLoading"
                maxlength="200"
                :counter="title? title.length > 100 : false"
            ></v-text-field>
          </v-row>
          <v-row align="center" justify="space-around">
            <v-btn @click="create" v-bind:disabled="title && title.length < 3 || isLoading">Anlegen</v-btn>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </v-main>

</template>

<script>

import router from "@/router";
import groupMixin from "@/mixins/groupMixin";

export default {
  name: "CreateGroup",
  data: () => ({
    title: "",
    createdId: "",
    rules: [
      value => !!value || 'Pflichtfeld.',
      value => (value && value.length >= 3) || 'Min. 3 Buchstaben',
      value => (value && value.length <= 200) || 'Max. 200 Buchstaben',
    ],
  }),
  mixins: [groupMixin],
  methods: {
    async create() {
      this.$store.commit('setLoading', true)
      await this.$store.dispatch("group/create", this.title);
      await router.push({name: 'EditGroup', params: {groupId: this.$store.state.group.currentGroup.id}})
      this.$store.commit('setLoading', false)
    }
  },

}
</script>

<style scoped>

</style>