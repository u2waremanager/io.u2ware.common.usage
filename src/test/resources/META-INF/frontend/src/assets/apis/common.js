import qs from "qs";
import $axios from "axios";

const name = "[/assets/apis/common.js]";

const $common = {

  ////////////////////////////////////
  // Axios Utils..
  ////////////////////////////////////
  axios : {
    
    execute(options) {
      options["paramsSerializer"] = (p)=>{
        return qs.stringify(p, { arrayFormat: "repeat" });
      },
      options["timestamp"] = new Date().getTime();
      if (options.data) console.log(options.timestamp, options.url, options.data);
      else console.log(options.timestamp, options.url);
      return $axios(options);
    },

    then(r) {
      if (r && r.config) {
        let options = r.config;
        let headers = r.headers;
        if (headers["content-type"].includes("json")) {
          console.log(options.timestamp, options.url, r.status);
        } else {
          console.log(options.timestamp, options.url, "Not JSON");
          throw 404;
        }
      }
      return r.data;
    },

    catch(e) {
      if (e && e.response && e.response.config) {
        let options = e.response.config;
        console.log(options.timestamp, options.url, e.response.status, options);
        throw e.response.status;
      }
      if (e && e.config) {
        let options = e.config;
        console.log(options.timestamp, options.url, "Network Error");
        throw 404;
      }
      throw e;
    },
  },


  api : {

    host(uri) {
      return $common.env.profile().then((profile) => {
        return $common.env.value(uri, profile);
      });
    },

    auth(oauth2, target, type) {

      let token_type = "bearar";
      let id_token = oauth2;

      if (typeof oauth2 == "object") { 
        token_type = oauth2.token_type;
        id_token = oauth2.id_token;
      }

      if("headers" == type || undefined == type) { // headers..
        const authorization = `${token_type} ${id_token}`;
        if (target == undefined) {
          return { Authorization: authorization };
        } else {
          target["Authorization"] = authorization;
          return target;
        }
      }else if("params" == type){ // params
        if (target == undefined) {
          return { access_token: id_token };
        } else {
          target["access_token"] = id_token;
          return target;
        }
      }else if("query" == type){ // query
        let p = $common.api.auth(oauth2, target, "params");
        let v = qs.stringify(p, { arrayFormat: "repeat" });
        return v;
      }
    },

    pageable(data) {
      if (!data) return {};
      const sort = [];
      if (data.sortBy != undefined) {
        data.sortBy.forEach((s, i) => {
          sort.push(s.key + "," + s.order);
        });
      }
      let pageRequest = {
        size: data.itemsPerPage,
        page: data.page - 1,
        sort: sort,
      };
      return pageRequest;
    },

    link(base, data) {
      if (typeof data == "object") {
        return `${data._links.self.href}`;
      } else {
        return `${base}/${data}`;
      }
    },   
  },


  ////////////////////////////////////
  // env
  ////////////////////////////////////
  env: {
    profile: () => {
      if (import.meta.env.PROD) {
        const url = "/actuator/info";
        return $axios({
            url: url,
          })
          .then((r) => {
            // console.log("base.js", "profile()", url, "then", r);
            if (r.headers["content-type"] == "application/json") {
              let profile = r.data.profile;
              if (profile != undefined) {
                return `${profile}`;
              }
            }
            return undefined;
          })
          .catch((e) => {
            // console.log("base.js", "profile()", url, "catch", r);
            return undefined;
          });
      } else {
        return Promise.resolve(undefined);
      }
    },

    key: (key, profile) => {
      let suffix = profile != undefined ? `_${profile}`.toUpperCase() : "";
      let prop = `${key.toUpperCase()}${suffix}`;
      return prop.startsWith("VITE_") ? prop : `VITE_${prop}`;
    },

    value: (key, profile) => {
      let envKey = $common.env.key(key, profile);
      let v = import.meta.env[envKey];

      // console.log("base.js", key, envKey, v);
      // v = v.replaceAll("localhost",  window.location.hostname);
      // console.log(window.location);
      // protocol: "https:"
      // hostname: "192.168.75.107"
      // port: "3000"
      // host: "192.168.75.107:3000"
      // origin: "https://192.168.75.107:3000"
      // pathname: "/contents/83151238-fda2-4feb-93bc-a11b94a38f1d/11"
      // href: "https://192.168.75.107:3000/contents/83151238-fda2-4feb-93bc-a11b94a38f1d/11"
      return v;
    },
  },



}
export default $common;