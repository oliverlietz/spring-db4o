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

import com.db4o.cs.config.ClientConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * author: olli
 */
public class ClientConfigurationFactoryBeanTest {

    private ClientConfigurationFactoryBean clientConfigurationFactoryBean;

    @BeforeClass
    public void setup() {
        clientConfigurationFactoryBean = new ClientConfigurationFactoryBean();
    }

    @Test
    public void testGetConfiguration() {
        Assert.assertNotNull(clientConfigurationFactoryBean.getConfiguration());
        Assert.assertTrue(clientConfigurationFactoryBean.getConfiguration() instanceof ClientConfiguration);
    }

    @Test
    public void testGetObject() {
        Assert.assertNotNull(clientConfigurationFactoryBean.getObject());
        Assert.assertTrue(clientConfigurationFactoryBean.getObject() instanceof ClientConfiguration);
    }

    @Test
    public void testGetObjectType() {
        Assert.assertTrue(clientConfigurationFactoryBean.getObjectType().isAssignableFrom(ClientConfiguration.class));
    }

    @Test
    public void testIsSingleton() {
        Assert.assertTrue(clientConfigurationFactoryBean.isSingleton());
    }

    @Test
    public void testGetCommon() {
        Assert.assertNotNull(clientConfigurationFactoryBean.getCommon());
        Assert.assertTrue(clientConfigurationFactoryBean.getCommon() instanceof CommonConfigurer);
    }

    @Test
    public void testGetNetworking() {
        Assert.assertNotNull(clientConfigurationFactoryBean.getNetworking());
        Assert.assertTrue(clientConfigurationFactoryBean.getNetworking() instanceof NetworkingConfigurer);
    }

}
