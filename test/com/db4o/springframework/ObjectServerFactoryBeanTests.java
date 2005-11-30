package com.db4o.springframework;

import java.util.Properties;

import junit.framework.TestCase;

import org.springframework.core.io.ClassPathResource;

import com.db4o.ObjectServer;
import com.db4o.ext.ExtObjectServer;

/**
 * Test class for Object Server FactoryBean.
 * 
 * @author Costin Leau
 *
 */
public class ObjectServerFactoryBeanTests extends TestCase {

	private ObjectServerFactoryBean serverFB;

	protected void setUp() throws Exception {
		super.setUp();
		serverFB = new ObjectServerFactoryBean();
		serverFB.setDatabaseFile(new ClassPathResource("testdb.file"));
		serverFB.setPort(0);
		serverFB.afterPropertiesSet();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		serverFB.destroy();
	}

	/*
	 * Test method for 'com.db4o.springframework.ObjectServerFactoryBean.getObjectType()'
	 */
	public void testGetObjectType() {
		assertTrue(ObjectServer.class.isAssignableFrom(serverFB.getObjectType()));
	}

	/*
	 * Test method for 'com.db4o.springframework.ObjectServerFactoryBean.isSingleton()'
	 */
	public void testIsSingleton() {
		assertTrue(serverFB.isSingleton());
	}

	/*
	 * Test method for 'com.db4o.springframework.ObjectServerFactoryBean.afterPropertiesSet()'
	 */
	public void testAfterPropertiesSet() throws Exception {
		serverFB.setDatabaseFile(null);
		try {
			serverFB.afterPropertiesSet();
			fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException iae) {
			// it's okay
		}

	}

}
