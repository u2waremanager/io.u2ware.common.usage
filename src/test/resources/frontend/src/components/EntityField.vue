<template>
  <v-autocomplete
    v-model="select"
    v-model:search="search"
    :items="items"
    :item-title="itemTitle"
    :loading="loading"
    autocomplete="off"
    return-object
    @update:modelValue="updateModelValue"
  ></v-autocomplete>
</template>
<script>
export default {
  props: {
    modelValue: {
      type: Object,
      default: {},
    },

    loading: {
      type: Boolean,
      default: false,
    },

    items: {
      type: Array,
      default: [],
    },   
    itemTitle: {
      type: String,
      default: "",
    },      
    
  },

  data: () => ({
    select: undefined,
    search: undefined,
  }),

  watch: {
    search: {
      handler(val) {
        this.$emit('querySelections', val);
      },
    },
  },

  mounted() {
    console.log("mounted", this.modelValue);
  },

  emits: ["querySelections", "update:modelValue"],

  methods: {
    querySelections(v) {
      this.$emit('querySelections', v);
    },

    updateModelValue(){
      this.$emit('update:modelValue', this.select);
    }
  },
};
</script>
