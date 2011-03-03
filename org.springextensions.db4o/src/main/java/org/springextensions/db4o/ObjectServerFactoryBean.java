/*
 * Copyright 2005-2010 the original author or authors.
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

import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.db4o.Db4o;
import com.db4o.ObjectServer;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;

/**
 * FactoryBean for creating {@link com.db4o.ObjectServer}s.
 * This class adds support for configuring user access through the users property
 * which takes a Properties object with key the user name and value the password.
 *
 * @author Costin Leau
 * @author olli
 */
public class ObjectServerFactoryBean { // implements FactoryBean<ObjectServer> { https://jira.springframework.org/browse/OSGI-808

    private ObjectServer server;

    private Resource databaseFile;

    private int port;

    private ServerConfiguration serverConfiguration;

    private Properties users;

    private final Logger logger = LoggerFactory.getLogger(ObjectServerFactoryBean.class);

    public ObjectServerFactoryBean() {
    }

    /**
     * @see com.db4o.cs.Db4oClientServer#openServer(String, int)
     */
    public void setDatabaseFile(Resource databaseFile) {
        this.databaseFile = databaseFile;
    }

    /**
     * @see com.db4o.cs.Db4oClientServer#openServer(String, int)
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @see com.db4o.cs.Db4oClientServer#openServer(com.db4o.cs.config.ServerConfiguration, String, int)
     */
    public void setServerConfiguration(ServerConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;
    }

    /**
     * @see com.db4o.ObjectServer#grantAccess(String, String)
     */
    public void setUsers(Properties users) {
        this.users = users;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public ObjectServer getObject() throws Exception {
        return server;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<? extends ObjectServer> getObjectType() {
        return (server != null ? server.getClass() : ObjectServer.class);
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }

    @PostConstruct
    public void initialize() throws Exception {
        if (databaseFile == null) {
            throw new IllegalArgumentException("database file is required");
        }

        logger.info("database file is {}", databaseFile.getFile().getAbsolutePath());

        if (serverConfiguration == null) {
            server = Db4oClientServer.openServer(databaseFile.getFile().getAbsolutePath(), port);
        } else {
            logger.info("using configuration: server");
            server = Db4oClientServer.openServer(serverConfiguration, databaseFile.getFile().getAbsolutePath(), port);
        }

        logger.info("opened object server {} at port {}", ObjectUtils.getIdentityHexString(server), server.ext().port());
        logger.info(Db4o.version());

        if (users != null) {
            for (Object o : users.entrySet()) {
                Entry entry = (Entry) o;
                String username = (String) entry.getKey();
                String password = (String) entry.getValue();
                server.grantAccess(username, password);
                logger.debug("access granted to user '{}' with password '{}'", username, ObjectServerUtils.maskString(password));
            }
        }
    }

    @PreDestroy
    public void destroy() throws Exception {
        logger.info("closing object server {}", ObjectUtils.getIdentityHexString(server));
        server.close();
    }

}
