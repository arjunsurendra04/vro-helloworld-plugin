package com.demo.helloworld;

import java.util.List;
import java.util.Collections;
import ch.dunes.vso.sdk.api.QueryResult;
import ch.dunes.vso.sdk.endpoints.IEndpointConfiguration;
import com.demo.helloworld.vo.Employee;
import com.vmware.o11n.plugin.sdk.spring.AbstractSpringPluginFactory;
import com.vmware.o11n.plugin.sdk.spring.InventoryRef;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.demo.helloworld.mgr.HelloWorldManager;

//Spring injection done via xml.
public final class HelloWorldPluginFactory extends AbstractSpringPluginFactory {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldPluginFactory.class);

    private static Map<String, Employee> nodes = new ConcurrentHashMap<>();

    @Autowired
    public HelloWorldManager manager;

    @Override
    public Object find(InventoryRef ref) {
        log.info("find:: ref: {}", ref);
        if (ref.isOfType(Employee.TYPE)) {
            Employee obj = nodes.get(ref.getId());
            if (obj == null) {
                log.info("Unable to find in cache!");
                rebuildCache();
            }
            return obj;
        }
        return null;
    }

    @Override
    public QueryResult findAll(String type, String query) {
        log.info("findAll:: type: {},query: {}", type, query);
        if (type.equals(Employee.TYPE)) {

            if (nodes.isEmpty()) {
                rebuildCache();
            }

            QueryResult queryResult = new QueryResult();
            nodes.values().forEach((obj) -> {
                queryResult.addElement(obj);
            });
            return queryResult;
        }
        return null;
    }

    @Override
    public List<?> findChildrenInRootRelation(String type, String relationName) {
        log.info("findChildrenInRootRelation:: type: {},relationName: {}", type, relationName);
        if (type.equals(HelloWorldModuleBuilder.ROOT)) {
            if (relationName.equals(HelloWorldModuleBuilder.NODERELATION)) {
                return findAll(Employee.TYPE, null).getElements();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<?> findChildrenInRelation(InventoryRef parent, String relationName) {
        log.info("findChildrenInRelation:: parent: {},relationName: {}", parent, relationName);
        return Collections.emptyList();
    }

    // invalidateAll may be called from within the plugin or from the vCO client when clicking "refresh" in the inventory
    // No other factory methods are called after invalidateAll was called
    @Override
    public void invalidateAll() {
        log.info("Running INVALIDATEALL..");
        rebuildCache();
        super.invalidateAll();
        log.info("Finished running INVALIDATEALL.");
    }

    @Override
    public void invalidate(String type, String id) {
        log.info("Running INVALIDATE for type '" + type + "' and id '" + id + "'...");
        if (type.equals(Employee.TYPE)) {
            // reload the node configuration from EndpointConfiguration
            log.info("Found node with id '" + id + "'.");
            rebuildCache(id);
            super.invalidate(type, id);
            log.info("Finished running INVALIDATE for type '" + type + "' and id '" + id + "'.");
        }
    }

    private Employee makeNode(IEndpointConfiguration config) {
        Employee object = createScriptingObject(Employee.class);
        object.setId(config.getString(Employee.ID));
        object.setName(config.getString(Employee.NAME));
        object.setAge(config.getAsInteger(Employee.AGE));
        object.setStatus(config.getAsBoolean(Employee.STATUS));
        return object;
    }

    private void rebuildCache() {
        log.info("Rebuilding full cache. Size: {}", nodes.size());
        /* Do not just simply clear the cache because e.g. find() requests executed while the new cache is being build 
        * would result in cache misses and trigger a cache rebuild, causing multiple inventory-objects to be created.
        * Instead, build the new cache into a newNode array, and replace it in a synchronized way
         */
        Map<String, Employee> newNodes = new ConcurrentHashMap<>();

        // Load node data from EndpointConfiguration
        for (IEndpointConfiguration config : manager.getAllEmployees()) {
            if (config != null) {
                // create scripting objects, reload child objects & add to cache
                newNodes.put(config.getId(), makeNode(config));
            }
        }
        // replace old list - must be in synchronized block
        synchronized (nodes) {
            nodes = newNodes;
        }
        log.info("Finished full cache rebuild. Size: {}", nodes.size());
    }

    private void rebuildCache(String nodeId) {
        log.info("Rebuilding cache for node:{}, size: {}", nodeId, nodes.size());
        // Remove the node which should be refreshed if present in the cache - must be in synchronized block
        synchronized (nodes) {
            nodes.remove(nodeId);
            log.info("Cleared cache for node ' " + nodeId + "'.");
        }

        // Reload the node from the EndpointConfiguration and add it to our synchronized list
        Employee node = makeNode(manager.getEmployee(nodeId));
        nodes.put(node.getId(), node);
        log.info("Finished cache rebuild for node: {},Size: {} ", nodeId, nodes.size());
    }

}
