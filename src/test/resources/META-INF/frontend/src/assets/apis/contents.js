import $common from "@/assets/apis/common.js";

import $accountsState from "@/assets/stores/accounts.js";

const name = "[/assets/apis/contents.js]";

const $contents = {
  api: {
    host() {
      return $common.api.host("VITE_API_BACKEND");
    },

    execute(optionsBuilder) {
      return $contents.api
        .host()
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

    headers(headers, token) {
      let oauth2 =
        token == undefined ? $accountsState.computed.oauth2.get() : token;
      return $common.api.auth(oauth2, headers, "headers");
    },
    params(params, token) {
      let oauth2 =
        token == undefined ? $accountsState.computed.oauth2.get() : token;
      return $common.api.auth(oauth2, params, "params");
    },
    query(params, token) {
      let oauth2 =
        token == undefined ? $accountsState.computed.oauth2.get() : token;
      return $common.api.auth(oauth2, params, "query");
    },

    pageable(data) {
      return $common.api.pageable(data);
    },

    link(base, data) {
      return $common.api.link(base, data);
    },
  },

  foos: {
    search(data, params) {
      return $contents.api.execute((uri) => ({
        url: `${uri}/api/foos`,
        headers: $contents.api.headers(),
        method: "GET",
        data: data,
        params: $common.api.pageable(params),
      }));
    },
		create(data){
			return $contents.api.execute((uri)=> ({
				url: `${uri}/api/foos`,
        headers: $contents.api.headers(),
				method : 'POST',
				data: data,
			}));
		},
		read(data){
			return $contents.api.execute((uri)=> ({
				url: `${data._links.self.href}`,
        headers: $contents.api.headers(),
				method : 'GET',
			}));
		},
		update(data){
			return $contents.api.execute((uri)=> ({
				url: `${data._links.self.href}`,
        headers: $contents.api.headers(),
				method : 'PUT',
				data : data
			}));
		},
		delete(data){
			return $contents.api.execute((uri)=> ({
				url: `${data._links.self.href}`,
        headers: $contents.api.headers(),
				method : 'DELETE',
			}));
		}
  },

  bars: {
    search(data, params) {
      return $contents.api.execute((uri) => ({
        url: `${uri}/api/bars/search`,
        headers: $contents.api.headers(),
        method: "POST",
        data: data,
        params: $common.api.pageable(params),
      }));
    },
		create(data){
			return $contents.api.execute((uri)=> ({
				url: `${uri}/api/bars`,
        headers: $contents.api.headers(),
				method : 'POST',
				data: data,
			}));
		},
		read(data){
			return $contents.api.execute((uri)=> ({
				url: `${data._links.self.href}`,
        headers: $contents.api.headers(),
				method : 'POST',
			}));
		},
		update(data){
			return $contents.api.execute((uri)=> ({
				url: `${data._links.self.href}`,
        headers: $contents.api.headers(),
				method : 'PUT',
				data : data
			}));
		},
		delete(data){
			return $contents.api.execute((uri)=> ({
				url: `${data._links.self.href}`,
        headers: $contents.api.headers(),
				method : 'DELETE',
			}));
		}    
  }
};
export default $contents;
