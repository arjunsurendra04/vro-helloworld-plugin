package com.demo.helloworld.mgr;

import ch.dunes.vso.sdk.endpoints.IEndpointConfiguration;
import com.demo.helloworld.vo.Employee;
import java.io.IOException;
import java.util.Collection;

public interface HelloWorldManager {

    public void addEmployee(Employee item) throws IllegalArgumentException, IOException;

    public void removeEmployee(String id) throws IllegalArgumentException, IOException;

    public Collection<IEndpointConfiguration> getAllEmployees();

    public IEndpointConfiguration getEmployee(String id) throws IllegalArgumentException;

}
