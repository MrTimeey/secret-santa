<template>
  <v-container v-if="!groupEmpty">
    <h3 class="text-center"> Teilnehmer</h3>
    <v-row v-for="participant in participants" v-bind:key="participant.id" justify="center">
      <v-col cols="12" sm="6">
        <v-text-field label="Name" hide-details v-model="participant.name" outlined
                      disabled></v-text-field>
      </v-col>
      <v-col cols="12" sm="6">
        <v-text-field label="E-Mail" hide-details v-model="participant.mail" outlined
                      disabled></v-text-field>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import groupMixin from "@/mixins/groupMixin";

export default {
  name: "EditGroupParticipantList",
  mixins: [groupMixin],
}
</script>

<style scoped>

</style>