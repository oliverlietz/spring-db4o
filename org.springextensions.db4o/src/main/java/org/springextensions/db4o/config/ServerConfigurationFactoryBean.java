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

    private CommonConfigurer commonConfigurer = new CommonConfigurer(configuration.common());

    private NetworkingConfigurer networkingConfigurer = new NetworkingConfigurer(configuration.networking());

    private FileConfigurer fileConfigurer = new FileConfigurer(configuration.file());

    private IdSystemConfigurer idSystemConfigurer = new IdSystemConfigurer(configuration.idSystem());

    public ServerConfigurationFactoryBean() {
    }

    public ServerConfiguration getConfiguration() throws Exception {
        return configuration;
    }

    /**
     * @return
     * @throws Exception
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public ServerConfiguration getObject() throws Exception {
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
        return commonConfigurer;
    }

    /**
     * @return
     * @see com.db4o.cs.config.ServerConfiguration#networking()
     */
    public NetworkingConfigurer getNetworking() {
        return networkingConfigurer;
    }

    /**
     * @return
     * @see com.db4o.cs.config.ServerConfiguration#file()
     */
    public FileConfigurer getFile() {
        return fileConfigurer;
    }

    /**
     * @return
     * @see com.db4o.cs.config.ServerConfiguration#idSystem()
     */
    public IdSystemConfigurer getIdSystem() {
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
