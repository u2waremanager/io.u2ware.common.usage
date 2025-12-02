<template>
  <v-container class="pa-4" fluid>
    <v-card>
      <v-card-title class="d-flex align-center pe-2">
        <v-icon icon="mdi-video-input-component"></v-icon> &nbsp;
        {{ $t("contents.items.title") }}&nbsp;
        <!-- 
        /////////////////////////////
        // Search Field Start
        /////////////////////////////
        -->
        <v-text-field
          class="ms-10"
          v-model="searchForm.keyword"
          density="compact"
          label="Search"
          prepend-inner-icon="mdi-magnify"
          variant="solo-filled"
          flat
          hide-details
          single-line
        ></v-text-field>
        <!-- 
        /////////////////////////////
        // Search Field End
        /////////////////////////////
        -->

      </v-card-title>

      <v-divider></v-divider>

      <v-data-table-server
        fixed-header
        density="compact"
        :loading="loading"
        :search="config.searchBy"
        :page="1"
        :items-per-page="20"
        :sort-by="config.sortBy"
        :items-per-page-options="config.itemsPerPageOptions"
        :headers="config.headers"
        :item-value="config.itemValue"
        :items-length="entitiesTotal"
        :items="entities"
        @update:options="searchAction"
      >
        <!-- 
        /////////////////////////////
        # Table Cell Template Start
        /////////////////////////////
        -->
        <template v-slot:item.id="{ item }">
          <v-btn
            variant="plain"
            color="primary"
            :text="item.id"
            style="text-transform: none"
            @click="readAction(item)"
          ></v-btn>
        </template>
        <template v-slot:item.updated.timestamp="{ item }">
          {{ $moment.format(item.updated.timestamp) }}
        </template>
        <!-- 
        /////////////////////////////
        # Table Cell Template End
        /////////////////////////////
        -->

        <template v-slot:footer.prepend>
          <v-btn class="ms-1" text variant="elevated" @click="refreshAction">
            <v-icon>mdi-refresh</v-icon>
          </v-btn>

          <v-btn
            class="ms-1"
            text
            variant="elevated"
            color="primary"
            @click="newAction"
          >
            <v-icon>mdi-plus</v-icon>
          </v-btn>

          <v-spacer></v-spacer>
        </template>
      </v-data-table-server>

      <v-dialog v-model="dialog" persistent width="800">
        <v-card
          prepend-icon="mdi-update"
          :title="$t('contents.items.title')"
          :subtitle="editForm.name"
        >
          <v-card-text>
            <v-form validate-on="eager" @update:model-value="formValidate">
              <!-- 
              /////////////////////////////
              # Edit Form Start
              /////////////////////////////
              -->
              <v-text-field
                v-if="!isNew"
                v-model="editForm.id"
                :rules="[$rules.requried]"
                label="id"
                placeholder="id"
                disabled
                hint="......."
                variant="outlined"
              ></v-text-field>

              <v-text-field
                class="ma-2"
                v-model="editForm.title"
                :rules="[$rules.requried]"
                label="title"
                placeholder="title"
                hint="......."
                variant="outlined"
              ></v-text-field>

              <v-text-field
                class="ma-2"
                v-model="editForm.cryptoValue"
                :rules="[$rules.requried]"
                label="cryptoValue"
                placeholder="cryptoValue"
                hint="......."
                variant="outlined"
              ></v-text-field>

              <json-field
                class="ma-2"
                v-model="editForm.jsonValue"
              >
              </json-field>

              <v-select
                class="ma-2"
                v-model="editForm.arrayValue"
                multiple
                chips
                label="arrayValue"
                placeholder="cryptoValue"
                variant="outlined"
                :items="['California', 'Colorado', 'Florida', 'Georgia', 'Texas', 'Wyoming']"
              ></v-select>

             <entity-field
                class="ma-2"
                v-model="editForm.foo"
                density="default"
                :rules="[$rules.requried]"
                :items="fooItems"
                :item-title="fooItemsTitle"
                :loading="fooItemsLoading"
                placeholder="Foo"
                @querySelections="fooItemsQuery"
                hint="......."
              >
              </entity-field>

             <entity-field
                class="ma-2"
                v-model="editForm.bars"
                density="default"
                :rules="[$rules.requried]"
                :items="barItems"
                :item-title="barItemsTitle"
                :loading="barItemsLoading"
                placeholder="Bars"
                @querySelections="barItemsQuery"
                multiple
                chips
                hint="......."
              >
              </entity-field>


              <!-- 
              /////////////////////////////
              # Edit Form End
              /////////////////////////////
              -->
            </v-form>
          </v-card-text>

          <v-card-actions>
            <v-btn
              class="ms-5"
              variant="elevated"
              color="primary"
              text="Save"
              :disabled="!validate"
              @click="isNew ? createAction(e) : updateAction(e)"
            ></v-btn>
            <v-btn text="Cancel" @click="cancelAction"></v-btn>

            <v-spacer></v-spacer>
            <v-btn
              v-if="!isNew"
              color="error"
              text="Delete"
              variant="text"
              @click="deleteAction"
            ></v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-card>
  </v-container>
