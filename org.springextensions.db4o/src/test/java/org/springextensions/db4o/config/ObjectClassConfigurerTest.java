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

import org.springextensions.db4o.example.Person;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.db4o.config.ObjectClass;
import com.db4o.io.PagingMemoryStorage;

/**
 * author: olli
 */
// TODO: write proper tests
public class ObjectClassConfigurerTest {

    public static final int MAXIMUM_ACTIVATION_DEPTH = 100;

    public static final int MINIMUM_ACTIVATION_DEPTH = 10;

    @Test
    public void testObjectClass() throws Exception {
        EmbeddedConfigurationFactoryBean embeddedConfigurationFactoryBean = new EmbeddedConfigurationFactoryBean();
        embeddedConfigurationFactoryBean.getFile().setStorage(new PagingMemoryStorage());

        ObjectClassConfigurer objectClassConfigurer = new ObjectClassConfigurer(embeddedConfigurationFactoryBean.getCommon(), Person.class);
        ObjectClass objectClass = objectClassConfigurer.getObjectClass();

        objectClassConfigurer.setMaximumActivationDepth(MAXIMUM_ACTIVATION_DEPTH);
        objectClassConfigurer.setMinimumActivationDepth(MINIMUM_ACTIVATION_DEPTH);

        Assert.assertNotNull(objectClass);

        // Assert.assertEquals(MAXIMUM_ACTIVATION_DEPTH, objectClass. ???);

        Assert.assertEquals(MINIMUM_ACTIVATION_DEPTH, objectClass.minimumActivationDepth());
    }

}
