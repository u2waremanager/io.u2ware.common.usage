<template>
    <v-container class="text-center">
        <v-btn
          variant="outlined"
          block
          size="x-large"
          @click="login"
        >
          {{$t('accounts.login.title')}}
        </v-btn>

    </v-container>

</template>

<script>
const x = "[/accounts/logoff]";
import $accountsApi from "@/assets/apis/accounts";

export default {

    methods : {
        login(){
            this.$router.push("/accounts/login");
        }
    },

    mounted() {

        Promise.resolve()
            .then((r) => {
                console.log(x, "mounted()", 1);
                return $accountsApi.oauth2.userinfo();
            })
            .then((r) => {
                console.log(x, "mounted()", 2);
                this.$router.push("/");
            })
            .catch((r) => {
                console.log(x, "mounted()", 3);
                // OK. show page
            })
    }

}



</script>