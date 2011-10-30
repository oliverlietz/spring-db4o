/*
 * Copyright 2005-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springextensions.db4o;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.db4o.Db4oEmbedded;
import com.db4o.Db4oVersion;
import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBeanNotInitializedException;
import org.springframework.util.ObjectUtils;

/**
 * @author Costin Leau
 * @author olli
 */
public class ObjectContainerFactoryBean { // implements FactoryBean<ObjectContainer> { https://jira.springframework.org/browse/OSGI-808

    private ObjectContainer container;

    /**
     * @see com.db4o.Db4oEmbedded#openFile(String)
     */
    private String filename;

    /**
     * @see com.db4o.ObjectServer#openClient()
     */
    private ObjectServer server;

    /**
     * @see com.db4o.cs.Db4oClientServer#openClient(String, int, String, String)
     */
    private String hostname;

    /**
     * @see com.db4o.cs.Db4oClientServer#openClient(String, int, String, String)
     */
    private int port;

    /**
     * @see com.db4o.cs.Db4oClientServer#openClient(String, int, String, String)
     */
    private String username;

    /**
     * @see com.db4o.cs.Db4oClientServer#openClient(String, int, String, String)
     */
    private String password;

    private EmbeddedConfiguration embeddedConfiguration;

    private ClientConfiguration clientConfiguration;

    private final Logger logger = LoggerFactory.getLogger(ObjectContainerFactoryBean.class);

    public ObjectContainerFactoryBean() {
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setServer(ObjectServer server) {
        this.server = server;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmbeddedConfiguration(EmbeddedConfiguration embeddedConfiguration) {
        this.embeddedConfiguration = embeddedConfiguration;
    }

    public void setClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public ObjectContainer getObject() throws Exception {
        if (container == null) {
            throw new FactoryBeanNotInitializedException("object container not opened");
        }
        return container;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<? extends ObjectContainer> getObjectType() {
        return (container != null ? container.getClass() : ObjectContainer.class);
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }

    @PostConstruct
    public void initialize() {
        if (filename != null) {
            container = openEmbeddedContainer(embeddedConfiguration, filename);
        } else if (server != null) {
            container = openEmbeddedClientContainer(server);
        } else if (hostname != null && port > 0 && username != null) {
            container = openRemoteClientContainer(clientConfiguration, hostname, port, username, password);
        } else {
            throw new IllegalArgumentException("mandatory fields are not set: database filename or embedded database server or remote database server (hostname, port, username)");
        }
        logger.info("db4o {}", Db4oVersion.NAME);
    }

    @PreDestroy
    public void destroy() {
        logger.info("closing object container {}", ObjectUtils.getIdentityHexString(container));
        container.close();
    }

    protected ObjectContainer openEmbeddedContainer(EmbeddedConfiguration embeddedConfiguration, String filename) {
        ObjectContainer container;
        if (embeddedConfiguration == null) {
            container = Db4oEmbedded.openFile(filename);
        } else {
            logger.info("using configuration: embedded");
            container = Db4oEmbedded.openFile(embeddedConfiguration, filename);
        }
        logger.info("embedded container opened: {}", ObjectUtils.getIdentityHexString(container));
        return container;
    }

    protected ObjectContainer openEmbeddedClientContainer(ObjectServer server) {
        ObjectContainer container = server.openClient();
        logger.info("embedded client container opened: {}", ObjectUtils.getIdentityHexString(container));
        return container;
    }

    protected ObjectContainer openRemoteClientContainer(ClientConfiguration clientConfiguration, String hostname, int port, String username, String password) {
        ObjectContainer container;
        if (clientConfiguration == null) {
            container = Db4oClientServer.openClient(hostname, port, username, password);
        } else {
            logger.info("using configuration: client");
            container = Db4oClientServer.openClient(clientConfiguration, hostname, port, username, password);
        }
        logger.info("remote client container opened: {}", ObjectUtils.getIdentityHexString(container));
        return container;
    }

}
