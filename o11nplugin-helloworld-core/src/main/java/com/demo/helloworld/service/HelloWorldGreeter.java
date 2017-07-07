package com.demo.helloworld.service;

import org.springframework.beans.factory.annotation.Autowired;

import ch.dunes.vso.sdk.api.IPluginFactory;
import com.vmware.o11n.plugin.sdk.annotation.VsoMethod;
import com.vmware.o11n.plugin.sdk.annotation.VsoObject;
import com.vmware.o11n.plugin.sdk.spring.AbstractSpringPluginFactory;

@VsoObject(singleton = true, name = "HelloworldGreeter")
public class HelloWorldGreeter {

    @Autowired
    private HelloWorldGreeterService service;

    public HelloWorldGreeter() {
    }

    public static HelloWorldGreeter createScriptingSingleton(IPluginFactory factory) {
        return ((AbstractSpringPluginFactory) factory).createScriptingObject(HelloWorldGreeter.class);
    }

    @VsoMethod(showInApi = true)
    public String greet(String name) {
        return service.greet(name);
    }
}
