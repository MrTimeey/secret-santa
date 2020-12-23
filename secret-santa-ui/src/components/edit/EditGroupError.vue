<template>
  <v-container style="color: darkred" v-if="groupReleased && containsMailFailures" >
    <v-row justify="center"><strong>Fehler beim Mail Versand!</strong></v-row>
    <v-row justify="center">Folgende Mails konnten nicht versendet werden: {{ failedMails }}</v-row>
    <br>
    <v-row justify="center">Aktuell besteht leider keine Möglichkeit einzelne Mails erneut zu verschicken.</v-row>
    <v-row justify="center">Um das Problem zu beheben musst du die Gruppe abbrechen, anschließend eine neue starten.</v-row>
    <br>
    <v-row justify="center">Aktuell arbeiten wir daran, diesen Prozess zu vereinfachen.</v-row>
  </v-container>
</template>

<script>
import groupStateMixin from "@/mixins/groupMixin";

export default {
  name: "EditGroupError",
  mixins: [groupStateMixin],
  computed: {
    containsMailFailures: function () {
      return this.$store.getters["group/errorParticipants"].length > 0
    },
    failedMails: function () {
      return this.$store.getters["group/errorParticipants"]
          .map(person => person.name.concat(" (", person.mail, ")"))
          .join(', ')
    }
  }
}
</script>

<style scoped>

</style>