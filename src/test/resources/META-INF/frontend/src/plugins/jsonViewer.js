import JsonViewer from "vue3-json-viewer";
// if you used v1.0.5 or latster ,you should add import "vue3-json-viewer/dist/index.css"
import "vue3-json-viewer/dist/vue3-json-viewer.css";



export default {


  install: (app, options) => {

    app.use(JsonViewer);
    // app.component("json-viewer", JsonViewer);
  },
};