import $common from "@/assets/apis/common.js";

import $accountsStore from "@/assets/stores/accounts.js";


const name = "[/assets/apis/accounts.js]";

const $accountsApi = {

  api: {

    host() {
      return $common.api.host("VITE_API_OAUTH2");
    },

    execute(optionsBuilder) {
      return $accountsApi.api.host()
        .then(optionsBuilder)
        .then((e) => {
          return $common.axios.execute(e);
        })
        .then((e) => {
          return $common.axios.then(e);
        })
        .catch((e) => {
          throw $common.axios.catch(e);
        });
    },

    headers(headers, token){
      let oauth2 = (token == undefined) ? $accountsStore.computed.oauth2.get() : token;
      return $common.api.auth(oauth2, headers, "headers");
    },
    params(params, token){
      let oauth2 = (token == undefined) ? $accountsStore.computed.oauth2.get() : token;
      return $common.api.auth(oauth2, params, "params");
    },
    query(params, token){
      let oauth2 = (token == undefined) ? $accountsStore.computed.oauth2.get() : token;
      return $common.api.auth(oauth2, params, "query");
    },

    pageable(data) {
      return $common.api.pageable(data);
    },

    link(base, data) {
      return $common.api.link(base, data);
    },
  },


  ////////////////////////////////////
  // APIs (Oath2)
  ////////////////////////////////////  
  oauth2: {
    providers() {
      return $accountsApi.api.execute((uri) => ({
        url: `${uri}/oauth2/providers`,
      }));
    },

    userinfo() {
      return $accountsApi.api.execute((uri) => ({
        url: `${uri}/oauth2/userinfo`,
        headers: $accountsApi.api.headers(),
      }));
    },

    login(query){
      return $accountsApi.api.execute((uri) => ({
        url: `${uri}/oauth2/userinfo`,
        headers: $common.api.auth(query, undefined, "headers"),
      }))
      .then(r=>{
        $accountsStore.computed.oauth2.set(query);
        return $accountsStore.computed.oauth2.get();
      });
    },

    logout() {
      return $accountsApi.api.execute((uri) => ({
        url: `${uri}/oauth2/logout`,
        headers: $accountsApi.api.headers(),
      }))
      .finally((r) => {
        $accountsStore.computed.oauth2.set(undefined);
      });
    },
  },

    
}
export default $accountsApi;
