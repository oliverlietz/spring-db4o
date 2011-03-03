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

import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.io.PagingMemoryStorage;
import org.springextensions.db4o.ObjectContainerFactoryBean;
import org.springextensions.db4o.example.Person;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * @author olli
 */
public class EmbeddedConfigurationFactoryBeanTest {

    @Test
    public void testFileConfigurator() throws Exception {
        EmbeddedConfigurationFactoryBean embeddedConfigurationFactoryBean = new EmbeddedConfigurationFactoryBean();
        embeddedConfigurationFactoryBean.getFile().setStorage(new PagingMemoryStorage());

        EmbeddedConfiguration embeddedConfiguration = embeddedConfigurationFactoryBean.getObject();
        AssertJUnit.assertNotNull("embeddedConfiguration is null", embeddedConfiguration);
        AssertJUnit.assertNotNull("file config is null", embeddedConfiguration.file());
        AssertJUnit.assertNotNull("storage config is null", embeddedConfiguration.file().storage());
        AssertJUnit.assertTrue(embeddedConfiguration.file().storage() instanceof PagingMemoryStorage);

        ObjectContainerFactoryBean objectContainerFactoryBean = new ObjectContainerFactoryBean();
        objectContainerFactoryBean.setName("EmbeddedConfigurationFactoryBeanTest");
        objectContainerFactoryBean.setEmbeddedConfiguration(embeddedConfigurationFactoryBean.getObject());
        objectContainerFactoryBean.initialize();

        ObjectContainer objectContainer = objectContainerFactoryBean.getObject();
        Person person = new Person();
        person.setName("olli");
        objectContainer.store(person);
    }

}
