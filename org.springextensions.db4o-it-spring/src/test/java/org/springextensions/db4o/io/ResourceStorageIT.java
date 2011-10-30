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
package org.springextensions.db4o.io;

import com.db4o.consistency.ConsistencyChecker;
import com.db4o.consistency.ConsistencyReport;
import org.springextensions.db4o.ObjectContainerIT;
import org.springextensions.db4o.example.Person;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * author: olli
 */
@ContextConfiguration
public class ResourceStorageIT extends ObjectContainerIT {

    @Test
    public void testSetup() {
        Assert.assertNotNull(objectContainer);
        Assert.assertNotNull(db4oOperations);
    }

    @Test
    public void testDatabaseConsistency() {
        ConsistencyChecker checker = new ConsistencyChecker(objectContainer);
        ConsistencyReport report = checker.checkSlotConsistency();
        Assert.assertTrue(report.consistent());
    }

    @Test
    public void testDatabaseContent() {
        int count = objectContainer.query(Person.class).size();
        Assert.assertTrue(count == 1);
    }

}
