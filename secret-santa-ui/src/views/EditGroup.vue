<template>

  <v-container style="max-width: 800px">
    <v-main>
      <v-container>
        <div class="block text-center notFound">
          <h1>Gruppe bearbeiten</h1>
          <p><strong>Titel:</strong> {{ currentGroup.title }}</p>
          <br>

          <v-container>
            <v-row v-for="participant in currentGroup.participants" v-bind:key="participant.id">
              <v-col cols="12" sm="6">
                <v-text-field label="Name" hide-details="auto" v-model="participant.name" outlined disabled></v-text-field>
              </v-col>
              <v-col cols="12" sm="6">
                <v-text-field label="E-Mail" hide-details="auto" v-model="participant.mail" outlined disabled></v-text-field>
              </v-col>
            </v-row>
          </v-container>

          <v-form ref="form" v-model="isFormValid">

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

          </v-form>
          <v-btn @click="create" v-bind:disabled="!isFormValid || loading">Anlegen</v-btn>
          <br>
          <div>
            <br>
            <strong>Status:</strong>
            <v-row justify="center" align-content="center">
              <p v-if="currentGroup.released">
                <v-icon size="20px">fa-check</v-icon>
                Gruppe bereits informiert!
              </p>
              <p v-else-if="!currentGroup.participants || currentGroup.participants.length <= 1">
                <v-icon size="20px">fa-stop-circle</v-icon>
                Zu wenig Teilnehmer
              </p>
              <p v-else>
                <v-icon size="20px">fa-hourglass-half</v-icon>
                Gruppe bereit
              </p>
            </v-row>
          </div>
          <p><strong>Vorsicht!</strong> <br>Die Gruppe kann nur unter dieser URL bearbeitet werden.<br>
            Wer über den Link verfügt kann Änderungen vornehmen.</p>
        </div>
      </v-container>
    </v-main>
  </v-container>
</template>

<script>
import {baseUrl} from "@/main";

export default {
  name: "EditGroup",
  props: {
    groupId: String
  },
  data: () => ({
    currentGroup: {},
    loading: false,
    personName: "",
    personMail: "",
    isFormValid: false,
    rules: [
      value => !!value || 'Pflichtfeld.',
      value => (value && value.length >= 3) || 'Min. 3 Buchstaben',
    ],
    emailRules: [
      value => !!value || 'Pflichtfeld.',
      v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-Mail muss valide sein'
    ]
  }),
  mounted() {
    this.getGroup(this.groupId)
  },
  methods: {
    async getGroup(groupId) {
      let response = await this.$axios.get(baseUrl + 'group/' + groupId)
      this.currentGroup = response.data
    },
    async create() {
      this.loading = true
      let data = {
        "name": this.personName,
        "mail": this.personMail,
        "secretSantaGroupId": this.groupId
      }
      let response = await this.$axios.post(baseUrl + 'person', data)
      this.currentGroup.participants.push(response.data)
      console.log(response.data)
      this.$refs.form.reset()
      this.loading = false
    }
  }
}
</script>

<style scoped>

</style>