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

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.ExtObjectContainer;
import com.db4o.io.PagingMemoryStorage;

/**
 * @author Costin Leau
 * @author olli
 */
public class ObjectContainerFactoryBeanTest {

    private ObjectContainerFactoryBean objectContainerFactoryBean;

    @BeforeMethod
    public void setUp() throws Exception {
        EmbeddedConfiguration embeddedConfiguration = Db4oEmbedded.newConfiguration();
        FileConfigurer fileConfigurer = new FileConfigurer(embeddedConfiguration.file());
        fileConfigurer.setStorage(new PagingMemoryStorage());
        objectContainerFactoryBean = new ObjectContainerFactoryBean();
        objectContainerFactoryBean.setFilename("ObjectContainerFactoryBeanTest");
        objectContainerFactoryBean.setEmbeddedConfiguration(embeddedConfiguration);
        objectContainerFactoryBean.initialize();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        objectContainerFactoryBean.destroy();
    }

    @Test
    public void testGetObjectType() {
        Assert.assertTrue(ObjectContainer.class.isAssignableFrom(objectContainerFactoryBean.getObjectType()));
    }

    @Test
    public void testIsSingleton() {
        Assert.assertTrue(objectContainerFactoryBean.isSingleton());
    }

    @Test
    public void testTaintedConfiguration() throws Exception {
        try {
            objectContainerFactoryBean.initialize();
            Assert.fail("expected illegal argument exception: tainted configuration");
        } catch (IllegalArgumentException e) {
            // expected "Configuration already used."
        }
    }

    @Test
    public void testDestroy() throws Exception {
        Assert.assertFalse(((ExtObjectContainer) objectContainerFactoryBean.getObject()).isClosed());
        objectContainerFactoryBean.destroy();
        Assert.assertTrue(((ExtObjectContainer) objectContainerFactoryBean.getObject()).isClosed());
    }

}
