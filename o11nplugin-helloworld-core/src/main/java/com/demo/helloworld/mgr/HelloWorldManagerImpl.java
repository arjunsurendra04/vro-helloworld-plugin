package com.demo.helloworld.mgr;

import com.demo.helloworld.vo.Employee;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.dunes.vso.sdk.api.IPluginFactory;
import ch.dunes.vso.sdk.endpoints.IEndpointConfiguration;
import com.vmware.o11n.plugin.sdk.annotation.*;
import com.vmware.o11n.plugin.sdk.spring.AbstractSpringPluginFactory;
import com.vmware.o11n.plugin.sdk.spring.platform.GlobalPluginNotificationHandler;
import com.vmware.o11n.plugin.sdk.spring.task.AsyncPluginTaskExecutor;
import com.vmware.o11n.plugin.sdk.spring.watch.WatchRequestService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import com.demo.helloworld.dao.HelloWorldRepository;

@VsoObject(
        name = "HelloworldManager",
        create = false,
        strict = true,
        singleton = true,
        description = "Provides CRUD operations for Employee objects.")
public class HelloWorldManagerImpl implements HelloWorldManager {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldManagerImpl.class);

    @Autowired
    private GlobalPluginNotificationHandler notificationHandler;
    @Autowired
    private HelloWorldRepository repository;
    @Autowired
    private AsyncPluginTaskExecutor asyncPluginTaskExecutor;
    @Autowired
    private WatchRequestService watchRequestService;

    //Singleton
    /*
    * To create a class to map to the Orchestrator JavaScript API, you add an instance of that class to an 
    * instance of the IPluginFactory implementation by defining a method named createScriptingSingleton(). 
    * When the plug-in adaptor instantiates the factory, it also instantiates the class to add to the 
    * JavaScript API. This makes it possible to call all methods of this class statically from within vCO. 
     */
    public static HelloWorldManagerImpl createScriptingSingleton(IPluginFactory factory) {
        return ((AbstractSpringPluginFactory) factory).createScriptingObject(HelloWorldManagerImpl.class);
    }

    @VsoMethod
    @Override
    public void addEmployee(Employee item) throws IllegalArgumentException, IOException {
        repository.createNode(item);
    }

    @VsoMethod
    @Override
    public void removeEmployee(String id) throws IllegalArgumentException, IOException {
        repository.deleteNode(id);
    }

    @VsoMethod
    @Override
    public Collection<IEndpointConfiguration> getAllEmployees() {
        return repository.getNodes();
    }

    @VsoMethod
    @Override
    public IEndpointConfiguration getEmployee(String id) throws IllegalArgumentException {
        return repository.getNode(id);
    }

}
