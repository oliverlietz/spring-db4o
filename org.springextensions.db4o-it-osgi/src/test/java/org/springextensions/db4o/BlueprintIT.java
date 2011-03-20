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
package org.springextensions.db4o;

import com.db4o.events.EventRegistry;
import org.apache.karaf.testing.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.MavenConfiguredJUnit4TestRunner;

/**
 * @author olli
 */
@RunWith(MavenConfiguredJUnit4TestRunner.class)
public class BlueprintIT extends AbstractIntegrationTest {

    @Test
    public void testDb4oOperations() {
        Db4oOperations db4oOperations = getOsgiService(Db4oOperations.class);
        Assert.assertNotNull("Db4oOperations is null", db4oOperations);
    }

    @Test
    public void testEventRegistry() {
        EventRegistry eventRegistry = getOsgiService(EventRegistry.class);
        Assert.assertNotNull("EventRegistry is null", eventRegistry);
    }

}
