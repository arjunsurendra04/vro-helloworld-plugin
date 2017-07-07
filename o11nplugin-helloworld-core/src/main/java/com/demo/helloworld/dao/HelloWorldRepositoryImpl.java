package com.demo.helloworld.dao;

import ch.dunes.vso.sdk.endpoints.IEndpointConfiguration;
import ch.dunes.vso.sdk.endpoints.IEndpointConfigurationService;
import com.demo.helloworld.vo.Employee;
import com.vmware.o11n.plugin.sdk.spring.InventoryRef;
import com.vmware.o11n.plugin.sdk.spring.platform.GlobalPluginNotificationHandler;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldRepositoryImpl implements HelloWorldRepository {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldRepositoryImpl.class);
    @Autowired
    private IEndpointConfigurationService service;
    @Autowired
    private GlobalPluginNotificationHandler notificationHandler;

    @Override
    public void createNode(Employee obj) {
        try {
            log.info("Creating Node!");
            IEndpointConfiguration config = service.newEndpointConfiguration(obj.getId());
            config.setString(Employee.ID, obj.getId());
            config.setString(Employee.NAME, obj.getName());
            config.setInt(Employee.AGE, obj.getAge());
            config.setBoolean(Employee.STATUS, obj.getStatus());
            service.saveEndpointConfiguration(config);
            notificationHandler.notifyElementsInvalidate();
        } catch (IOException ex) {
            log.error("Repo Error: {}", ex);
        }
    }

    @Override
    public void deleteNode(String id) {
        try {
            log.info("Deleting Node!");
            service.deleteEndpointConfiguration(id);
            notificationHandler.notifyElementDeleted(InventoryRef.valueOf(Employee.TYPE, id));
        } catch (IOException ex) {
            log.error("Repo Error: {}", ex);
        }
    }

    @Override
    public Collection<IEndpointConfiguration> getNodes() {
        try {
            return service.getEndpointConfigurations();
        } catch (IOException ex) {
            log.error("Repo Error: {}", ex);
        }
        return Collections.emptyList();
    }

    @Override
    public IEndpointConfiguration getNode(String id) {
        try {
            return service.getEndpointConfiguration(id);
        } catch (IOException ex) {
            log.error("Repo Error: {}", ex);
        }
        return null;
    }

}
