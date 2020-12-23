<template>

  <v-container style="max-width: 800px">
    <v-main>
      <v-container>
        <div class="block text-center notFound">
          <h1>Gruppe bearbeiten</h1>
          <p><strong>Titel:</strong> {{ currentGroup.title }}</p>
          <br>
          <EditGroupError />
          <EditGroupParticipantList />
          <EditGroupAddUserForm />
          <br>
          <EditGroupStatus />
          <v-btn v-if="!groupReleased && groupReady" @click="startGroup" v-bind:disabled="isLoading"
                 v-bind:loading="isLoading" color="primary"
                 large>Start
          </v-btn>

          <v-container>
            <v-row justify="center">
              <v-col align="right">
                <v-btn v-if="groupReleased" @click="cancelGroup" v-bind:disabled="isLoading" v-bind:loading="isLoading"
                       elevation="3" style="width:200px;">
                  <span class="text-truncate" style="width:200px;">Wichteln Abbrechen</span>
                </v-btn>
              </v-col>
              <v-col align="left" >
                <v-btn v-if="groupReleased" @click="resendMail" v-bind:disabled="isLoading" v-bind:loading="isLoading"
                       elevation="3" color="primary" style="width:200px;">
                  <span class="text-truncate" style="width:200px;">Erneut verschicken</span>
                </v-btn>
              </v-col>
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
import EditGroupError from "@/components/edit/EditGroupError";
import EditGroupParticipantList from "@/components/edit/EditGroupParticipantList";
import EditGroupAddUserForm from "@/components/edit/EditGroupAddUserForm";
import EditGroupStatus from "@/components/edit/EditGroupStatus";
import groupMixin from "@/mixins/groupMixin";

export default {
  name: "EditGroup",
  components: {EditGroupStatus, EditGroupAddUserForm, EditGroupParticipantList, EditGroupError},
  props: {
    groupId: String
  },
  data: () => ({
    currentGroup: {},
  }),
  mixins: [groupMixin],
  created() {
    this.$store.dispatch("group/loadGroup", this.groupId);
  },
  methods: {
    async startGroup() {
      this.$store.commit('setLoading', true)
      await this.$store.dispatch('group/startGroup', this.groupId);
      this.$store.commit('setLoading', false)
    },
    async resendMail() {
      this.$store.commit('setLoading', true)
      this.addUser = false
      await this.$store.dispatch('group/resendMail', this.groupId)
      this.$store.commit('setLoading', false)
    },
    async cancelGroup() {
      this.$store.commit('setLoading', true)
      this.addUser = false
      this.error = false
      await this.$store.dispatch('group/cancelGroup', this.groupId)
      this.$store.commit('setLoading', false)
    }
  }
}
</script>

<style scoped>

</style>