<template>
  <v-content>
    <v-container fill-height grid-list-md>
      <v-row justify="center" align="center">
        <v-col cols="12" sm="4">
          <v-row align="center" justify="space-around">
            <v-text-field
                label="Gruppennamen"
                :rules="rules"
                hide-details="auto"
                v-model="title"
                @keyup.enter="create"
            ></v-text-field>
          </v-row>
          <v-row align="center" justify="space-around">
            <v-btn @click="create" v-bind:disabled="title && title.length < 3">Anlegen</v-btn>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </v-content>

</template>

<script>
import {baseUrl} from "@/main";
import router from "@/router";

export default {
  name: "CreateGroup",
  data: () => ({
    title: "",
    createdId: "",
    rules: [
      value => !!value || 'Required.',
      value => (value && value.length >= 3) || 'Min 3 characters',
    ],
  }),
  methods: {
    async create() {
      let data = {
        'title': this.title
      }
      let config = {
        headers: {
          'Access-Control-Allow-Origin': '*'
        }
      }
      let response = await this.$axios.post(baseUrl + "group/", data, config)
      await router.push({ name: 'EditGroup', params: {groupId: response.data.id } })
    }
  }
}
</script>

<style scoped>
.v-text-field{
  width: 400px;
}
.v-btn{
  margin-left: 1px;
}
</style>