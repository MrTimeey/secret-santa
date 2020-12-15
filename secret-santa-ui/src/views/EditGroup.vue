<template>

  <v-container style="max-width: 800px">
    <v-main>
      <v-container>
        <div class="block text-center notFound">
          <h1>Gruppe bearbeiten</h1>
          <p><strong>Titel:</strong> {{ currentGroup.title }}</p>
          <br>
          <v-container style="color: darkred" v-if="containsMailFailures" >
            <v-row justify="center"><strong>Fehler beim Mail Versand!</strong></v-row>
            <v-row justify="center">Folgende Mails konnten nicht versendet werden: {{ failedMails }}</v-row>
            <br>
            <v-row justify="center">Aktuell besteht leider keine Möglichkeit einzelne Mails erneut zu verschicken.</v-row>
            <v-row justify="center">Um das Problem zu beheben musst du die Gruppe abbrechen, die Mail korrigieren und anschließend erneut starten.</v-row>
            <br>
            <v-row justify="center">Aktuell arbeiten wir daran, diesen Prozess zu vereinfachen.</v-row>
          </v-container>

          <v-container v-if="!groupEmpty">
            <v-row v-for="participant in currentGroup.participants" v-bind:key="participant.id" justify="center">
              <v-col cols="12" sm="6">
                <v-text-field label="Name" hide-details v-model="participant.name" outlined
                              disabled></v-text-field>
              </v-col>
              <v-col cols="12" sm="6">
                <v-text-field label="E-Mail" hide-details v-model="participant.mail" outlined
                              disabled></v-text-field>
              </v-col>
              <!--<v-col cols="1" sm="1">
                <v-icon size="20px">fa-edit</v-icon>
                <v-icon size="20px">fa-trash-alt</v-icon>
              </v-col>-->
            </v-row>
          </v-container>

          <v-form ref="form" v-model="isFormValid" v-if="addUser">
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
              <v-btn @click="addUser = !addUser" v-bind:disabled="loading">Abbrechen</v-btn>
              <v-btn @click="create" v-bind:disabled="!isFormValid || loading">Anlegen</v-btn>
            </v-container>
          </v-form>
          <v-btn v-if="!addUser && !currentGroup.released" @click="addUser = !addUser" v-bind:disabled="loading">
            Hinzufügen
          </v-btn>
          <br>
          <div>
            <br>
            <strong>Status:</strong>
            <v-row justify="center" align-content="center">
              <p v-if="groupReleased">
                <v-icon size="20px">fa-check</v-icon>
                Gruppe informiert!
              </p>
              <p v-else-if="!groupReady">
                <v-icon size="20px">fa-stop-circle</v-icon>
                Zu wenig Teilnehmer
              </p>
              <p v-else>
                <v-icon size="20px">fa-hourglass-half</v-icon>
                Gruppe bereit
              </p>
            </v-row>
          </div>
          <v-btn v-if="!groupReleased && groupReady" @click="startGroup" v-bind:disabled="loading"
                 v-bind:loading="loading" color="primary"
                 large>Start
          </v-btn>
          <v-container>
            <v-row justify="center" class="pa-5">
                <v-btn v-if="groupReleased" @click="cancelGroup" v-bind:disabled="loading" v-bind:loading="loading"
                       color="error"
                >Wichteln Abbrechen
                </v-btn>
                <v-btn v-if="groupReleased" @click="resendMail" v-bind:disabled="loading" v-bind:loading="loading"
                       color="primary"
                >Erneut verschicken
                </v-btn>
            </v-row>
          </v-container>
          <br>
          <br>
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
    addUser: false,
    rules: [
      value => !!value || 'Pflichtfeld.',
      value => (value && Object.keys(value).length >= 3) || 'Min. 3 Buchstaben',
    ],
    emailRules: [
      value => !!value || 'Pflichtfeld.',
      v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-Mail muss valide sein'
    ]
  }),
  computed: {
    groupReady: function () {
      return !this.groupReleased && this.currentGroup.participants && Object.keys(this.currentGroup.participants).length > 1
    },
    groupEmpty: function () {
      return !this.currentGroup.participants || Object.keys(this.currentGroup.participants).length === 0
    },
    groupReleased: function () {
      return this.currentGroup.released
    },
    containsMailFailures: function () {
      return this.currentGroup.participants && this.currentGroup.participants
          .filter(person => person.mailSend === false).length > 0
    },
    failedMails: function () {
      return this.currentGroup.participants
          .filter(person => person.mailSend === false)
          .map(person => person.name.concat(" (", person.mail, ")"))
          .join(', ')
    }
  },
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
      this.$refs.form.reset()
      this.loading = false
    },
    async startGroup() {
      this.loading = true
      this.addUser = false
      try {
        await this.$axios.post(baseUrl + 'group/' + this.groupId + '/release', {})
        await this.getGroup(this.groupId);
      } catch (e) {
        console.error(e)
      }
      this.loading = false
    },
    async resendMail() {
      this.loading = true
      this.addUser = false
      try {
        await this.$axios.post(baseUrl + 'group/' + this.groupId + '/resend', {})
        await this.getGroup(this.groupId);
      } catch (e) {
        console.error(e)
      }
      this.loading = false
    },
    async cancelGroup() {
      this.loading = true
      this.addUser = false
      this.error = false
      try {
        await this.$axios.post(baseUrl + 'group/' + this.groupId + '/cancel', {})
        await this.getGroup(this.groupId)
      } catch (e) {
        console.error(e)
      }
      this.loading = false
    }
  }
}
</script>

<style scoped>

</style>