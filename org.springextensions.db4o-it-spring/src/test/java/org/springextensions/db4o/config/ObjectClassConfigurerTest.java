package org.springextensions.db4o.config;

import org.springextensions.db4o.ObjectContainerTest;
import org.springextensions.db4o.example.Person;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * author: olli
 */
@ContextConfiguration
public class ObjectClassConfigurerTest extends ObjectContainerTest {

    @Test
    public void testObjectContainer() {
        Assert.assertNotNull(objectContainer);
        Assert.assertNotNull(db4oOperations);
        Person person = new Person();
        db4oOperations.store(person);
    }

}
