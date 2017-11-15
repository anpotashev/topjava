package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/* Singleton of ApplicationContext for use in several Servlets */
public enum AppContext {
    APP_CONTEXT;
    private ConfigurableApplicationContext context = new GenericXmlApplicationContext("spring/spring-app.xml");

    public ConfigurableApplicationContext getContextInstance() {
        return context;
    }

}
