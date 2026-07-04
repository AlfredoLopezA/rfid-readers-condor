package com.condor.rfid.config;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18nManager {

    private ResourceBundle bundle;

    public I18nManager() {
        String language = System.getProperty("app.language");
        System.out.println("app.language: " + language);
        System.out.println("Locale default: " + Locale.getDefault());
        if (language == null || language.isBlank()) {
            language = "es"; // idioma por defecto de la aplicación
        }
        setLanguage(language);
    }

    public void setLanguage(String language) {
        Locale locale = Locale.forLanguageTag(language);
        Locale.setDefault(locale);
        bundle = ResourceBundle.getBundle("i18n.messages", locale);
    }

    public String get(String key) {
        return bundle.getString(key);
    }

    public Locale getCurrentLocale() {
        return bundle.getLocale();
    }
}