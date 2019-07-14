/*
 * Copyright (c) Jannik Will and Albert Munsch
 */

package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.PropertyResourceBundle;

public class LanguageHelper {
    private static LanguageHelper helper;
    private Language language = Language.Auto;

    private LanguageHelper() {

    }

    public static LanguageHelper getInstance() {
        if (helper == null) {
            helper = new LanguageHelper();
        }
        return helper;
    }

    public PropertyResourceBundle getLanguageAuto() throws IOException {
        String path = "";
        if (Locale.getDefault().getLanguage().equals("de") && language == Language.Auto) {
            path = "Languages/Languages_de.properties";
        } else if (language == Language.Deutsch) {
            path = "Languages/Languages_de.properties";
        } else {
            path = "Languages/Languages_en.properties";
        }
        InputStream inputStream = getClass().getClassLoader().getResource(path).openStream();
        return new PropertyResourceBundle(new InputStreamReader(inputStream, "UTF-8"));
    }

    public void setLanguage(Language langage) {
        this.language = langage;
    }


}
