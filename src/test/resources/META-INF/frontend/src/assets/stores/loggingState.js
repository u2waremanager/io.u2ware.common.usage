const state = {
  root: [],
};
const getters = {
  root: (state, getters, rootState, rootGetters) => {
    return state.root;
  },
};
const mutations = {
  root: (state, payload) =>{
    // payload.unshift(moment().format("YYYY-MM-DD hh:mm:ss"));
    // let fn = window.console.log;
    // fn[Array.isArray(payload) ? 'apply' : 'call'](window.console, payload);
    state.root.push(payload);
  },
};
const actions = {
  root: ({ dispatch, commit, rootState, rootGetters }, payload) => {
    return Promise.try(() => {
      commit("root", payload);
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
        return $store.getters["loggingState/root"];
      },
      set: (val) => {
        // $store.commit("loggingState/root", val);
      },      
    }
  }
};
