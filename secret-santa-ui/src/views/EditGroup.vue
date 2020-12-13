<template>

  <v-container>
    <v-main>
      <v-container>
        <div class="block text-center notFound">
          <h1>Gruppe bearbeiten</h1>
          <p><strong>Titel:</strong> {{ currentGroup.title }}</p>
          <hr>
          <br>
          Here goes participants stuff
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
    currentGroup: ""
  }),
  mounted() {
    this.getGroup(this.groupId)
  },
  methods: {
    async getGroup(groupId) {
      let response = await this.$axios.get(baseUrl + 'group/' + groupId)
      this.currentGroup = response.data
    }
  }
}
</script>

<style scoped>

</style>