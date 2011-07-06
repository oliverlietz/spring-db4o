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

import java.util.Arrays;
import java.util.List;

import com.db4o.cs.config.ServerConfiguration;
import com.db4o.cs.config.ServerConfigurationItem;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;

/**
 * author: olli
 */
public class ServerConfigurationFactoryBeanTest {

    private ServerConfigurationFactoryBean serverConfigurationFactoryBean;

    @BeforeClass
    public void setup() {
        serverConfigurationFactoryBean = new ServerConfigurationFactoryBean();
    }

    @Test
    public void testGetConfiguration() {
        Assert.assertNotNull(serverConfigurationFactoryBean.getConfiguration());
        Assert.assertTrue(serverConfigurationFactoryBean.getConfiguration() instanceof ServerConfiguration);
    }

    @Test
    public void testGetObject() {
        Assert.assertNotNull(serverConfigurationFactoryBean.getObject());
        Assert.assertTrue(serverConfigurationFactoryBean.getObject() instanceof ServerConfiguration);
    }

    @Test
    public void testGetObjectType() {
        Assert.assertTrue(serverConfigurationFactoryBean.getObjectType().isAssignableFrom(ServerConfiguration.class));
    }

    @Test
    public void testIsSingleton() {
        Assert.assertTrue(serverConfigurationFactoryBean.isSingleton());
    }

    @Test
    public void testGetCommon() {
        Assert.assertNotNull(serverConfigurationFactoryBean.getCommon());
        Assert.assertTrue(serverConfigurationFactoryBean.getCommon() instanceof CommonConfigurer);
    }

    @Test
    public void testGetNetworking() {
        Assert.assertNotNull(serverConfigurationFactoryBean.getNetworking());
        Assert.assertTrue(serverConfigurationFactoryBean.getNetworking() instanceof NetworkingConfigurer);
    }

    @Test
    public void testGetFile() {
        Assert.assertNotNull(serverConfigurationFactoryBean.getFile());
        Assert.assertTrue(serverConfigurationFactoryBean.getFile() instanceof FileConfigurer);
    }

    @Test
    public void testGetIdSystem() {
        Assert.assertNotNull(serverConfigurationFactoryBean.getIdSystem());
        Assert.assertTrue(serverConfigurationFactoryBean.getIdSystem() instanceof IdSystemConfigurer);
    }

    @Test
    public void testSetTimeoutServerSocket() {
        serverConfigurationFactoryBean.setTimeoutServerSocket(600000);
        // TODO verify
    }

    @Test
    public void testSetConfigurationItem() {
        ServerConfigurationItem serverConfigurationItem = mock(ServerConfigurationItem.class);
        serverConfigurationFactoryBean.setConfigurationItem(serverConfigurationItem);
        // TODO verify
    }

    @Test
    public void testSetConfigurationItems() {
        List<ServerConfigurationItem> serverConfigurationItems = Arrays.asList(mock(ServerConfigurationItem.class), mock(ServerConfigurationItem.class), mock(ServerConfigurationItem.class));
        serverConfigurationFactoryBean.setConfigurationItem(serverConfigurationItems);
        // TODO verify
    }

}
