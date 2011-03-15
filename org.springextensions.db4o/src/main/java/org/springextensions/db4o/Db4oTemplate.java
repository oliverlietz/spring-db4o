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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Comparator;

import org.springframework.dao.DataAccessException;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oDatabase;
import com.db4o.ext.Db4oException;
import com.db4o.ext.Db4oUUID;
import com.db4o.ext.ExtClient;
import com.db4o.ext.ExtObjectContainer;
import com.db4o.ext.ObjectInfo;
import com.db4o.ext.StoredClass;
import com.db4o.io.Storage;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import com.db4o.query.QueryComparator;
import com.db4o.reflect.ReflectClass;
import com.db4o.reflect.generic.GenericReflector;

/**
 * @author Costin Leau
 * @author olli
 */
public class Db4oTemplate extends Db4oAccessor implements Db4oOperations {

    private boolean exposeNativeContainer = false;

    public Db4oTemplate() {
    }

    public Db4oTemplate(ObjectContainer container) {
        setObjectContainer(container);
        afterPropertiesSet();
    }

    /**
     * @param exposeNativeContainer The exposeNativeContainer to set.
     */
    public void setExposeNativeContainer(boolean exposeNativeContainer) {
        this.exposeNativeContainer = exposeNativeContainer;
    }

    /**
     * @return Returns the exposeNativeContainer.
     */
    public boolean isExposeNativeContainer() {
        return exposeNativeContainer;
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#execute(org.springextensions.db4o.Db4oCallback)
     */
    public Object execute(Db4oCallback callback) throws DataAccessException {
        return execute(callback, isExposeNativeContainer());
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#execute(org.springextensions.db4o.Db4oCallback, boolean)
     */
    public Object execute(Db4oCallback callback, boolean exposeNativeContainer) throws DataAccessException {
        ObjectContainer cont = getObjectContainer();
        try {
            ObjectContainer container = (exposeNativeContainer ? cont : createContainerProxy(cont));
            return callback.doInDb4o(container);
        } catch (Db4oException ex) {
            throw convertDb4oAccessException(ex);
        } catch (RuntimeException ex) {
            RuntimeException convEx = convertDb4oAccessException(ex);
            // it's user specific
            if (convEx instanceof Db4oSystemException)
                throw ex;
            // it's a converted exception
            throw convEx;
        }
    }

    //
    // ObjectContainer interface methods
    //

    /**
     * @see org.springextensions.db4o.Db4oOperations#activate(java.lang.Object, int)
     */
    public void activate(final Object object, final int depth) {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                container.activate(object, depth);
                return null;
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#deactivate(java.lang.Object, int)
     */
    public void deactivate(final Object object, final int depth) {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                container.deactivate(object, depth);
                return null;
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#delete(java.lang.Object)
     */
    public void delete(final Object object) {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                container.delete(object);
                return null;
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#queryByExample(java.lang.Object)
     */
    public <T> ObjectSet<T> queryByExample(final Object template) {
        return (ObjectSet<T>) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return container.queryByExample(template);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#query()
     */
    public Query query() {
        return (Query) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return container.query();
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#query(java.lang.Class)
     */
    public <TargetType> ObjectSet<TargetType> query(final Class<TargetType> clazz) {
        return (ObjectSet<TargetType>) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return container.query(clazz);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#query(com.db4o.query.Predicate)
     */
    public <TargetType> ObjectSet<TargetType> query(final Predicate<TargetType> predicate) {
        return (ObjectSet<TargetType>) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return container.query(predicate);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#query(com.db4o.query.Predicate, com.db4o.query.QueryComparator)
     */
    public <TargetType> ObjectSet<TargetType> query(final Predicate<TargetType> predicate, final QueryComparator<TargetType> comparator) {
        return (ObjectSet<TargetType>) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return container.query(predicate, comparator);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#query(com.db4o.query.Predicate, java.util.Comparator)
     */
    public <TargetType> ObjectSet<TargetType> query(final Predicate<TargetType> predicate, final Comparator<TargetType> comparator) {
        return (ObjectSet<TargetType>) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return container.query(predicate, comparator);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#store(java.lang.Object)
     */
    public void store(final Object object) {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                container.store(object);
                return null;
            }
        }, true);
    }

    //
    // ExtObjectContainer interface
    //

    /**
     * @see org.springextensions.db4o.Db4oOperations#activate(java.lang.Object)
     */
    public void activate(final Object object) {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                ((ExtObjectContainer) container).activate(object);
                return null;
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#deactivate(java.lang.Object)
     */
    public void deactivate(final Object object) {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                ((ExtObjectContainer) container).deactivate(object);
                return null;
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#backup(java.lang.String)
     */
    public void backup(final String path) {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                // special hack
                // the IOException appears out of the blue inside the API which
                // has only RuntimeExceptions
                //try {
                ((ExtObjectContainer) container).backup(path);
                return null;
                //}
                // TODO: apparently the latest version of db4o no longer throws IOException
                // from the backup() method
                //catch (IOException e) {
                //	throw convertDb4oAccessException(e);
                //}
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#backup(com.db4o.io.Storage, java.lang.String)
     */
    public void backup(final Storage targetStorage, final String path) {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                ((ExtObjectContainer) container).backup(targetStorage, path);
                return null;
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#bind(java.lang.Object, long)
     */
    public void bind(final Object object, final long id) {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                ((ExtObjectContainer) container).bind(object, id);
                return null;
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#descend(java.lang.Object, java.lang.String[])
     */
    public Object descend(final Object object, final String[] path) {
        return execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).descend(object, path);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#getByID(long)
     */
    public Object getByID(final long id) {
        return execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).getByID(id);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#getByUUID(com.db4o.ext.Db4oUUID)
     */
    public Object getByUUID(final Db4oUUID uuid) {
        return execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).getByUUID(uuid);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#getID(java.lang.Object)
     */
    public long getID(final Object object) {
        return (Long) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).getID(object);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#getObjectInfo(java.lang.Object)
     */
    public ObjectInfo getObjectInfo(final Object object) {
        return (ObjectInfo) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).getObjectInfo(object);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#identity()
     */
    public Db4oDatabase identity() {
        return (Db4oDatabase) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).identity();
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#isActive(java.lang.Object)
     */
    public boolean isActive(final Object object) {
        return (Boolean) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).isActive(object);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#isCached(long)
     */
    public boolean isCached(final long ID) {
        return (Boolean) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).isCached(ID);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#isClosed()
     */
    public boolean isClosed() {
        return (Boolean) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).isClosed();
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#isStored(java.lang.Object)
     */
    public boolean isStored(final Object object) {
        return (Boolean) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).isStored(object);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#knownClasses()
     */
    public ReflectClass[] knownClasses() {
        return (ReflectClass[]) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).knownClasses();
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#lock()
     */
    public Object lock() {
        return execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).lock();
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#openSession()
     */
    public ObjectContainer openSession() {
        return (ObjectContainer) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).openSession();
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#peekPersisted(java.lang.Object, int, boolean)
     */
    public <T> T peekPersisted(final T object, final int depth, final boolean committed) {
        return (T) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).peekPersisted(object, depth, committed);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#purge()
     */
    public void purge() {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                ((ExtObjectContainer) container).purge();
                return null;
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#purge(java.lang.Object)
     */
    public void purge(final Object object) {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                ((ExtObjectContainer) container).purge(object);
                return null;
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#reflector()
     */
    public GenericReflector reflector() {
        return (GenericReflector) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).reflector();
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#refresh(java.lang.Object, int)
     */
    public void refresh(final java.lang.Object object, final int depth) {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                ((ExtObjectContainer) container).refresh(object, depth);
                return null;
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#releaseSemaphore(java.lang.String)
     */
    public void releaseSemaphore(final String name) {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                ((ExtObjectContainer) container).releaseSemaphore(name);
                return null;
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#store(java.lang.Object, int)
     */
    public void store(final Object object, final int depth) {
        execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                ((ExtObjectContainer) container).store(object, depth);
                return null;
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#setSemaphore(java.lang.String, int)
     */
    public boolean setSemaphore(final String name, final int waitForAvailability) {
        return (Boolean) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).setSemaphore(name, waitForAvailability);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#storedClass(java.lang.Object)
     */
    public StoredClass storedClass(final Object clazz) {
        return (StoredClass) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).storedClass(clazz);
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#storedClasses()
     */
    public StoredClass[] storedClasses() {
        return (StoredClass[]) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).storedClasses();
            }
        }, true);
    }

    /**
     * @see org.springextensions.db4o.Db4oOperations#version()
     */
    public long version() {
        return (Long) execute(new Db4oCallback() {
            public Object doInDb4o(ObjectContainer container) throws RuntimeException {
                return ((ExtObjectContainer) container).version();
            }
        }, true);
    }

    //
    // Ext Client interface
    //

    /**
     * Create a close-suppressing proxy for the given object container.
     *
     * @param container the Db4o ObjectContainer to create a proxy for
     * @return the ObjectContainer proxy
     * @see com.db4o.ObjectContainer#close()
     */
    protected ObjectContainer createContainerProxy(ObjectContainer container) {
        Class intrf = ExtObjectContainer.class;

        // the documentation states that the container always implements
        // the following interface but we do this here just to make sure

        if (container instanceof ExtClient)
            intrf = ExtClient.class;

        return (ObjectContainer) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{intrf}, new CloseSuppressingInvocationHandler(container));
    }

    /**
     * Invocation handler that suppresses close calls on Object Container.
     *
     * @see com.db4o.ObjectContainer#close()
     */
    private class CloseSuppressingInvocationHandler implements InvocationHandler {

        private final ObjectContainer target;

        public CloseSuppressingInvocationHandler(ObjectContainer target) {
            this.target = target;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Invocation on ObjectContainer interface (or vendor-specific
            // extension) coming in...

            if (method.getName().equals("equals")) {
                // Only consider equal when proxies are identical.
                return (proxy == args[0] ? Boolean.TRUE : Boolean.FALSE);
            } else if (method.getName().equals("hashCode")) {
                // Use hashCode of session proxy.
                return hashCode();
            } else if (method.getName().equals("close")) {
                // Handle close method: suppress, not valid.
                // tell the truth when returning the value.
                return Boolean.FALSE;
            }

            // invoke method on target container
            try {
                return method.invoke(this.target, args);
            } catch (InvocationTargetException ex) {
                throw ex.getTargetException();
            }
        }
    }

}
