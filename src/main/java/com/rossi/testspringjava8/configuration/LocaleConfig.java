package com.rossi.testspringjava8.configuration;

import com.rossi.testspringjava8.common.utils.LanguageListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


public class LocaleConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    @Autowired
    private LanguageListUtils languageListUtils;

    List<Locale> locales = languageListUtils.getLanguage();

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Accept-Language"))
                .filter(val -> !val.isEmpty())
                .map(headerLang -> Locale.lookup(Locale.LanguageRange.parse(headerLang), locales))
                .orElse(request.getLocale());
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

}
