import $common from "@/assets/apis/common.js";
import $commonStore from "@/assets/stores/common.js";

// import $contentsStore from "@/assets/stores/contents.js";

const name = "[/assets/apis/contents.js]";

const $contentsApi = {
  api: {
    execute(optionsBuilder) {
      return $common.meta
        .env("VITE_API_CONTENTS", "VITE_API_TOKEN")
        .then(optionsBuilder)
        .then((e) => {
          return $common.api.execute(e);
        })
        .then((e) => {
          return $common.api.then(e);
        })
        .catch((e) => {
          throw $common.api.catch(e);
        });
    },

    url(env, data) {
      if (typeof data == "object") {
        return `${data._links.self.href}`;
      } else {
        return `${env["VITE_API_CONTENTS"]}${data}`;
      }
    },

    token(env){
      let t = env["VITE_API_TOKEN"];
      let token = t == undefined ? $commonStore.computed.token.get() : t;
      return token;
    }, 

    headers(env, headers) {
      let token = $contentsApi.api.token(env);
      return $common.api.headers(headers, token);
    },

    params(env, params){
      let token = $contentsApi.api.token(env);
      return $common.api.params(params, token);
    },

    query(env, query) {
      let token = $contentsApi.api.token(env);
      return $common.api.query(query, token);
    },

    pageable(data) {
      return $common.api.pageable(data);
    },
  },


  /////////////////////////////////////
  //
  /////////////////////////////////////
  oauth2: {
    userinfo(role) {
      return $contentsApi.api.execute((e) => ({
        method: "GET",
        url: $contentsApi.api.url(e, "/oauth2/userinfo"),
        headers: $contentsApi.api.headers(e, {}),        
      })).then(r => {
        if(role == undefined) return r;        
        let idx = r.roles.findIndex(e=>{
          return e == role;
        });
        if(idx > -1) {
          return r;
        }else{
          throw r;
        }
      });
    },    
  },


  /////////////////////////////////////
  //
  /////////////////////////////////////
  foos: {
    search(data, params) {
      return $contentsApi.api
        .execute((e) => ({
          method: "GET",
          url: $contentsApi.api.url(e, "/api/foos"),
          headers: $contentsApi.api.headers(e, {}),
          params: $contentsApi.api.pageable(params),
          data: data,
        }));
    },
    create(data) {
      return $contentsApi.api.execute((e) => ({
        method: "POST",
        url: $contentsApi.api.url(e, "/api/foos"),
        headers: $contentsApi.api.headers(e, {}),
        data: data,
      }));
    },
    read(data) {
      return $contentsApi.api.execute((e) => ({
        method: "GET",
        url: $contentsApi.api.url(e, data),
        headers: $contentsApi.api.headers(e, {}),
      }));
    },
    update(data) {
      return $contentsApi.api.execute((e) => ({
        method: "PUT",
        url: $contentsApi.api.url(e, data),
        headers: $contentsApi.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $contentsApi.api.execute((e) => ({
        method: "DELETE",
        url: $contentsApi.api.url(e, data),
        headers: $contentsApi.api.headers(e, {}),
      }));
    },
  },

  /////////////////////////////////////
  //
  /////////////////////////////////////
  bars: {
    search(data, params) {
      return $contentsApi.api
        .execute((e) => ({
          method: "POST",
          url: $contentsApi.api.url(e, "/api/bars/search"),
          headers: $contentsApi.api.headers(e, {}),
          params: $contentsApi.api.pageable(params),
          data: data,
        }));
    },
    create(data) {
      return $contentsApi.api.execute((e) => ({
        method: "POST",
        url: $contentsApi.api.url(e, "/api/bars"),
        headers: $contentsApi.api.headers(e, {}),
        data: data,
      }));
    },
    read(data) {
      return $contentsApi.api.execute((e) => ({
        method: "POST",
        url: $contentsApi.api.url(e, data),
        headers: $contentsApi.api.headers(e, {}),
      }));
    },
    update(data) {
      return $contentsApi.api.execute((e) => ({
        method: "PUT",
        url: $contentsApi.api.url(e, data),
        headers: $contentsApi.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $contentsApi.api.execute((e) => ({
        method: "DELETE",
        url: $contentsApi.api.url(e, data),
        headers: $contentsApi.api.headers(e, {}),
      }));
    },
  },

  /////////////////////////////////////
  //
  /////////////////////////////////////
  items: {
    search(data, params) {
      return $contentsApi.api
        .execute((e) => ({
          method: "POST",
          url: $contentsApi.api.url(e, "/api/items/search"),
          headers: $contentsApi.api.headers(e, {}),
          params: $contentsApi.api.pageable(params),
          data: data,
        }));
    },
    create(data) {
      return $contentsApi.api.execute((e) => ({
        method: "POST",
        url: $contentsApi.api.url(e, "/api/items"),
        headers: $contentsApi.api.headers(e, {}),
        data: data,
      }));
    },
    read(data) {
      return $contentsApi.api.execute((e) => ({
        method: "POST",
        url: $contentsApi.api.url(e, data),
        headers: $contentsApi.api.headers(e, {}),
      }));
    },
    update(data) {
      return $contentsApi.api.execute((e) => ({
        method: "PUT",
        url: $contentsApi.api.url(e, data),
        headers: $contentsApi.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $contentsApi.api.execute((e) => ({
        method: "DELETE",
        url: $contentsApi.api.url(e, data),
        headers: $contentsApi.api.headers(e, {}),
      }));
    },
  },

  /////////////////////////////////////
  //
  /////////////////////////////////////
  users: {
    search(data, params) {
      return $contentsApi.api
        .execute((e) => ({
          method: "POST",
          url: $contentsApi.api.url(e, "/api/users/search"),
          headers: $contentsApi.api.headers(e, {}),
          params: $contentsApi.api.pageable(params),
          data: data,
        }));
    },
    create(data) {
      return $contentsApi.api.execute((e) => ({
        method: "POST",
        url: $contentsApi.api.url(e, "/api/users"),
        headers: $contentsApi.api.headers(e, {}),
        data: data,
      }));
    },
    read(data) {
      return $contentsApi.api.execute((e) => ({
        method: "POST",
        url: $contentsApi.api.url(e, data),
        headers: $contentsApi.api.headers(e, {}),
      }));
    },
    update(data) {
      return $contentsApi.api.execute((e) => ({
        method: "PUT",
        url: $contentsApi.api.url(e, data),
        headers: $contentsApi.api.headers(e, {}),
        data: data,
      }));
    },
    delete(data) {
      return $contentsApi.api.execute((e) => ({
        method: "DELETE",
        url: $contentsApi.api.url(e, data),
        headers: $contentsApi.api.headers(e, {}),
      }));
    },
  },

  sessions: {
    search(data, params) {
      return $contentsApi.api
        .execute((e) => ({
          method: "POST",
          url: $contentsApi.api.url(e, "/api/sessions/search"),
          headers: $contentsApi.api.headers(e, {}),
          params: $contentsApi.api.pageable(params),
          data: data,
        }));
    },
  },


};

export default $contentsApi;