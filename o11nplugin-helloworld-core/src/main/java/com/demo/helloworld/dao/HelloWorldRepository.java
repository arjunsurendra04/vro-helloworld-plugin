package com.demo.helloworld.dao;

import ch.dunes.vso.sdk.endpoints.IEndpointConfiguration;
import com.demo.helloworld.vo.Employee;
import java.util.Collection;

public interface HelloWorldRepository {

    public void createNode(Employee obj);

    public void deleteNode(String id);

    public Collection<IEndpointConfiguration> getNodes();

    public IEndpointConfiguration getNode(String id);

}
