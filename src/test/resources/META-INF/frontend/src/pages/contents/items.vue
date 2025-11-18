<template>

items

</template>

<script>
const x = "[/contents/items]";
import $contentsState from "@/assets/stores/contents.js";

export default {
  data: () => ({
    
  }),

  computed: {
    subtitle : $contentsState.computed.subtitle,
  },

  watch: {},

  methods: {},

  mounted() {
        this.subtitle = x;

        // Promise.resolve()
        //     .then((r) => {
        //         console.log(x, "mounted()", 1);
        //         return $accounts.oauth2.userinfo();
        //     })
        //     .catch((r) => {
        //         console.log(x, "mounted()", 2);
        //         this.$router.push("/");
        //     })
        //     .then((r) => {
        //         console.log(x, "mounted()", 3);
        //     })
  },
};
</script>