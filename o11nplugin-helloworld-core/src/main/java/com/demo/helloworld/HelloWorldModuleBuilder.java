package com.demo.helloworld;

import com.demo.helloworld.vo.Employee;
import com.vmware.o11n.plugin.sdk.module.ModuleBuilder;

public final class HelloWorldModuleBuilder extends ModuleBuilder {

    private static final String DESCRIPTION = "Hello World plug-in for vRealize Orchestrator";
    private static final String DISPLAYNAME = "HelloWorld";
    private static final String DATASOURCE = "main-datasource";
    private static final String PLUGIN_NAME = "HelloWorld";

    static final String ROOT = "ROOT_NODE";
    static final String NODERELATION = "Nodes";

    @Override
    public void configure() {
        module(PLUGIN_NAME)
                .displayName(DISPLAYNAME)
                .withDescription(DESCRIPTION)
                .withImage("images/default-16x16.png")
                .buildNumber("${build.number}")
                .basePackages(HelloWorldModuleBuilder.class.getPackage().getName()).version("${project.version}")
                .installation(InstallationMode.VERSION)
                .action(ActionType.INSTALL_PACKAGE, "packages/${project.artifactId}-package-${project.version}.package");

        finderDatasource(HelloWorldPluginAdaptor.class, DATASOURCE).anonymousLogin(LoginMode.INTERNAL);

        inventory(ROOT);
        finder(ROOT, DATASOURCE).addRelation(Employee.TYPE, NODERELATION).hide(true);
    }
}
