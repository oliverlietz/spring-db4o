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

import org.springextensions.db4o.config.FileConfigurer;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.db4o.ObjectServer;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ServerConfiguration;
import com.db4o.io.PagingMemoryStorage;

/**
 * Test class for ObjectServerFactoryBean.
 *
 * @author Costin Leau
 * @author olli
 */
public class ObjectServerFactoryBeanTest {

    private ObjectServerFactoryBean objectServerFactoryBean;

    @BeforeMethod
    public void setUp() throws Exception {
        ServerConfiguration serverConfiguration = Db4oClientServer.newServerConfiguration();
        FileConfigurer fileConfigurer = new FileConfigurer(serverConfiguration.file());
        fileConfigurer.setStorage(new PagingMemoryStorage());
        objectServerFactoryBean = new ObjectServerFactoryBean();
        objectServerFactoryBean.setFilename("ObjectServerFactoryBeanTest");
        objectServerFactoryBean.setPort(0);
        objectServerFactoryBean.setServerConfiguration(serverConfiguration);
        objectServerFactoryBean.initialize();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        objectServerFactoryBean.destroy();
    }

    @Test
    public void testGetObjectType() {
        Assert.assertTrue(ObjectServer.class.isAssignableFrom(objectServerFactoryBean.getObjectType()));
    }

    @Test
    public void testIsSingleton() {
        Assert.assertTrue(objectServerFactoryBean.isSingleton());
    }

    @Test
    public void testInitialize() throws Exception {
        objectServerFactoryBean.setFilename(null);
        try {
            objectServerFactoryBean.initialize();
            Assert.fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

}
