import { createI18n } from "vue-i18n";

const messages = {}

register(messages, import.meta.glob("@/assets/locales/**", {
  eager: true,
}));


const i18n = createI18n({
  warnHtmlMessage: false,
  legacy: false,
  locale: "KO",
  fallbackLocale: "KO",
  messages: messages,
});

export default i18n;


//////////////////////
//
//////////////////////
function register(target, resources){
  for (let path in resources) {
    let name = path.replace(/^.*[\\\/]/, '').replace(/\.[^/.]+$/, '').toUpperCase();
    let resource = resources[path];
    console.log("[locales]", name, resource);
    target[name] = resource;
  }
}