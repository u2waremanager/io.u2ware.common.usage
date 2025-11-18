import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";


/////////////////////////////////
//
/////////////////////////////////
const modules = {};

register(modules, import.meta.glob("@/assets/stores/**", {
  import: "default",
  eager: true,
}));


const persistedPaths = [];
// console.log("[stores] modules:", modules);

for(let m in modules){
  // console.log(`[stores] module: ${m}`, modules[m].persistedstate);
  if(true == modules[m].persistedstate){
    persistedPaths.push(m);
  }
}
// console.log("[stores] persistedPaths:", persistedPaths);


/////////////////////////////////
//
/////////////////////////////////
const VITE_PERSISTED_KEY = import.meta.env["VITE_PERSISTED_KEY"]
// alert(`persistedState_${VITE_PERSISTED_KEY}`);

const persistedState = createPersistedState({
  key: `${VITE_PERSISTED_KEY}`,
  paths: persistedPaths,
});


// import { mapState, mapGetters, mapMutations, mapActions } from "vuex";
const store = createStore({
  state: {},

  getters: {},
  //this.count 를 this.$store.getters['경로명/함수명']에 매핑한다.
  //
  // computed : {
  //   ...mapGetters({count : '경로명/함수명'})
  // }

  mutations: {},
  //this.add()를this.$store.commit('경로명/함수명')에 매핑한다.
  //
  // methods: { 
  //   ...mapMutations({add: '경로명/함수명'}),
  // }

  actions: {},
  // methods: {
  // 	 ...mapActions({ add: '경로명/함수명'}),
  // }
  //
  // this.add()를this.$store.dispatch('경로명/함수명')에 매핑한다.
  modules: modules,


  plugins: [persistedState]
});

export default store;


//////////////////////
//
//////////////////////
function register(target, resources){
  for (let path in resources) {
    let name = path.replace(/^.*[\\\/]/, '').replace(/\.[^/.]+$/, '');//.toUpperCase();
    let resource = resources[path];
    console.log("[stores]", name, resource);
    target[name] = resource;
  }
}