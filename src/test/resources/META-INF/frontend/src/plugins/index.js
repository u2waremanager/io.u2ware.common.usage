/**
 * plugins/index.js
 *
 * Automatically included in `./src/main.js`
 */

// Plugins
import vuetify from './vuetify'
import router from './router'

import stores from './stores' //vuex
import locales from './locales' //i18n

import moment from './moment' 
import axios from './axios' 
import jsonViewer from './jsonViewer' 


import dialog from './dialog'
import rules from './rules'


export function registerPlugins (app) {
  app
    .use(vuetify)
    .use(router)

    .use(stores)
    .use(locales)
    .use(moment)
    .use(axios)
    .use(jsonViewer)

    .use(dialog)
    .use(rules)
}
