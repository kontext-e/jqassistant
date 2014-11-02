package com.buschmais.jqassistant.core.store.impl;

import java.io.File;
import java.util.Collection;

import org.neo4j.kernel.GraphDatabaseAPI;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.xo.api.XOManager;
import com.buschmais.xo.api.XOManagerFactory;
import com.buschmais.xo.api.bootstrap.XO;
import com.buschmais.xo.api.bootstrap.XOUnit;
import com.buschmais.xo.api.bootstrap.XOUnitBuilder;
import com.buschmais.xo.neo4j.api.Neo4jXOProvider;
import com.buschmais.xo.neo4j.impl.datastore.EmbeddedNeo4jDatastoreSession;

/**
 * {@link Store} implementation using an embedded Neo4j instance.
 */
public class EmbeddedGraphStore extends AbstractGraphStore {

    private static final String PROPERTY_NEO4J_CACHE_TYPE = "neo4j.cache_type";
    private static final String VALUE_NEO4J_CACHE_TYPE_WEAK = "weak";
    private static final String PROPERTY_NEO4J_ALLOW_STORE_UPGRADE = "neo4j.allow_store_upgrade";

    /**
     * The directory of the database.
     */
    private final String databaseDirectory;

    /**
     * Constructor.
     *
     * @param databaseDirectory
     *            The directory of the database.
     */
    public EmbeddedGraphStore(String databaseDirectory) {
        this.databaseDirectory = databaseDirectory;
    }

    @Override
    protected GraphDatabaseAPI getDatabaseAPI(XOManager xoManager) {
        return (GraphDatabaseAPI) xoManager.getDatastoreSession(EmbeddedNeo4jDatastoreSession.class).getGraphDatabaseService();
    }

    @Override
    protected XOManagerFactory createXOManagerFactory(Collection<Class<?>> types) {
        File database = new File(databaseDirectory);
        XOUnit xoUnit = XOUnitBuilder.create(database.toURI(), Neo4jXOProvider.class, types.toArray(new Class<?>[0]))
                .property(PROPERTY_NEO4J_CACHE_TYPE, VALUE_NEO4J_CACHE_TYPE_WEAK).property(PROPERTY_NEO4J_ALLOW_STORE_UPGRADE, Boolean.TRUE.toString())
                .create();
        return XO.createXOManagerFactory(xoUnit);
    }

    @Override
    protected void closeXOManagerFactory(XOManagerFactory cdoManagerFactory) {
        cdoManagerFactory.close();
    }

}
