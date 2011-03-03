package org.springextensions.db4o;

import com.db4o.ObjectContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author olli
 */
public class ObjectContainerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    protected ObjectContainer objectContainer;

    @Autowired
    protected Db4oOperations db4oOperations;

}
