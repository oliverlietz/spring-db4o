package com.db4o.springframework;

import junit.framework.TestCase;

import com.db4o.ObjectContainer;
import com.db4o.ext.ExtObjectContainer;
import com.db4o.ext.MemoryFile;

/**
 * ObjectContainer FactoryBean tests. 
 * 
 * @author Costin Leau
 *
 */
public class ObjectContainerFactoryBeanTests extends TestCase {
	private ObjectContainerFactoryBean containerFB;

	protected void setUp() throws Exception {
		super.setUp();
		containerFB = new ObjectContainerFactoryBean();
		containerFB.setMemoryFile(new MemoryFile());
		containerFB.afterPropertiesSet();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		containerFB.destroy();
	}

	/*
	 * Test method for 'com.db4o.springframework.ObjectContainerFactoryBean.getObjectType()'
	 */
	public void testGetObjectType() {
		assertTrue(ObjectContainer.class.isAssignableFrom(containerFB.getObjectType()));
	}

	/*
	 * Test method for 'com.db4o.springframework.ObjectContainerFactoryBean.isSingleton()'
	 */
	public void testIsSingleton() {
		assertTrue(containerFB.isSingleton());
	}

	/*
	 * Test method for 'com.db4o.springframework.ObjectContainerFactoryBean.afterPropertiesSet()'
	 */
	public void testAfterPropertiesSet() throws Exception {
		containerFB.afterPropertiesSet();

		try {
			containerFB.setMemoryFile(null);
			containerFB.afterPropertiesSet();
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			// it's okay
		}
	}

	/*
	 * Test method for 'com.db4o.springframework.ObjectContainerFactoryBean.destroy()'
	 */
	public void testDestroy() throws Exception {
		assertFalse(((ExtObjectContainer)containerFB.getObject()).isClosed());
		containerFB.destroy();
		assertTrue(((ExtObjectContainer)containerFB.getObject()).isClosed());
	}
}
