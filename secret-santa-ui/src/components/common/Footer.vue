<template>
  <v-container grid-list-md>
    <v-footer padless>
      <v-row style="background-color: white" justify="center" align="center">
        <v-card flat class="text-center">
          <v-card-text style="padding: 5px">
            <v-btn v-for="link in links" :key="link.icon" class="mx-4" icon>
              <a :href="link.url" target="_blank">
                <v-icon size="20px">{{ link.icon }}</v-icon>
              </a>
            </v-btn>
          </v-card-text>
          <v-divider></v-divider>
          <div style="padding: 8px; font-size: 10px" class="text-uppercase grey--text">Icons made by <a href="https://icon54.com/" title="Pixel perfect">Pixel perfect</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
          <v-card-text style="padding: 8px" class="text-uppercase grey--text">
            {{ new Date().getFullYear() }} —
            Secret<strong>Santa</strong>
          </v-card-text>
        </v-card>
      </v-row>
    </v-footer>
  </v-container>
</template>

<script>
export default {
  name: "Footer",
  data: () => ({
    links: [
      {icon: "fab fa-github", url: "https://github.com/MrTimeey/secret-santa"},
      {icon: "fab fa-instagram", url: "https://www.instagram.com/mrtimeey/"},
      {icon: "fab fa-twitter", url: "https://twitter.com/tim_siegler"},
      {icon: "fab fa-xing", url: "https://www.xing.com/profile/Tim_Siegler3"}
    ]
  })
}
</script>

<style scoped>
a {  text-decoration: none; color: grey !important; }

v-card { padding: 5px; }
</style>