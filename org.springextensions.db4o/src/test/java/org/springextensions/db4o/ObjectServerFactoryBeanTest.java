/*
 * Copyright 2005-2010 the original author or authors.
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

import com.db4o.ObjectServer;
import org.springframework.core.io.ClassPathResource;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for ObjectServerFactoryBean.
 *
 * @author Costin Leau
 */
public class ObjectServerFactoryBeanTest {

    private ObjectServerFactoryBean objectServerFactoryBean;

    @BeforeMethod
    public void setUp() throws Exception {
        objectServerFactoryBean = new ObjectServerFactoryBean();
        objectServerFactoryBean.setDatabaseFile(new ClassPathResource("testdb.file"));
        objectServerFactoryBean.setPort(0);
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
        objectServerFactoryBean.setDatabaseFile(null);
        try {
            objectServerFactoryBean.initialize();
            Assert.fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

}
