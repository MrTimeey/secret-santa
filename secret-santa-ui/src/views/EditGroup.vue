<template>

  <v-container>
    <v-main>
      <v-container>
        <div class="block text-center notFound">
          <h1>Gruppe bearbeiten</h1>
          <p><strong>Titel:</strong> {{ currentGroup.title }}</p>
          <hr>
          <br>
          <ul v-for="participant in currentGroup.participants" v-bind:key="participant.id">
            <li>{{ participant.name }} - {{ participant.mail }}</li>
          </ul>
          <hr>
          <v-form v-model="isFormValid">
            <v-text-field label="Name" :rules="rules" hide-details="auto" v-model="personName"
                          v-bind:loading="loading"></v-text-field>
            <v-text-field label="E-Mail" :rules="rules && emailRules" hide-details="auto" v-model="personMail"
                          v-bind:loading="loading"></v-text-field>
          </v-form>
          <v-btn @click="create" v-bind:disabled="!isFormValid || loading">Anlegen</v-btn>
          <br>
          <hr>
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
    currentGroup: "",
    loading: false,
    personName: "",
    personMail: "",
    isFormValid: false,
    rules: [
      value => !!value || 'Required.',
      value => (value && value.length >= 3) || 'Min 3 characters',
    ],
    emailRules: [
      v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail must be valid'
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

    }
  }
}
</script>

<style scoped>

</style>