package org.springextensions.db4o;

import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author olli
 */
@ContextConfiguration
public class RemoteClientServerTest extends ObjectContainerTest {

    @Test
    public void testObjectContainer() {
        Assert.assertNotNull(objectContainer);
        Assert.assertNotNull(db4oOperations);
        Object object = new Object();
        db4oOperations.store(object);
    }

}
