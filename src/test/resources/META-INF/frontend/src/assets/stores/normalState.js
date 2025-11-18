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
    // state.root = { ...payload };
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


  computed : {

    sample : {
      get: () => {
        return $store.getters["normalState/root"].sample;
      },
      set: (val) => {
        $store.commit("normalState/root", { sample: val });
      },      
    },

    subtitle : {
      get: () => {
        return $store.getters["normalState/root"].subtitle;
      },
      set: (val) => {
        $store.commit("normalState/root", { subtitle: val });
      },      
    }


  }
};