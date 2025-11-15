package com.kane.util;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerConfig {
    private static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

    static {
        cfg.setClassForTemplateLoading(FreemarkerConfig.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
    }

    public static Configuration getConfig() {
        return cfg;
    }
}
