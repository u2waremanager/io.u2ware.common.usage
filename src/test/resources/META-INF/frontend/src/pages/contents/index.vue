<template>

    index

</template>

<script>
const x = "[/contents/index]";
import $accounts from "@/assets/apis/accounts";

export default {
    data: () => ({

    }),

    computed: {
    },

    watch: {},

    methods: {},

    mounted() {


        Promise.resolve()
            .then((r) => {
                console.log(x, "mounted()", 1);
                return $accounts.oauth2.userinfo();
            })
            .catch((r) => {
                console.log(x, "mounted()", 2);
                this.$router.push("/");
            })
            .then((r) => {
                console.log(x, "mounted()", 3);
            })
    },
};
</script>