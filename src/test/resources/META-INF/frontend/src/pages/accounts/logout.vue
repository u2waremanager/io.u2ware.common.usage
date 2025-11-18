<template>

    <v-container class="text-center">
        <v-btn
          variant="outlined"
          block
          size="x-large"
          @click="logout"
        >
          {{$t('accounts.logout.title')}}
        </v-btn>

    </v-container>



</template>

<script>
const x = "[/accounts/logout]";
import $accountsApi from "@/assets/apis/accounts";

export default {

    methods : {

        logout(){

            let before = this.$t('accounts.logout.title');
            let after = this.$t('accounts.logoff.title');

            this.$dialog.confirm(before, {})
                .then((r) => {
                    console.log(x, "logout()", 1, r);
                    return $accountsApi.oauth2.logout();
                })
                .then((r) => {
                    console.log(x, "logout()", 2, r);
                    return this.$dialog.alert(after)
                })
                .then((r) => {
                    this.$router.push("/accounts/logoff");
                })
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
                // OK. show page
            })
            .catch((r) => {
                console.log(x, "mounted()", 3);
                this.$router.push("/");
            });
    }
}



</script>