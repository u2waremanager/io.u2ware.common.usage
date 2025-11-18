<template>
  Setter<br/>

  hello1: <pre>{{ this.hello1 }}</pre>
  hello2: <pre>{{ this.hello2 }}</pre>
  hello3: <pre>{{ this.hello3 }}</pre>
  hello4: <pre>{{ this.hello4 }}</pre>


  hello5: <pre>{{ this.hello5 }}</pre>
  hello6: <pre>{{ this.hello6 }}</pre>
  hello7: <pre>{{ this.hello7 }}</pre>


</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from "vuex"; //#1, //#2
import $store from "@/plugins/stores.js"; //#3

import $normalState from "@/assets/stores/normalState.js"; //#4
import $loggingState from "@/assets/stores/loggingState.js"; //#5
import $persistedState from "@/assets/stores/persistedState.js"; //#6

export default {

  data: () => ({
    hello3 : {},
    hello4 : {}
  }),

  computed: {
    ...mapGetters({hello1 : 'normalState/root', hello2 : 'normalState/root'}), //#1

    hello5 : $normalState.computed.sample,
    hello6 : $loggingState.computed.sample,
    hello7 : $persistedState.computed.sample,
  },

  methods: {
    ...mapMutations({setHello1: 'normalState/root'}), //#1
    ...mapActions({ setHello2: 'normalState/root'}), //#1

  },

  mounted(){

    //#1
    this.setHello1({hello1: "world1"});

    //#2
    this.setHello2({hello2: "world2"});

    //#3
    this.$store.commit('normalState/root', {hello3: "world3"});
    this.hello3 = this.$store.getters['normalState/root'];

    //#4
    $store.commit('normalState/root', {hello4: "world4"});
    this.hello4 = $store.getters['normalState/root'];

    //#5
    this.hello5 = new Date();

    //#6
    this.hello6.push({ item : new Date()});    

    //#7
    this.hello7 = { item : new Date()};
  }
}
//
</script>