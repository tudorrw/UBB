import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import en from "@/locales/en.json";
import ro from "@/locales/ro.json";
import de from "@/locales/de.json";

i18n.use(initReactI18next).init({
    resources: {
        en: { translation: en },
        ro: { translation: ro },
        de: { translation: de },
    },
    lng: "en", // default language
    fallbackLng: "en", // fallback if language is not found
    interpolation: { escapeValue: false },
});

export default i18n;