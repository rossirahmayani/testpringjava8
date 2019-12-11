package com.rossi.testspringjava8.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LanguageListUtils {
    private List<Locale> locales = Arrays.asList(
            new Locale("en"), //ENGLISH
            new Locale("in") //INDONESIAN
    );

    public List<Locale> getLanguage(){
        return locales;
    }
}
