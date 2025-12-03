<template>
  <v-form :class="class" validate-on="eager" @update:model-value="formValidate">
    <v-data-table
      class="py-4 highlight-row"
      :headers="headers"
      :sort-by="sortBy"
      :items="items"
      density="compact"
      hide-default-footer
    >
      <template v-slot:header.actions="{ item }">
        <v-btn
          size="x-small"
          text
          variant="elevated"
          @click="addItem()"
          :disabled="!editable"
        >
          <v-icon>mdi-plus</v-icon>
        </v-btn>
      </template>

      <template v-slot:item.key="{ item }">
        <span v-if="'remove' == item.actions">{{ item.key }}</span>

        <v-text-field
          v-if="'add' == item.actions"
          v-model="item.key"
          :rules="[$rules.requried]"
          class="mx-0"
          placeholder="key"
          variant="outlined"
          density="compact"
          hide-details
        ></v-text-field>
      </template>

      <template v-slot:item.value="{ item }">
        <span v-if="'remove' == item.actions">{{ item.value }}</span>

        <v-text-field
          v-if="'add' == item.actions"
          v-model="item.value"
          :rules="[$rules.requried]"
          class="mx-0"
          placeholder="key"
          variant="outlined"
          density="compact"
          hide-details
        ></v-text-field>
      </template>

      <template v-slot:item.actions="{ item }">
        <v-btn
          v-if="'add' == item.actions"
          size="x-small"
          text
          variant="elevated"
          @click="add(item)"
          :disabled="!validate"
        >
          <v-icon>mdi-plus</v-icon>
        </v-btn>
        <v-btn
          v-if="'add' == item.actions"
          size="x-small"
          text
          variant="elevated"
          @click="cancel(item)"
        >
          <v-icon>mdi-cancel</v-icon>
        </v-btn>

        <v-btn
          v-if="'remove' == item.actions"
          size="x-small"
          text
          variant="elevated"
          :disabled="!editable"
          @click="remove(item)"
        >
          <v-icon>mdi-cancel</v-icon>
        </v-btn>
      </template>
    </v-data-table>
  </v-form>
</template>
<script>
export default {
  props: {
    modelValue: {
      type: Object,
      default: {},
    },
    class: {
      type: String,
      default: "ma-0",
    },
  },

  data: () => ({
    items: [],
    headers: [
      { key: "key", title: "key", align: "start", width:"40%" },
      { key: "value", title: "value", align: "start", width:"40%" },
      { key: "actions", title: "Actions", align: "end", width:"20%" },
    ],
    sortBy: [{ key: "key", order: "asc" }],

    editable: true,
    validate: false,
  }),

  mounted() {
    console.log("mounted", this.modelValue);
    this.addItems();
  },

  methods: {
    formValidate(r) {
      this.validate = r;
    },

    addItems() {
      let keys = Object.keys(this.modelValue).sort();
      for (let key of keys) {
        let item = {
          key: key,
          value: this.modelValue[key],
          actions: "remove",
        };
        this.items.push(item);
      }
    },

    addItem() {
      let item = {
        key: undefined,
        value: undefined,
        actions: "add",
      };
      this.items.push(item);
      this.editable = false;
    },

    cancel(item) {
      // console.log("cancel", 1, item);
      let idx = this.items.findIndex((e) => e.actions == "add");
      if (idx > -1) this.items.splice(idx, 1);
      // console.log("cancel", 2, idx);

      this.editable = true;
    },

    add(item) {
      // console.log("add", 1, item);
      if (this.modelValue[item.key] != undefined) {
        let idx = this.items.findIndex(
          (e) => e.key == item.key && e.actions == "remove"
        );
        if (idx > -1) this.items.splice(idx, 1);
        // console.log("add", 2, idx);
      }
      item.actions = "remove";

      this.modelValue[item.key] = item.value;
      this.editable = true;
    },

    remove(item) {
      // console.log("add", 1, item);

      let idx = this.items.findIndex((e) => e.key == item.key);
      if (idx > -1) this.items.splice(idx, 1);
      // console.log("add", 2, idx);

      delete this.modelValue[item.key];
      this.editable = true;
    },
  },
};
</script>

<style scoped>
.highlight-row {
  border: 1px solid gray;
}
</style>