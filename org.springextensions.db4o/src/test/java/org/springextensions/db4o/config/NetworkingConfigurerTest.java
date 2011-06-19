/*
 * Copyright 2011 the original author or authors.
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * author: olli
 */
public class NetworkingConfigurerTest {

    private NetworkingConfiguration networkingConfiguration;

    private NetworkingConfigurer networkingConfigurer;

    @BeforeClass
    public void setup() {
        networkingConfiguration = mock(NetworkingConfiguration.class);
        networkingConfigurer = new NetworkingConfigurer(networkingConfiguration);
    }

    @Test
    public void testSetBatchMessages() {
        boolean batchMessages = true;
        networkingConfigurer.setBatchMessages(batchMessages);
        verify(networkingConfiguration).batchMessages(batchMessages);
    }

    @Test
    public void testSetClientServerFactory() {
        ClientServerFactory clientServerFactory = mock(ClientServerFactory.class);
        networkingConfigurer.setClientServerFactory(clientServerFactory);
        verify(networkingConfiguration).clientServerFactory(clientServerFactory);
    }

    @Test
    public void testSetMaxBatchQueueSize() {
        int maxBatchQueueSize = 1024;
        networkingConfigurer.setMaxBatchQueueSize(maxBatchQueueSize);
        verify(networkingConfiguration).maxBatchQueueSize(maxBatchQueueSize);
    }

    @Test
    public void testSetMessageRecipient() {
        MessageRecipient messageRecipient = mock(MessageRecipient.class);
        networkingConfigurer.setMessageRecipient(messageRecipient);
        verify(networkingConfiguration).messageRecipient(messageRecipient);
    }

    @Test
    public void testSetSingleThreadedClient() {
        boolean singleThreadedClient = true;
        networkingConfigurer.setSingleThreadedClient(singleThreadedClient);
        verify(networkingConfiguration).singleThreadedClient(singleThreadedClient);
    }

    @Test
    public void testSetSocketFactory() {
        Socket4Factory socket4Factory = mock(Socket4Factory.class);
        networkingConfigurer.setSocketFactory(socket4Factory);
        verify(networkingConfiguration).socketFactory(socket4Factory);
    }

}
