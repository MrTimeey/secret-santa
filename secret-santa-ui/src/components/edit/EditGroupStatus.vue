<template>
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
</template>

<script>
export default {
  name: "EditGroupStatus",
  props: {
    groupReleased: Boolean,
    groupReady: Boolean
  }
}
</script>

<style scoped>

</style>