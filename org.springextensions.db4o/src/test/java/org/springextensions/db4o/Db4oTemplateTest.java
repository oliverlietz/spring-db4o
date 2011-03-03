/*
 * Copyright 2005-2009 the original author or authors.
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

import java.io.IOException;

import org.easymock.MockControl;
import org.easymock.classextension.MockClassControl;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oDatabase;
import com.db4o.ext.Db4oUUID;
import com.db4o.ext.ExtClient;
import com.db4o.ext.ObjectInfo;
import com.db4o.ext.StoredClass;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import com.db4o.reflect.ReflectClass;
import com.db4o.reflect.generic.GenericReflector;

/**
 * Db4o Template tests.
 *
 * @author Costin Leau
 */
public class Db4oTemplateTest {

    private MockControl containerControl, objectSetControl;

    private ExtClient container;

    private ObjectSet set;

    private Db4oTemplate template;

    @BeforeMethod
    public void setUp() throws Exception {
        containerControl = MockControl.createControl(ExtClient.class);
        container = (ExtClient) containerControl.getMock();
        objectSetControl = MockControl.createControl(ObjectSet.class);
        set = (ObjectSet) objectSetControl.getMock();

        template = new Db4oTemplate(container);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        try {
            containerControl.verify();
            objectSetControl.verify();
        }
        catch (IllegalStateException ex) {
            // ignore: test method didn't call replay
        }
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.execute(Db4oCallback)'
      */

    @Test
    public void testExecuteDb4oCallback() {
        final Object result = new Object();

        containerControl.replay();
        // callback for unproxied container
        Db4oCallback action = new Db4oCallback() {
            public Object doInDb4o(ObjectContainer cont) throws RuntimeException {
                AssertJUnit.assertEquals(container, cont);
                // compare proxy / native
                return result;
            }
        };

        // callback for proxied callback
        Db4oCallback proxiedAction = new Db4oCallback() {
            public Object doInDb4o(ObjectContainer cont) throws RuntimeException {
                // compare proxy / native
                AssertJUnit.assertFalse(container == cont);
                AssertJUnit.assertFalse(container.hashCode() == cont.hashCode());
                // if not proxied, mock control will throw an error
                AssertJUnit.assertFalse(cont.close());
                return result;
            }
        };

        // compare results
        AssertJUnit.assertSame(result, template.execute(action, true));
        AssertJUnit.assertSame(result, template.execute(proxiedAction, false));
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.activate(Object, int)'
      */

    @Test
    public void testActivate() {
        Object obj = new Object();
        int depth = 10;
        container.activate(obj, depth);
        containerControl.replay();
        template.activate(obj, depth);
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.deactivate(Object, int)'
      */

    @Test
    public void testDeactivate() {
        Object obj = new Object();
        int depth = 10;
        container.deactivate(obj, depth);
        containerControl.replay();
        template.deactivate(obj, depth);
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.delete(Object)'
      */

    @Test
    public void testDelete() {
        Object obj = new Object();
        container.delete(obj);
        containerControl.replay();
        template.delete(obj);
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.queryByExample(Object)'
      */

    @Test
    public void testQueryByExample() {
        Object obj = new Object();
        objectSetControl.replay();
        containerControl.expectAndReturn(container.queryByExample(obj), set);
        containerControl.replay();

        AssertJUnit.assertSame(set, template.queryByExample(obj));
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.query()'
      */

    public void testQuery() {

        MockControl queryCtrl = MockControl.createControl(Query.class);
        Query query = (Query) queryCtrl.getMock();
        queryCtrl.replay();

        containerControl.expectAndReturn(container.query(), query);
        containerControl.replay();

        AssertJUnit.assertSame(query, template.query());
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.query(Predicate)'
      */

    @Test
    public void testQueryPredicate() {
        Predicate predicate = new Predicate() {
            public boolean match(Object candidate) {
                return false;
            }

            ;
        };

        objectSetControl.replay();
        containerControl.expectAndReturn(container.query(predicate), set);
        containerControl.replay();

        AssertJUnit.assertSame(set, template.query(predicate));
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.store(Object)'
      */

    @Test
    public void testStoreObject() {
        Object obj = new Object();
        container.store(obj);
        containerControl.replay();

        template.store(obj);
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.backup(String)'
      */

    @Test
    public void testBackup() throws IOException {
        String backup = "";
        container.backup(backup);
        containerControl.replay();

        template.backup(backup);

    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.bind(Object, long)'
      */

    @Test
    public void testBind() {
        Object obj = new Object();
        long id = 1234l;

        container.bind(obj, id);
        containerControl.replay();

        template.bind(obj, id);
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.getByID(long)'
      */

    @Test
    public void testGetByID() {
        Object result = new Object();
        long id = 1234l;

        containerControl.expectAndReturn(container.getByID(id), result);
        containerControl.replay();

        AssertJUnit.assertSame(result, template.getByID(id));
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.getByUUID(Db4oUUID)'
      */

    @Test
    public void testGetByUUID() {
        long id = 1234l;
        Db4oUUID uuid = new Db4oUUID(id, new byte[]{});
        Object result = new Object();

        containerControl.expectAndReturn(container.getByUUID(uuid), result);
        containerControl.replay();

        AssertJUnit.assertSame(result, template.getByUUID(uuid));

    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.getID(Object)'
      */

    @Test
    public void testGetID() {
        long id = 1234l;
        Object result = new Object();

        containerControl.expectAndReturn(container.getByID(id), result);
        containerControl.replay();

        AssertJUnit.assertSame(result, template.getByID(id));

    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.getObjectInfo(Object)'
      */

    @Test
    public void testGetObjectInfo() {

        MockControl infoCtrl = MockControl.createControl(ObjectInfo.class);
        ObjectInfo info = (ObjectInfo) infoCtrl.getMock();

        Object obj = new Object();

        containerControl.expectAndReturn(container.getObjectInfo(obj), info);
        containerControl.replay();
        infoCtrl.replay();

        AssertJUnit.assertSame(info, template.getObjectInfo(obj));
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.identity()'
      */

    @Test
    public void testIdentity() {
        Db4oDatabase result = new Db4oDatabase();

        containerControl.expectAndReturn(container.identity(), result);
        containerControl.replay();
        AssertJUnit.assertSame(result, template.identity());
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.isActive(Object)'
      */

    @Test
    public void testIsActive() {
        boolean result = false;
        Object obj = new Object();

        containerControl.expectAndReturn(container.isActive(obj), result);
        containerControl.replay();
        AssertJUnit.assertFalse(template.isActive(obj));

    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.isCached(long)'
      */

    public void testIsCached() {
        boolean result = false;
        long id = 12345l;

        containerControl.expectAndReturn(container.isCached(id), result);
        containerControl.replay();
        AssertJUnit.assertFalse(template.isCached(id));
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.isClosed()'
      */

    @Test
    public void testIsClosed() {
        boolean result = false;

        containerControl.expectAndReturn(container.isClosed(), result);
        containerControl.replay();
        AssertJUnit.assertFalse(template.isClosed());
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.isStored(Object)'
      */

    public void testIsStored() {
        boolean result = false;
        Object obj = new Object();

        containerControl.expectAndReturn(container.isStored(obj), result);
        containerControl.replay();
        AssertJUnit.assertFalse(template.isStored(obj));
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.knownClasses()'
      */

    @Test
    public void testKnownClasses() {
        ReflectClass[] result = new ReflectClass[]{};

        containerControl.expectAndReturn(container.knownClasses(), result);
        containerControl.replay();
        AssertJUnit.assertSame(result, template.knownClasses());
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.lock()'
      */

    @Test
    public void testLock() {
        Object lock = new Object();

        containerControl.expectAndReturn(container.lock(), lock);
        containerControl.replay();
        AssertJUnit.assertSame(lock, template.lock());
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.peekPersisted(Object, int, boolean)'
      */

    @Test
    public void testPeekPersisted() {
        Object obj = new Object();
        int depth = 123;
        boolean committed = false;

        Object result = new Object();

        containerControl.expectAndReturn(container.peekPersisted(obj, depth, committed), result);
        containerControl.replay();
        AssertJUnit.assertSame(result, template.peekPersisted(obj, depth, committed));
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.purge()'
      */

    @Test
    public void testPurge() {
        container.purge();
        containerControl.replay();
        template.purge();
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.purge(Object)'
      */

    @Test
    public void testPurgeObject() {
        Object obj = new Object();

        container.purge(obj);
        containerControl.replay();
        template.purge(obj);
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.reflector()'
      */

    @Test
    public void testReflector() {
        MockControl refCtrl = MockClassControl.createControl(GenericReflector.class);
        GenericReflector reflector = (GenericReflector) refCtrl.getMock();
        refCtrl.replay();

        containerControl.expectAndReturn(container.reflector(), reflector);
        containerControl.replay();
        AssertJUnit.assertSame(reflector, template.reflector());
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.refresh(Object, int)'
      */

    @Test
    public void testRefresh() {
        Object obj = new Object();
        int depth = 1234;

        container.refresh(obj, depth);
        containerControl.replay();
        template.refresh(obj, depth);
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.releaseSemaphore(String)'
      */

    @Test
    public void testReleaseSemaphore() {
        String name = "";

        container.releaseSemaphore(name);
        containerControl.replay();
        template.releaseSemaphore(name);
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.store(Object, int)'
      */

    @Test
    public void testStoreObjectInt() {
        Object obj = new Object();
        int depth = 123;

        container.store(obj, depth);
        containerControl.replay();
        template.store(obj, depth);
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.setSemaphore(String, int)'
      */

    @Test
    public void testSetSemaphore() {
        boolean result = false;
        String name = "";
        int wait = 123;

        containerControl.expectAndReturn(container.setSemaphore(name, wait), result);
        containerControl.replay();
        AssertJUnit.assertFalse(template.setSemaphore(name, wait));

    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.storedClass(Object)'
      */

    @Test
    public void testStoredClass() {
        MockControl classCtrl = MockControl.createControl(StoredClass.class);
        StoredClass clazz = (StoredClass) classCtrl.getMock();

        Object obj = new Object();

        containerControl.expectAndReturn(container.storedClass(obj), clazz);
        containerControl.replay();
        AssertJUnit.assertSame(clazz, template.storedClass(obj));
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.storedClasses()'
      */

    @Test
    public void testStoredClasses() {
        StoredClass[] result = new StoredClass[]{};

        Object obj = new Object();

        containerControl.expectAndReturn(container.storedClasses(), result);
        containerControl.replay();
        AssertJUnit.assertSame(result, template.storedClasses());
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oTemplate.version()'
      */

    @Test
    public void testVersion() {
        long result = 1234;

        containerControl.expectAndReturn(container.version(), result);
        containerControl.replay();
        AssertJUnit.assertEquals(result, template.version());
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oAccessor.afterPropertiesSet()'
      */

    @Test
    public void testAfterPropertiesSet() {
        try {
            template.setObjectContainer(null);
            template.afterPropertiesSet();
            AssertJUnit.fail("expected illegal argument exception");
        }
        catch (RuntimeException e) {
            // it's okay. it's expected exception
        }
    }

    /*
      * Test method for 'org.springextensions.db4o.Db4oAccessor.convertDb4oAccessException(Exception)'
      */
    /*
    @Test
	public void testConvertDb4oAccessException() {

		try {
			createTemplate().execute(new Db4oCallback() {
				public Object doInDb4o(ObjectContainer container) {
					throw new DatabaseFileLockedException("");
				}
			});
			fail("Should have thrown DataAccessResourceFailureException");
		}
		catch (DataAccessResourceFailureException ex) {
			// expected
		}
		try {
			createTemplate().execute(new Db4oCallback() {
				public Object doInDb4o(ObjectContainer container) {
                    // TODO: fix
					ReflectClass refClass = new JdkClass(new JdkReflector(getClass().getClassLoader()), this.getClass());
					throw new ObjectNotStorableException(refClass);
				}
			});
			fail("Should have thrown InvalidDataAccessApiUsageException");
		}
		catch (InvalidDataAccessApiUsageException ex) {
			// expected
		}

	}
	*/

    private Db4oOperations createTemplate() {
        containerControl.reset();

        Db4oTemplate tmpl = new Db4oTemplate(container);
        containerControl.replay();
        return tmpl;
    }

}
