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

import org.springframework.dao.DataAccessException;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oDatabase;
import com.db4o.ext.Db4oUUID;
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
public interface Db4oOperations {

    public Object execute(Db4oCallback callback) throws DataAccessException;

    public Object execute(Db4oCallback callback, boolean exposeNativeContainer) throws DataAccessException;

    //
    // ObjectContainer interface methods
    //
    public void activate(final Object obj, final int depth);

    public void deactivate(final Object obj, final int depth);

    public void delete(final Object obj);

    public <T> ObjectSet<T> queryByExample(final Object template);

    public Query query();

    public <TargetType> ObjectSet<TargetType> query(final Class<TargetType> clazz);

    public <TargetType> ObjectSet<TargetType> query(final Predicate<TargetType> predicate);

    public <TargetType> ObjectSet<TargetType> query(final Predicate<TargetType> predicate, final QueryComparator<TargetType> comparator);

    public <TargetType> ObjectSet<TargetType> query(final Predicate<TargetType> predicate, final Comparator<TargetType> comparator);

    public void store(final Object obj);

    //
    // ExtObjectContainer interface methods
    //
    public void activate(final Object obj);

    public void deactivate(final Object obj);

    public void backup(final String path);

    public void backup(final Storage targetStorage, final String path);

    public void bind(final Object obj, final long id);

    // public Configuration configure();

    public Object descend(final Object obj, final String[] path);

    public <T> T getByID(final long ID);

    public <T> T getByUUID(final Db4oUUID uuid);

    public long getID(final Object obj);

    public ObjectInfo getObjectInfo(final Object obj);

    public Db4oDatabase identity();

    public boolean isActive(final Object obj);

    public boolean isCached(final long ID);

    public boolean isClosed();

    public boolean isStored(final Object obj);

    public ReflectClass[] knownClasses();

    public Object lock();

    public ObjectContainer openSession();

    public <T> T peekPersisted(final T object, final int depth, final boolean committed);

    public void purge();

    public void purge(final Object obj);

    public GenericReflector reflector();

    public void refresh(final Object obj, final int depth);

    public void releaseSemaphore(final String name);

    public void store(final Object obj, final int depth);

    public boolean setSemaphore(final String name, final int waitForAvailability);

    public StoredClass storedClass(final Object clazz);

    public StoredClass[] storedClasses();

    // public SystemInfo systemInfo();

    public long version();

}
