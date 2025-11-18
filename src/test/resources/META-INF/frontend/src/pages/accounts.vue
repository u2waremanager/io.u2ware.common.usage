<template>
  <v-app id="inspire">
    <v-app-bar app>
      <router-link to="/">
        <U2wareAvatar></U2wareAvatar>
      </router-link>

      <v-toolbar-title> {{ $t("accounts.bar.title") }}</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn text variant="elevated" color="primary" to="/">
        <v-icon>mdi-home</v-icon>
      </v-btn>
    </v-app-bar>

    <U2wareFooter></U2wareFooter>

    <v-main>

      <router-view />

    </v-main>
  </v-app>



</template>

<script>
const x = "[/accounts]";

export default {
  data: () => ({
    
  }),

  computed: {
  },

  watch: {},

  methods: {},

  mounted() {
    console.log(x, "mounted()", this.$route.matched.length);
    if (this.$route.matched.length < 2) {
      this.$router.push("/accounts/login");
    }
  },
};
</script>