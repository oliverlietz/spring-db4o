/*
 * Copyright 2010-2011 the original author or authors.
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
package org.springextensions.db4o.config;

import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ServerConfiguration;

/**
 * @author olli
 */
public class ServerConfigurationFactoryBean { // implements FactoryBean<ServerConfiguration> { https://jira.springframework.org/browse/OSGI-808

    private ServerConfiguration configuration = Db4oClientServer.newServerConfiguration();

    private CommonConfigurer commonConfigurer;

    private NetworkingConfigurer networkingConfigurer;

    private FileConfigurer fileConfigurer;

    private IdSystemConfigurer idSystemConfigurer;

    public ServerConfigurationFactoryBean() {
    }

    /**
     * @return
     */
    public ServerConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * @return
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public ServerConfiguration getObject() {
        return configuration;
    }

    /**
     * @return
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<ServerConfiguration> getObjectType() {
        return ServerConfiguration.class;
    }

    /**
     * @return
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * @return
     * @see com.db4o.cs.config.ServerConfiguration#common()
     */
    public CommonConfigurer getCommon() {
        if (commonConfigurer == null) {
            commonConfigurer = new CommonConfigurer(configuration.common());
        }
        return commonConfigurer;
    }

    /**
     * @return
     * @see com.db4o.cs.config.ServerConfiguration#networking()
     */
    public NetworkingConfigurer getNetworking() {
        if (networkingConfigurer == null) {
            networkingConfigurer = new NetworkingConfigurer(configuration.networking());
        }
        return networkingConfigurer;
    }

    /**
     * @return
     * @see com.db4o.cs.config.ServerConfiguration#file()
     */
    public FileConfigurer getFile() {
        if (fileConfigurer == null) {
            fileConfigurer = new FileConfigurer(configuration.file());
        }
        return fileConfigurer;
    }

    /**
     * @return
     * @see com.db4o.cs.config.ServerConfiguration#idSystem()
     */
    public IdSystemConfigurer getIdSystem() {
        if (idSystemConfigurer == null) {
            idSystemConfigurer = new IdSystemConfigurer(configuration.idSystem());
        }
        return idSystemConfigurer;
    }

    /**
     * @param milliseconds
     * @see com.db4o.cs.config.ServerConfiguration#timeoutServerSocket(int)
     */
    public void setTimeoutServerSocket(int milliseconds) {
        configuration.timeoutServerSocket(milliseconds);
    }

    /* TODO
    public void addConfigurationItem(ServerConfigurationItem serverConfigurationItem) {
        configuration.addConfigurationItem(serverConfigurationItem);
    }
    */

}
