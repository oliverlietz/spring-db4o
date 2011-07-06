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

import java.util.List;

import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;
import com.db4o.cs.config.ClientConfigurationItem;

/**
 * @author olli
 */
public class ClientConfigurationFactoryBean { // implements FactoryBean<ClientConfiguration> { https://jira.springframework.org/browse/OSGI-808

    private ClientConfiguration configuration = Db4oClientServer.newClientConfiguration();

    private CommonConfigurer commonConfigurer;

    private NetworkingConfigurer networkingConfigurer;

    public ClientConfigurationFactoryBean() {
    }

    /**
     * @return
     */
    public ClientConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * @return
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public ClientConfiguration getObject() {
        return configuration;
    }

    /**
     * @return
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<ClientConfiguration> getObjectType() {
        return ClientConfiguration.class;
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
     * @see com.db4o.cs.config.ClientConfiguration#common()
     */
    public CommonConfigurer getCommon() {
        if (commonConfigurer == null) {
            commonConfigurer = new CommonConfigurer(configuration.common());
        }
        return commonConfigurer;
    }

    /**
     * @return
     * @see com.db4o.cs.config.ClientConfiguration#networking()
     */
    public NetworkingConfigurer getNetworking() {
        if (networkingConfigurer == null) {
            networkingConfigurer = new NetworkingConfigurer(configuration.networking());
        }
        return networkingConfigurer;
    }

    /**
     * @param prefetchDepth
     * @see com.db4o.cs.config.ClientConfiguration#prefetchDepth(int)
     */
    public void setPrefetchDepth(int prefetchDepth) {
        configuration.prefetchDepth(prefetchDepth);
    }

    /**
     * @param prefetchIDCount
     * @see com.db4o.cs.config.ClientConfiguration#prefetchIDCount(int)
     */
    public void setPrefetchIDCount(int prefetchIDCount) {
        configuration.prefetchIDCount(prefetchIDCount);
    }

    /**
     * @param prefetchObjectCount
     * @see com.db4o.cs.config.ClientConfiguration#prefetchObjectCount(int)
     */
    public void setPrefetchObjectCount(int prefetchObjectCount) {
        configuration.prefetchObjectCount(prefetchObjectCount);
    }

    /**
     * @param prefetchSlotCacheSize
     * @see com.db4o.cs.config.ClientConfiguration#prefetchSlotCacheSize(int)
     */
    public void setPrefetchSlotCacheSize(int prefetchSlotCacheSize) {
        configuration.prefetchSlotCacheSize(prefetchSlotCacheSize);
    }

    /**
     * @param timeoutClientSocket
     * @see com.db4o.cs.config.ClientConfiguration#timeoutClientSocket(int)
     */
    public void setTimeoutClientSocket(int timeoutClientSocket) {
        configuration.timeoutClientSocket(timeoutClientSocket);
    }

    /**
     * @param clientConfigurationItem
     * @see com.db4o.cs.config.ClientConfiguration#addConfigurationItem(com.db4o.cs.config.ClientConfigurationItem)
     */
    public void setConfigurationItem(ClientConfigurationItem clientConfigurationItem) {
        configuration.addConfigurationItem(clientConfigurationItem);
    }

    /**
     * @param clientConfigurationItems
     * @see com.db4o.cs.config.ClientConfiguration#addConfigurationItem(com.db4o.cs.config.ClientConfigurationItem)
     */
    public void setConfigurationItem(List<ClientConfigurationItem> clientConfigurationItems) {
        for (ClientConfigurationItem clientConfigurationItem : clientConfigurationItems) {
            configuration.addConfigurationItem(clientConfigurationItem);
        }
    }

}
