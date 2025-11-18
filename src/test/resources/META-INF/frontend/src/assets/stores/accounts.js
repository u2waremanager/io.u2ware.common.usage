const state = {
  root: {},
};

const getters = {
  root: (state, getters, rootState, rootGetters) => {
    return state.root;
  },
};

const mutations = {
  root: (state, payload) => {
    Object.keys(payload).forEach((k) => {
      state.root[k] = payload[k];
    });
  },
};

const actions = {
  root: ({ dispatch, commit, rootState, rootGetters }, payload) => {
    return new Promise((resolve) => {
      commit("root", payload);
      resolve(payload);
    });
  },
};

import $store from "@/plugins/stores.js";

export default {
  namespaced: true,
  state: state,
  getters: getters,
  mutations: mutations,
  actions: actions,

  ////////////////////
  // 
  /////////////////////

  persistedstate: true,

  computed: {
    oauth2: {
      get: () => {
        return $store.getters["accounts/root"].oauth2;
      },
      set: (val) => {
        $store.commit("accounts/root", { oauth2: val });
      },
    },
  },
};
