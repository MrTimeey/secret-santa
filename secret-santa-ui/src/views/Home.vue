<template>
  <v-main>
    <v-container>
      <div class="block text-center notFound">
        <div class="home">
          <h1>Herzlich Willkommen!</h1>
          <br>
          <br>
          <p>Du willst auch in aktuellen Zeiten gerne mit deinen Liebsten wichteln, kommst allerdings nicht zusammen um die Gruppen auszulosen?</p>
          <p>Dafür gibts hier die Lösung! Lege dir einfach eine Gruppe an, füg deine Freunde hinzu und schon kann es losgehen!</p>
          <br>
          <p><b>Hier gehts <router-link v-bind:to="{name:'CreateGroup'}">los</router-link>!</b></p>
        </div>
        <br>
        <br>
        <v-switch
            v-model="darkMode"
            label="Dark-Mode"
        ></v-switch>
      </div>
    </v-container>
  </v-main>

</template>

<script>

export default {
  name: 'Home',
  computed: {
    darkMode: {
      get() { return this.$vuetify.theme.dark },
      set(newValue) {
        this.$vuetify.theme.dark = newValue
      }
    }
  }
}
</script>
