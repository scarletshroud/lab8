package src.client.gui;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localizer {

    private ResourceBundle bundleRus;
    private ResourceBundle bundleNor;
    private ResourceBundle bundleCat;
    private ResourceBundle bundlePan;
    private ResourceBundle defaultBundle;

    private Locale defaultLocale;

    private Locale rusLocale;
    private Locale norLocale;
    private Locale panLocale;
    private Locale catLocale;


    public Localizer() {

        rusLocale = new Locale("ru", "RU");
        norLocale = new Locale("nor", "NO");
        panLocale = new Locale("pan", "PA");
        catLocale = new Locale("cat", "ES");

        defaultLocale = rusLocale;

        bundleRus = ResourceBundle.getBundle("src.resources.resource", rusLocale);
        bundleNor = ResourceBundle.getBundle("src.resources.resource", norLocale);
        bundlePan = ResourceBundle.getBundle("src.resources.resource", panLocale);
        bundleCat = ResourceBundle.getBundle("src.resources.resource", catLocale);

        defaultBundle = bundleRus;
    }

    void setRus() {
       defaultBundle = bundleRus;
       defaultLocale = rusLocale;
    }

    void setNor() {
       defaultBundle = bundleNor;
       defaultLocale = norLocale;
    }

    void setCat() {
       defaultBundle = bundleCat;
       defaultLocale = catLocale;
    }

    void setPan() {
      defaultBundle = bundlePan;
      defaultLocale = panLocale;
    }

    public ResourceBundle getBundle() {
        return defaultBundle;
    }

    public Locale getLocale() {
        return defaultLocale;
    }
}
