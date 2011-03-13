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
package org.springextensions.db4o;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.db4o.ObjectSet;
import com.db4o.ext.ExtClient;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Costin Leau
 * @author olli
 */
public class Db4oTemplateTest {

    private ExtClient container;

    private Db4oTemplate template;

    @BeforeMethod
    public void setUp() throws Exception {
        container = mock(ExtClient.class);
        template = new Db4oTemplate(container);
    }

    /*
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
    */

    @Test
    public void testActivate() {
        Object object = new Object();
        int depth = 10;
        template.activate(object, depth);
        verify(container).activate(object, depth);
    }

    @Test
    public void testDeactivate() {
        Object object = new Object();
        int depth = 10;
        template.deactivate(object, depth);
        verify(container).deactivate(object, depth);
    }

    @Test
    public void testDelete() {
        Object object = new Object();
        template.delete(object);
        verify(container).delete(object);
    }

    @Test
    public void testQueryByExample() {
        ObjectSet<Object> objectSet = mock(ObjectSet.class);
        Object object = new Object();
        when(container.queryByExample(object)).thenReturn(objectSet);
        Assert.assertSame(objectSet, template.queryByExample(object));
    }

    @Test
    public void testQuery() {
        Query query = mock(Query.class);
        when(container.query()).thenReturn(query);
        Assert.assertSame(query, template.query());
    }

    @Test
    public void testQueryPredicate() {
        ObjectSet<Object> objectSet = mock(ObjectSet.class);
        Predicate<Object> predicate = new Predicate<Object>() {
            public boolean match(Object candidate) {
                return false;
            }
        };
        when(container.query(predicate)).thenReturn(objectSet);
        Assert.assertSame(objectSet, template.query(predicate));
    }

    @Test
    public void testStore() {
        Object object = new Object();
        template.store(object);
        verify(container).store(object);
    }

    /*
    @Test
    public void testBackup() throws IOException {
        String backup = "";
        container.backup(backup);
        containerControl.replay();

        template.backup(backup);
    }

    @Test
    public void testBind() {
        Object obj = new Object();
        long id = 1234l;

        container.bind(obj, id);
        containerControl.replay();

        template.bind(obj, id);
    }

    @Test
    public void testGetByID() {
        Object result = new Object();
        long id = 1234l;

        containerControl.expectAndReturn(container.getByID(id), result);
        containerControl.replay();

        AssertJUnit.assertSame(result, template.getByID(id));
    }

    @Test
    public void testGetByUUID() {
        long id = 1234l;
        Db4oUUID uuid = new Db4oUUID(id, new byte[]{});
        Object result = new Object();

        containerControl.expectAndReturn(container.getByUUID(uuid), result);
        containerControl.replay();

        AssertJUnit.assertSame(result, template.getByUUID(uuid));

    }

    @Test
    public void testGetID() {
        long id = 1234l;
        Object result = new Object();

        containerControl.expectAndReturn(container.getByID(id), result);
        containerControl.replay();

        AssertJUnit.assertSame(result, template.getByID(id));

    }

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

    @Test
    public void testIdentity() {
        Db4oDatabase result = new Db4oDatabase();

        containerControl.expectAndReturn(container.identity(), result);
        containerControl.replay();
        AssertJUnit.assertSame(result, template.identity());
    }

    @Test
    public void testIsActive() {
        boolean result = false;
        Object obj = new Object();

        containerControl.expectAndReturn(container.isActive(obj), result);
        containerControl.replay();
        AssertJUnit.assertFalse(template.isActive(obj));

    }

    public void testIsCached() {
        boolean result = false;
        long id = 12345l;

        containerControl.expectAndReturn(container.isCached(id), result);
        containerControl.replay();
        AssertJUnit.assertFalse(template.isCached(id));
    }

    @Test
    public void testIsClosed() {
        boolean result = false;

        containerControl.expectAndReturn(container.isClosed(), result);
        containerControl.replay();
        AssertJUnit.assertFalse(template.isClosed());
    }

    public void testIsStored() {
        boolean result = false;
        Object obj = new Object();

        containerControl.expectAndReturn(container.isStored(obj), result);
        containerControl.replay();
        AssertJUnit.assertFalse(template.isStored(obj));
    }

    @Test
    public void testKnownClasses() {
        ReflectClass[] result = new ReflectClass[]{};

        containerControl.expectAndReturn(container.knownClasses(), result);
        containerControl.replay();
        AssertJUnit.assertSame(result, template.knownClasses());
    }

    @Test
    public void testLock() {
        Object lock = new Object();

        containerControl.expectAndReturn(container.lock(), lock);
        containerControl.replay();
        AssertJUnit.assertSame(lock, template.lock());
    }

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

    @Test
    public void testPurge() {
        container.purge();
        containerControl.replay();
        template.purge();
    }

    @Test
    public void testPurgeObject() {
        Object obj = new Object();

        container.purge(obj);
        containerControl.replay();
        template.purge(obj);
    }

    @Test
    public void testReflector() {
        MockControl refCtrl = MockClassControl.createControl(GenericReflector.class);
        GenericReflector reflector = (GenericReflector) refCtrl.getMock();
        refCtrl.replay();

        containerControl.expectAndReturn(container.reflector(), reflector);
        containerControl.replay();
        AssertJUnit.assertSame(reflector, template.reflector());
    }

    @Test
    public void testRefresh() {
        Object obj = new Object();
        int depth = 1234;

        container.refresh(obj, depth);
        containerControl.replay();
        template.refresh(obj, depth);
    }

    @Test
    public void testReleaseSemaphore() {
        String name = "";

        container.releaseSemaphore(name);
        containerControl.replay();
        template.releaseSemaphore(name);
    }

    @Test
    public void testStoreObjectInt() {
        Object obj = new Object();
        int depth = 123;

        container.store(obj, depth);
        containerControl.replay();
        template.store(obj, depth);
    }

    @Test
    public void testSetSemaphore() {
        boolean result = false;
        String name = "";
        int wait = 123;

        containerControl.expectAndReturn(container.setSemaphore(name, wait), result);
        containerControl.replay();
        AssertJUnit.assertFalse(template.setSemaphore(name, wait));

    }

    @Test
    public void testStoredClass() {
        MockControl classCtrl = MockControl.createControl(StoredClass.class);
        StoredClass clazz = (StoredClass) classCtrl.getMock();

        Object obj = new Object();

        containerControl.expectAndReturn(container.storedClass(obj), clazz);
        containerControl.replay();
        AssertJUnit.assertSame(clazz, template.storedClass(obj));
    }

    @Test
    public void testStoredClasses() {
        StoredClass[] result = new StoredClass[]{};

        Object obj = new Object();

        containerControl.expectAndReturn(container.storedClasses(), result);
        containerControl.replay();
        AssertJUnit.assertSame(result, template.storedClasses());
    }

    @Test
    public void testVersion() {
        long result = 1234;

        containerControl.expectAndReturn(container.version(), result);
        containerControl.replay();
        AssertJUnit.assertEquals(result, template.version());
    }

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

    /*
    private Db4oOperations createTemplate() {
        containerControl.reset();

        Db4oTemplate tmpl = new Db4oTemplate(container);
        containerControl.replay();
        return tmpl;
    }
    */

}
