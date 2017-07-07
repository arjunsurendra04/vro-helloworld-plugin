package com.demo.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vmware.o11n.plugin.sdk.spring.AbstractSpringPluginAdaptor;

public final class HelloWorldPluginAdaptor extends AbstractSpringPluginAdaptor {

    private static final String DEFAULT_CONFIG = "com/demo/helloworld/pluginConfig.xml";

    @Override
    protected ApplicationContext createApplicationContext(ApplicationContext defaultParent) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{DEFAULT_CONFIG}, false, defaultParent);
        applicationContext.setClassLoader(getClass().getClassLoader());
        applicationContext.refresh();

        return applicationContext;
    }
}
