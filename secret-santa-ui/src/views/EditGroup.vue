<template>

  <v-container>
    <v-content>
      <v-container>
        <div class="block text-center notFound">
          <h1>{{this.groupId}}</h1>
        </div>
      </v-container>
    </v-content>
  </v-container>
</template>

<script>
export default {
  name: "EditGroup",
  props: {
    groupId: String
  }
}
</script>

<style scoped>

</style>