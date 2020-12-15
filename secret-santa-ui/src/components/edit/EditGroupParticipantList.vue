<template>
  <v-container v-if="!groupEmpty">
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
export default {
  name: "EditGroupParticipantList",
  props: {
    participants: {
      type: Array,
      required: true
    }
  },
  computed: {
    groupEmpty: function () {
      return !this.participants || Object.keys(this.participants).length === 0
    }
  }
}
</script>

<style scoped>

</style>