/*
 * Copyright 2005-2009 the original author or authors.
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

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.ExtObjectContainer;
import com.db4o.io.PagingMemoryStorage;
import org.springextensions.db4o.config.FileConfigurer;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * ObjectContainer FactoryBean tests.
 *
 * @author Costin Leau
 */
public class ObjectContainerFactoryBeanTest {

    private ObjectContainerFactoryBean objectContainerFactoryBean;

    @BeforeMethod
    public void setUp() throws Exception {
        EmbeddedConfiguration embeddedConfiguration = Db4oEmbedded.newConfiguration();
        FileConfigurer fileConfigurer = new FileConfigurer(embeddedConfiguration.file());
        fileConfigurer.setStorage(new PagingMemoryStorage());
        objectContainerFactoryBean = new ObjectContainerFactoryBean();
        objectContainerFactoryBean.setName("ObjectContainerFactoryBeanTest");
        objectContainerFactoryBean.setEmbeddedConfiguration(embeddedConfiguration);
        objectContainerFactoryBean.initialize();
    }

    @AfterTest
    public void tearDown() throws Exception {
        objectContainerFactoryBean.destroy();
    }

    /*
      * Test method for 'org.springextensions.db4o.ObjectContainerFactoryBean.getObjectType()'
      */

    @Test
    public void testGetObjectType() {
        AssertJUnit.assertTrue(ObjectContainer.class.isAssignableFrom(objectContainerFactoryBean.getObjectType()));
    }

    /*
      * Test method for 'org.springextensions.db4o.ObjectContainerFactoryBean.isSingleton()'
      */

    @Test
    public void testIsSingleton() {
        AssertJUnit.assertTrue(objectContainerFactoryBean.isSingleton());
    }

    /*
      * Test method for 'org.springextensions.db4o.ObjectContainerFactoryBean.afterPropertiesSet()'
      */
    /*
    @Test
    public void testAfterPropertiesSet() throws Exception {
        objectContainerFactoryBean.afterPropertiesSet();
        try {
            objectContainerFactoryBean.setName(null);
            objectContainerFactoryBean.afterPropertiesSet();
            AssertJUnit.fail("expected illegal argument exception");
        } catch (IllegalArgumentException iae) {
            // it's okay
        }
    }
    */

    /*
      * Test method for 'org.springextensions.db4o.ObjectContainerFactoryBean.destroy()'
      */

    @Test
    public void testDestroy() throws Exception {
        AssertJUnit.assertFalse(((ExtObjectContainer) objectContainerFactoryBean.getObject()).isClosed());
        objectContainerFactoryBean.destroy();
        AssertJUnit.assertTrue(((ExtObjectContainer) objectContainerFactoryBean.getObject()).isClosed());
    }

}
