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

import java.util.Comparator;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oDatabase;
import com.db4o.ext.Db4oUUID;
import com.db4o.ext.ExtClient;
import com.db4o.ext.ObjectInfo;
import com.db4o.ext.StoredClass;
import com.db4o.ext.SystemInfo;
import com.db4o.io.Storage;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import com.db4o.query.QueryComparator;
import com.db4o.reflect.ReflectClass;
import com.db4o.reflect.generic.GenericReflector;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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

    @Test
    public void testSetObjectContainer() {
        template = new Db4oTemplate();
        template.setObjectContainer(container);
    }

    @Test
    public void testAfterPropertiesSet() {
        try {
            template.setObjectContainer(null);
            template.afterPropertiesSet();
            Assert.fail("expected illegal argument exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testExposeNativeContainer() {
        boolean exposeNativeContainer = true;
        template.setExposeNativeContainer(exposeNativeContainer);
        Assert.assertSame(exposeNativeContainer, template.isExposeNativeContainer());
    }

    @Test
    public void testExecuteDb4oCallback() {
        final Object result = new Object();

        Db4oCallback callback = new Db4oCallback() {
            public Object doInDb4o(ObjectContainer nativeContainer) throws RuntimeException {
                Assert.assertEquals(container, nativeContainer);
                return result;
            }
        };

        Db4oCallback proxiedCallback = new Db4oCallback() {
            public Object doInDb4o(ObjectContainer proxiedContainer) throws RuntimeException {
                Assert.assertFalse(container == proxiedContainer);
                Assert.assertFalse(container.hashCode() == proxiedContainer.hashCode());
                // if not proxied, mock control will throw an error
                Assert.assertFalse(proxiedContainer.close());
                return result;
            }
        };

        Assert.assertSame(result, template.execute(callback, true));
        Assert.assertSame(result, template.execute(proxiedCallback, false));
    }

    //
    // ObjectContainer interface methods
    //

    @Test
    public void testActivateWithDepth() {
        Object object = new Object();
        int depth = 10;
        template.activate(object, depth);
        verify(container).activate(object, depth);
    }

    @Test
    public void testDeactivateWithDepth() {
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
        verify(container).queryByExample(object);
    }

    @Test
    public void testQuery() {
        Query query = mock(Query.class);
        when(container.query()).thenReturn(query);
        Assert.assertSame(query, template.query());
        verify(container).query();
    }

    @Test
    public void queryClass() {
        Class clazz = Object.class;
        ObjectSet<Object> objectSet = mock(ObjectSet.class);
        when(container.query(clazz)).thenReturn(objectSet);
        Assert.assertSame(objectSet, template.query(clazz));
        verify(container).query(clazz);
    }

    @Test
    public void testQueryPredicate() {
        ObjectSet<Object> objectSet = mock(ObjectSet.class);
        Predicate<Object> predicate = mock(Predicate.class);
        when(container.query(predicate)).thenReturn(objectSet);
        Assert.assertSame(objectSet, template.query(predicate));
        verify(container).query(predicate);
    }

    @Test
    public void testQueryPredicateWithQueryComparator() {
        ObjectSet<Object> objectSet = mock(ObjectSet.class);
        Predicate<Object> predicate = mock(Predicate.class);
        QueryComparator<Object> comparator = mock(QueryComparator.class);
        when(container.query(predicate, comparator)).thenReturn(objectSet);
        Assert.assertSame(objectSet, template.query(predicate, comparator));
        verify(container).query(predicate, comparator);
    }

    @Test
    public void testQueryPredicateWithComparator() {
        ObjectSet<Object> objectSet = mock(ObjectSet.class);
        Predicate<Object> predicate = mock(Predicate.class);
        Comparator<Object> comparator = mock(Comparator.class);
        when(container.query(predicate, comparator)).thenReturn(objectSet);
        Assert.assertSame(objectSet, template.query(predicate, comparator));
        verify(container).query(predicate, comparator);
    }

    @Test
    public void testStore() {
        Object object = new Object();
        template.store(object);
        verify(container).store(object);
    }

    //
    // ExtObjectContainer interface methods
    //

    @Test
    public void testActivate() {
        Object object = new Object();
        template.activate(object);
        verify(container).activate(object);
    }

    @Test
    public void testDeactivate() {
        Object object = new Object();
        template.deactivate(object);
        verify(container).deactivate(object);
    }

    @Test
    public void testBackup() {
        String path = "";
        template.backup(path);
        verify(container).backup(path);
    }

    @Test
    public void testBackupWithStorage() {
        Storage targetStorage = mock(Storage.class);
        String path = "";
        template.backup(targetStorage, path);
        verify(container).backup(targetStorage, path);
    }

    @Test
    public void testBind() {
        Object object = new Object();
        long id = 1234l;
        template.bind(object, id);
        verify(container).bind(object, id);
    }

    @Test
    public void testDescend() {
        Object object = new Object();
        String[] path = new String[]{};
        Object result = new Object();
        when(container.descend(object, path)).thenReturn(result);
        Assert.assertSame(result, template.descend(object, path));
        verify(container).descend(object, path);
    }

    @Test
    public void testGetByID() {
        Object result = new Object();
        long id = 1234l;
        when(container.getByID(id)).thenReturn(result);
        Assert.assertSame(result, template.getByID(id));
        verify(container).getByID(id);
    }

    @Test
    public void testGetByUUID() {
        Object result = new Object();
        long id = 1234l;
        Db4oUUID uuid = new Db4oUUID(id, new byte[]{});
        when(container.getByUUID(uuid)).thenReturn(result);
        Assert.assertSame(result, template.getByUUID(uuid));
        verify(container).getByUUID(uuid);
    }

    @Test
    public void testGetID() {
        Object object = new Object();
        long id = 1234l;
        when(container.getID(object)).thenReturn(id);
        // TODO Assert.assertSame(id, template.getID(object)); // (Long)
        Assert.assertEquals(id, template.getID(object));
        verify(container).getID(object);
    }

    @Test
    public void testGetObjectInfo() {
        Object object = new Object();
        ObjectInfo objectInfo = mock(ObjectInfo.class);
        when(container.getObjectInfo(object)).thenReturn(objectInfo);
        Assert.assertSame(objectInfo, template.getObjectInfo(object));
        verify(container).getObjectInfo(object);
    }

    @Test
    public void testIdentity() {
        Db4oDatabase result = new Db4oDatabase();
        when(container.identity()).thenReturn(result);
        Assert.assertSame(result, template.identity());
        verify(container).identity();
    }

    @Test
    public void testIsActive() {
        boolean result = false;
        Object object = new Object();
        when(container.isActive(object)).thenReturn(result);
        Assert.assertFalse(template.isActive(object));
        verify(container).isActive(object);
    }

    @Test
    public void testIsCached() {
        boolean result = false;
        long id = 12345l;
        when(container.isCached(id)).thenReturn(result);
        Assert.assertFalse(template.isCached(id));
        verify(container).isCached(id);
    }

    @Test
    public void testIsClosed() {
        boolean result = false;
        when(container.isClosed()).thenReturn(result);
        Assert.assertFalse(template.isClosed());
        verify(container).isClosed();
    }

    @Test
    public void testIsStored() {
        boolean result = false;
        Object object = new Object();
        when(container.isStored(object)).thenReturn(result);
        Assert.assertFalse(template.isStored(object));
        verify(container).isStored(object);
    }

    @Test
    public void testKnownClasses() {
        ReflectClass[] result = new ReflectClass[]{};
        when(container.knownClasses()).thenReturn(result);
        Assert.assertSame(result, template.knownClasses());
        verify(container).knownClasses();
    }

    @Test
    public void testLock() {
        Object lock = new Object();
        when(container.lock()).thenReturn(lock);
        Assert.assertSame(lock, template.lock());
        verify(container).lock();
    }

    @Test
    public void testOpenSession() {
        ObjectContainer objectContainer = mock(ObjectContainer.class);
        when(container.openSession()).thenReturn(objectContainer);
        Assert.assertSame(objectContainer, template.openSession());
        verify(container).openSession();
    }

    @Test
    public void testPeekPersisted() {
        Object object = new Object();
        int depth = 123;
        boolean committed = false;
        Object result = new Object();
        when(container.peekPersisted(object, depth, committed)).thenReturn(result);
        Assert.assertSame(result, template.peekPersisted(object, depth, committed));
        verify(container).peekPersisted(object, depth, committed);
    }

    @Test
    public void testPurge() {
        template.purge();
        verify(container).purge();
    }

    @Test
    public void testPurgeObject() {
        Object object = new Object();
        template.purge(object);
        verify(container).purge(object);
    }

    @Test
    public void testReflector() {
        GenericReflector reflector = mock(GenericReflector.class);
        when(container.reflector()).thenReturn(reflector);
        Assert.assertSame(reflector, template.reflector());
        verify(container).reflector();
    }

    @Test
    public void testRefresh() {
        Object object = new Object();
        int depth = 1234;
        template.refresh(object, depth);
        verify(container).refresh(object, depth);
    }

    @Test
    public void testReleaseSemaphore() {
        String name = "";
        template.releaseSemaphore(name);
        verify(container).releaseSemaphore(name);
    }

    @Test
    public void testStoreWithDepth() {
        Object object = new Object();
        int depth = 123;
        template.store(object, depth);
        verify(container).store(object, depth);
    }

    @Test
    public void testSetSemaphore() {
        boolean result = false;
        String name = "";
        int wait = 123;
        when(container.setSemaphore(name, wait)).thenReturn(result);
        Assert.assertFalse(template.setSemaphore(name, wait));
        verify(container).setSemaphore(name, wait);
    }

    @Test
    public void testStoredClass() {
        Object object = new Object();
        StoredClass storedClass = mock(StoredClass.class);
        when(container.storedClass(object)).thenReturn(storedClass);
        Assert.assertSame(storedClass, template.storedClass(object));
        verify(container).storedClass(object);
    }

    @Test
    public void testStoredClasses() {
        StoredClass[] result = new StoredClass[]{};
        when(container.storedClasses()).thenReturn(result);
        Assert.assertSame(result, template.storedClasses());
        verify(container).storedClasses();
    }

    @Test
    public void testSystemInfo() {
        SystemInfo result = mock(SystemInfo.class);
        when(container.systemInfo()).thenReturn(result);
        Assert.assertSame(result, template.systemInfo());
        verify(container).systemInfo();
    }

    @Test
    public void testVersion() {
        long result = 1234;
        when(container.version()).thenReturn(result);
        Assert.assertEquals(result, template.version());
        verify(container).version();
    }

    //
    // ExtClient interface methods
    //

    @Test
    public void testIsAlive() {
        boolean result = true;
        when(container.isAlive()).thenReturn(result);
        Assert.assertTrue(template.isAlive());
        verify(container).isAlive();
    }

    //
    // Proxy
    //

    @Test
    public void testClose() {
        when(container.close()).thenReturn(true);
        Db4oCallback callback = new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return container.close();
            }
        };
        Assert.assertEquals(true, template.execute(callback, true));
        verify(container).close();
    }

    @Test
    public void testCloseSuppressingInvocationHandlerClose() {
        when(container.close()).thenReturn(true);
        Db4oCallback callback = new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return container.close();
            }
        };
        Assert.assertEquals(false, template.execute(callback, false));
        verify(container, never()).close();
    }

    //
    //
    //

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