</template>

<script>
const x = "[/contents/foos]";
import $contentsApi from "@/assets/apis/contents.js";
import $contentsStore from "@/assets/stores/contents.js";

export default {
  data: () => ({
    loading: false,
    dialog: false,
    isNew: false,
    validate: false,

    searchForm: {},
    editForm: {},

    entities: [],
    entitiesTotal: 0,

    config: {
      /////////////////////////////////
      // Config Start
      /////////////////////////////////
      api: $contentsApi.items,

      itemsPerPageOptions: [
        { value: 10, title: "10" },
        { value: 20, title: "20" },
        { value: 50, title: "50" },
        { value: 100, title: "100" },
      ],
      
      itemValue: "id",

      headers: [
        { key: "id", title: "id", align: "start" },
        { key: "title", title: "title", align: "center" },
        { key: "cryptoValue", title: "crypto", align: "end" },
        { key: "updated.timestamp", title: "updatedTimestamp", align: "end" },
      ],
      sortBy: [{ key: "updated.timestamp", order: "desc" }],
      searchBy: "",

      initForm: {
        id: undefined,
        title: undefined,
        jsonValue: {}, 
        arrayValue : [],
      }
      /////////////////////////////////
      // Config Start
      /////////////////////////////////
    },

    fooItems : [],
    fooItemsTitle : "id",
    fooItemsLoading : false,

    barItems : [],
    barItemsTitle : "_links.self.href",
    barItemsLoading : false

  }),

  watch: {
    searchForm: {
      handler(e) {
        this.refreshAction();
      },
      deep: true,
    },
  },

  computed: {
    subtitle: $contentsStore.computed.subtitle,
  },


  methods: {

    fooItemsQuery(v){
      console.log(x, "fooItemsQuery", 1, v);
      this.fooItemsLoading = true;
      
      $contentsApi.foos.search({}, {})
      .then(r=>{
        console.log(x, "fooItemsQuery", 2, r);

        this.fooItems = r.entities;
        this.fooItemsLoading = false;
      })
      .catch(r=>{
        
      });      
    },

    barItemsQuery(v){
      console.log(x, "barItemsQuery", 1, v);
      this.barItemsLoading = true;
      
      $contentsApi.bars.search({}, {})
      .then(r=>{
        console.log(x, "barItemsQuery", 2, r);

        this.barItems = r.entities;
        this.barItemsLoading = false;
      })
      .catch(r=>{
        
      });      
    },



    ////////////////////////////////////////
    //
    ////////////////////////////////////////
    dialogOpen(isNew) {
      this.dialog = true;
      this.isNew = isNew;
      return "opened";
    },
    dialogClose() {
      this.dialog = false;
      this.isNew = false;
      return "closed";
    },

    confirmBefore(code) {
      let msg = this.$t(`$dialog.before.${code}`);
      return this.$dialog.confirm(msg);
    },
    confirmAfter(code) {
      let msg = this.$t(`$dialog.after.${code}`);
      return this.$dialog.alert(msg);
    },
    confirmError(code) {
      let msg = this.$t(`$dialog.error.${code}`);
      return this.$dialog.alert(msg, code);
    },

    actionStart(loading) {
      this.loading = true == loading ? true : false;
      return Promise.resolve();
    },
    actionEnd(refresh) {
      this.loading = false;
      if (true == refresh) {
        this.dialog = false;
        this.isNew = false;
        this.refreshAction();
      }
    },

    formValidate(r) {
      this.validate = r;
    },
    formReset(r) {
      this.editForm = r ? r : Object.assign({}, this.config.initForm);
      return r;
    },

    ////////////////////////////////////////
    //
    ////////////////////////////////////////
    refreshAction() {
      this.config.searchBy = String(Date.now());
    },

    searchAction(params) {
      this.actionStart(true)
        .then((r) => {
          return this.config.api.search(this.searchForm, params);
        })
        .then((r) => {
          this.entitiesTotal = r.entitiesTotal;
          this.entities = r.entities;
          return r;
        })
        .then((r) => {
          return this.actionEnd(false);
        })
        .catch((e) => {
          return this.confirmError(e);
        })
        .catch((e) => {
          this.$router.push("/");
        });
    },

    newAction() {
      this.actionStart(true)
        .then((r) => {
          return this.formReset();
        })
        .then((r) => {
          return this.dialogOpen(true);
        })
        .then((r) => {
          return this.actionEnd(false);
        });
    },

    cancelAction() {
      this.actionStart(true)
        .then((r) => {
          return this.dialogClose();
        })
        .then((r) => {
          return this.formReset();
        })
        .then((r) => {
          return this.actionEnd(false);
        });
    },

    createAction() {
      this.confirmBefore("create")
        .then((r) => {
          return this.actionStart(true);
        })
        .then((r) => {
          return this.config.api.create(this.editForm);
        })
        .then((r) => {
          return this.confirmAfter("create");
        })
        .then((r) => {
          return this.formReset();
        })
        .then((r) => {
          return this.actionEnd(true);
        })
        .catch((r) => {
          return this.confirmError(r);
        })
        .catch((r) => {
          return this.actionEnd(true);
        });
    },

    readAction(entity) {
      this.actionStart(true)
        .then((r) => {
          return this.config.api.read(entity);
        })
        .then((r) => {
          return this.formReset(r);
        })
        .then((e) => {
          return this.dialogOpen(false);
        })
        .then((e) => {
          return this.actionEnd(false);
        })
        .catch((e) => {
          return this.confirmError(e);
        })
        .catch((e) => {
          return this.actionEnd(true);
        });
    },

    updateAction() {
      this.confirmBefore("update")
        .then((r) => {
          return this.actionStart(true);
        })
        .then((r) => {
          return this.config.api.update(this.editForm);
        })
        .then((r) => {
          return this.confirmAfter("update");
        })
        .then((r) => {
          return this.formReset();
        })
        .then((r) => {
          return this.actionEnd(true);
        })
        .catch((r) => {
          return this.confirmError(r);
        })
        .catch((r) => {
          return this.actionEnd(true);
        });
    },

    deleteAction() {
      this.confirmBefore("delete")
        .then((r) => {
          return this.actionStart(true);
        })
        .then((r) => {
          return this.config.api.delete(this.editForm);
        })
        .then((r) => {
          return this.confirmAfter("delete");
        })
        .then((r) => {
          return this.formReset();
        })
        .then((r) => {
          return this.actionEnd(true);
        })
        .catch((r) => {
          return this.confirmError(r);
        })
        .catch((r) => {
          return this.actionEnd(true);
        });
    },
  },

  mounted() {
    this.subtitle = x;
  },
};
</script>
