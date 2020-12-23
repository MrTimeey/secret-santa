<template>

  <v-container style="max-width: 800px">
    <v-main>
      <v-container>
        <div class="block text-center notFound">
          <h1>Gruppe bearbeiten</h1>
          <p><strong>Titel:</strong> {{ currentGroup.title }}</p>
          <br>
          <EditGroupError v-bind:participants="currentGroup.participants ? currentGroup.participants: []" v-bind:groupReleased="groupReleased === true"/>
          <EditGroupParticipantList :participants="currentGroup.participants ? currentGroup.participants: []"/>
          <EditGroupAddUserForm v-bind:groupReleased="groupReleased === true" v-bind:group-id="this.groupId"/>
          <br>
          <EditGroupStatus v-bind:group-ready="groupReady" v-bind:group-released="groupReleased"/>
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
import {baseUrl} from "@/main";
import {bus} from "@/main";
import EditGroupError from "@/components/edit/EditGroupError";
import EditGroupParticipantList from "@/components/edit/EditGroupParticipantList";
import EditGroupAddUserForm from "@/components/edit/EditGroupAddUserForm";
import EditGroupStatus from "@/components/edit/EditGroupStatus";

export default {
  name: "EditGroup",
  components: {EditGroupStatus, EditGroupAddUserForm, EditGroupParticipantList, EditGroupError},
  props: {
    groupId: String
  },
  data: () => ({
    currentGroup: {},
  }),
  created() {
    bus.$on('addedUser', (user) => this.currentGroup.participants.push(user))
  },
  computed: {
    groupReady: function () {
      return !this.groupReleased && this.currentGroup.participants && Object.keys(this.currentGroup.participants).length > 1
    },
    groupReleased: function () {
      return this.currentGroup.released
    }
  },
  mounted() {
    this.reloadGroup(this.groupId)
  },
  methods: {
    async reloadGroup(groupId) {
      let response = await this.$axios.get(baseUrl + 'group/' + groupId)
      this.currentGroup = response.data
    },
    async startGroup() {
      this.$store.commit('setLoading', true)
      this.addUser = false
      try {
        await this.$axios.post(baseUrl + 'group/' + this.groupId + '/release', {})
        await this.reloadGroup(this.groupId);
      } catch (e) {
        console.error(e)
      }
      this.$store.commit('setLoading', false)
    },
    async resendMail() {
      this.$store.commit('setLoading', true)
      this.addUser = false
      try {
        await this.$axios.post(baseUrl + 'group/' + this.groupId + '/resend', {})
        await this.reloadGroup(this.groupId);
      } catch (e) {
        console.error(e)
      }
      this.$store.commit('setLoading', false)
    },
    async cancelGroup() {
      this.$store.commit('setLoading', true)
      this.addUser = false
      this.error = false
      try {
        await this.$axios.post(baseUrl + 'group/' + this.groupId + '/cancel', {})
        await this.reloadGroup(this.groupId)
      } catch (e) {
        console.error(e)
      }
      this.$store.commit('setLoading', false)
    }
  }
}
</script>

<style scoped>

</style>