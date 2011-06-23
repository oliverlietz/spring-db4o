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

import com.db4o.cs.config.ClientServerFactory;
import com.db4o.cs.config.NetworkingConfiguration;
import com.db4o.cs.foundation.Socket4Factory;
import com.db4o.messaging.MessageRecipient;

/**
 * @author olli
 * @see <a href="http://developer.db4o.com/Documentation/Reference/db4o-8.0/java/reference/Content/configuration/network_configuration.htm">Networking Configuration</a>
 */
public class NetworkingConfigurer {

    protected NetworkingConfiguration networkingConfiguration;

    public NetworkingConfigurer(NetworkingConfiguration networkingConfiguration) {
        this.networkingConfiguration = networkingConfiguration;
    }

    /**
     * @param batchMessages
     * @see com.db4o.cs.config.NetworkingConfiguration#batchMessages(boolean)
     */
    public void setBatchMessages(boolean batchMessages) {
        networkingConfiguration.batchMessages(batchMessages);
    }

    /**
     * @param clientServerFactory
     * @see com.db4o.cs.config.NetworkingConfiguration#clientServerFactory(com.db4o.cs.config.ClientServerFactory)
     */
    public void setClientServerFactory(ClientServerFactory clientServerFactory) {
        networkingConfiguration.clientServerFactory(clientServerFactory);
    }

    /**
     * @param maxSize
     * @see com.db4o.cs.config.NetworkingConfiguration#maxBatchQueueSize(int)
     */
    public void setMaxBatchQueueSize(int maxSize) {
        networkingConfiguration.maxBatchQueueSize(maxSize);
    }

    /**
     * @param messageRecipient
     * @see com.db4o.cs.config.NetworkingConfiguration#messageRecipient(com.db4o.messaging.MessageRecipient)
     */
    public void setMessageRecipient(MessageRecipient messageRecipient) {
        networkingConfiguration.messageRecipient(messageRecipient);
    }

    /**
     * @param singleThreadedClient
     * @see com.db4o.cs.config.NetworkingConfiguration#singleThreadedClient(boolean)
     */
    public void setSingleThreadedClient(boolean singleThreadedClient) {
        networkingConfiguration.singleThreadedClient(singleThreadedClient);
    }

    /**
     * @param socket4Factory
     * @see com.db4o.cs.config.NetworkingConfiguration#socketFactory(com.db4o.cs.foundation.Socket4Factory)
     */
    public void setSocketFactory(Socket4Factory socket4Factory) {
        networkingConfiguration.socketFactory(socket4Factory);
    }

}
