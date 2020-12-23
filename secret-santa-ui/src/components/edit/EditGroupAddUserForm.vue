<template>
  <div>
    <v-form ref="form" v-model="isFormValid" v-if="addition">
      <v-container>
        <v-row>
          <v-col cols="12" sm="6">
            <v-text-field label="Name"
                          :rules="this.rules.concat((this.uniqueUserName === true)  || 'Name muss eindeutig sein')"
                          hide-details="auto"
                          v-model="personName"
                          v-bind:loading="isLoading" maxlength="100"
                          :counter="personName? personName.length > 50 : false"></v-text-field>
          </v-col>
          <v-col cols="12" sm="6">
            <v-text-field label="E-Mail"
                          :rules="emailRules.concat((this.uniqueUserMail === true) || 'Mail muss eindeutig sein')"
                          hide-details="auto"
                          v-model="personMail"
                          :loading="isLoading"
                          maxlength="100"
                          :counter="personMail? personMail.length > 50 : false"></v-text-field>
          </v-col>
        </v-row>
      </v-container>
      <v-container>
        <v-row justify="center">
          <v-col align="right">
            <v-btn @click="addition = !addition" v-bind:disabled="isLoading" style="width:130px;">
              <span class="text-truncate" style="width:130px;">Abbrechen</span>
            </v-btn>
          </v-col>
          <v-col align="left">
            <v-btn @click="addUser" v-bind:disabled="!isFormValid || isLoading" style="width:130px;">
              <span class="text-truncate" style="width:130px;">Anlegen</span>
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
    <v-btn v-if="!addition && !groupReleased" @click="addition = !addition" v-bind:disabled="isLoading">
      Hinzufügen
    </v-btn>
  </div>
</template>

<script>

import groupMixin from "@/mixins/groupMixin";

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
      value => (value && Object.keys(value).length <= 100) || 'Max. 100 Buchstaben',
    ],
    emailRules: [
      value => !!value || 'Pflichtfeld.',
      v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-Mail muss valide sein'
    ],
  }),
  mixins: [groupMixin],
  computed: {
    uniqueUserName() {
      return () => this.participants && !this.participants.map(p => p.name).includes(this.personName);
    },
    uniqueUserMail() {
      return this.participants && this.participants.map(p => p.mail).contains(this.personMail);
    }
  },
  methods: {
    async addUser() {
      let payload = {'name': this.personName, 'mail': this.personMail}
      await this.$store.dispatch("group/addUser", payload);
      this.$refs.form.reset()
      this.addition = false
    },
  }
}
</script>

<style scoped>

</style>