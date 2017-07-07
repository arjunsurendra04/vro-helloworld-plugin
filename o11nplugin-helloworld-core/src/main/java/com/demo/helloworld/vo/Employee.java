package com.demo.helloworld.vo;

import com.vmware.o11n.plugin.sdk.annotation.*;
import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

/**
 * Represents the root element object type of the plug-in.
 */
@Data
@VsoFinder(name = Employee.TYPE,
        datasource = "main-datasource",
        image = "images/item-16x16.png",
        idAccessor = "getId()")
@VsoObject(
        name = "employee",
        create = true,
        strict = true,
        description = "Employee POJO Objects"
)
public class Employee implements Serializable {

    public static final String TYPE = "employee";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String STATUS = "status";

    public Employee() {
        this.id = UUID.randomUUID().toString();
    }

    @VsoProperty(readOnly = true, name = "id", displayName = "Entity Id", description = "Unique identifier of this node")
    private String id;

    @VsoProperty(name = "name", displayName = "Entity Name", description = "Entity name")
    private String name;

    @VsoProperty(name = "age", displayName = "Entity Age", description = "Entity Age")
    private Integer age;

    @VsoProperty(name = "status", displayName = "Entity Status", description = "Entity Status")
    private Boolean status;

}
