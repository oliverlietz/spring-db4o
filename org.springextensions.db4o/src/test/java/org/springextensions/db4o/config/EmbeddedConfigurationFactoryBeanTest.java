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

import java.util.Arrays;
import java.util.List;

import com.db4o.config.EmbeddedConfiguration;
import com.db4o.config.EmbeddedConfigurationItem;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;

/**
 * @author olli
 */
public class EmbeddedConfigurationFactoryBeanTest {

    private EmbeddedConfigurationFactoryBean embeddedConfigurationFactoryBean;

    @BeforeClass
    public void setup() {
        embeddedConfigurationFactoryBean = new EmbeddedConfigurationFactoryBean();
    }

    @Test
    public void testGetConfiguration() {
        Assert.assertNotNull(embeddedConfigurationFactoryBean.getConfiguration());
        Assert.assertTrue(embeddedConfigurationFactoryBean.getConfiguration() instanceof EmbeddedConfiguration);
    }

    @Test
    public void testGetObject() {
        Assert.assertNotNull(embeddedConfigurationFactoryBean.getObject());
        Assert.assertTrue(embeddedConfigurationFactoryBean.getObject() instanceof EmbeddedConfiguration);
    }

    @Test
    public void testGetObjectType() {
        Assert.assertTrue(embeddedConfigurationFactoryBean.getObjectType().isAssignableFrom(EmbeddedConfiguration.class));
    }

    @Test
    public void testIsSingleton() {
        Assert.assertTrue(embeddedConfigurationFactoryBean.isSingleton());
    }

    @Test
    public void testGetCommon() {
        Assert.assertNotNull(embeddedConfigurationFactoryBean.getCommon());
        Assert.assertTrue(embeddedConfigurationFactoryBean.getCommon() instanceof CommonConfigurer);
    }

    @Test
    public void testGetFile() {
        Assert.assertNotNull(embeddedConfigurationFactoryBean.getFile());
        Assert.assertTrue(embeddedConfigurationFactoryBean.getFile() instanceof FileConfigurer);
    }

    @Test
    public void testGetIdSystem() {
        Assert.assertNotNull(embeddedConfigurationFactoryBean.getIdSystem());
        Assert.assertTrue(embeddedConfigurationFactoryBean.getIdSystem() instanceof IdSystemConfigurer);
    }

    @Test
    public void testSetConfigurationItem() {
        EmbeddedConfigurationItem embeddedConfigurationItem = mock(EmbeddedConfigurationItem.class);
        embeddedConfigurationFactoryBean.setConfigurationItem(embeddedConfigurationItem);
        // TODO verify
    }

    @Test
    public void testSetConfigurationItems() {
        List<EmbeddedConfigurationItem> embeddedConfigurationItems = Arrays.asList(mock(EmbeddedConfigurationItem.class), mock(EmbeddedConfigurationItem.class), mock(EmbeddedConfigurationItem.class));
        embeddedConfigurationFactoryBean.setConfigurationItems(embeddedConfigurationItems);
        // TODO verify
    }

}
