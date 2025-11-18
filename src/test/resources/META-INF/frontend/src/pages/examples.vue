<template>
  <router-view/>
</template>

<script>
const x = "[/examples]";

export default {
  data: () => ({
  }),

  computed: {},

  watch: {},

  methods: {},

  mounted() {
    console.log(x, "mounted()", 1);
  },
};
</script>