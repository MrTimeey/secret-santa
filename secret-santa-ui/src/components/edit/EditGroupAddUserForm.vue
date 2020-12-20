<template>
  <div>
    <v-form ref="form" v-model="isFormValid" v-if="addition">
      <v-container>
        <v-row>
          <v-col cols="12" sm="6">
            <v-text-field label="Name" :rules="rules" hide-details="auto" v-model="personName"
                          v-bind:loading="loading"></v-text-field>
          </v-col>
          <v-col cols="12" sm="6">
            <v-text-field label="E-Mail" :rules="emailRules" hide-details="auto" v-model="personMail"
                          v-bind:loading="loading"></v-text-field>
          </v-col>
        </v-row>
      </v-container>
      <v-container>
        <v-row justify="center">
          <v-col align="right">
            <v-btn @click="addition = !addition" v-bind:disabled="loading" style="width:130px;">
              <span class="text-truncate" style="width:130px;">Abbrechen</span>
            </v-btn>
          </v-col>
          <v-col align="left">
            <v-btn @click="addUser" v-bind:disabled="!isFormValid || loading" style="width:130px;">
              <span class="text-truncate" style="width:130px;">Anlegen</span>
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
    <v-btn v-if="!addition && !groupReleased" @click="addition = !addition" v-bind:disabled="loading">
      Hinzufügen
    </v-btn>
  </div>
</template>

<script>
import {baseUrl, bus} from "@/main";

export default {
  name: "EditGroupAddUserForm",
  data: () => ({
    isFormValid: false,
    addition: false,
    personName: "",
    personMail: "",
    rules: [
      value => !!value || 'Pflichtfeld.',
      value => (value && Object.keys(value).length >= 3) || 'Min. 3 Buchstaben',
    ],
    emailRules: [
      value => !!value || 'Pflichtfeld.',
      v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-Mail muss valide sein'
    ],
  }),

  props: {
    groupId: String,
    groupReleased: {
      type: Boolean,
      required: true
    },
    loading: {
      type: Boolean,
      required: true
    }
  },

  methods: {
    async addUser() {
      let data = {
        "name": this.personName,
        "mail": this.personMail,
        "secretSantaGroupId": this.groupId
      }
      try {
        let response = await this.$axios.post(baseUrl + 'person', data)
        bus.$emit('addedUser', response.data)
        this.$refs.form.reset()
      } catch (e) {
        console.error(e);
      }
    },
  }
}
</script>

<style scoped>

</style>