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
package org.springextensions.db4o.support;

import org.springextensions.db4o.Db4oTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.db4o.ObjectContainer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Costin Leau
 * @author olli
 */
public class Db4oDaoSupportTest {

    private ObjectContainer container;

    private Db4oTemplate template;

    @BeforeMethod
    public void setUp() throws Exception {
        container = mock(ObjectContainer.class);
        template = mock(Db4oTemplate.class);
    }

    @Test
    public void testConfigureWithObjectContainer() {
        Db4oDaoSupport dao = new Db4oDaoSupport() {
        };
        dao.setObjectContainer(container);
        dao.afterPropertiesSet();

        Assert.assertNotNull(dao.getDb4oTemplate());
        Assert.assertEquals(container, dao.getDb4oTemplate().getObjectContainer());
        Assert.assertEquals(container, dao.getObjectContainer());
    }

    @Test
    public void testConfigureWithTemplate() {
        when(template.getObjectContainer()).thenReturn(container);

        Db4oDaoSupport dao = new Db4oDaoSupport() {
        };
        dao.setDb4oTemplate(template);
        dao.afterPropertiesSet();

        Assert.assertEquals(template, dao.getDb4oTemplate());
        Assert.assertEquals(container, dao.getDb4oTemplate().getObjectContainer());
        Assert.assertEquals(container, dao.getObjectContainer());
    }

    @Test
    public void testMissingConfiguration() {
        try {
            Db4oDaoSupport dao = new Db4oDaoSupport() {
            };
            dao.afterPropertiesSet();
            Assert.fail("Should have thrown IllegalArgumentException because neither #setObjectContainer nor #setTemplate have been invoked");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

}
