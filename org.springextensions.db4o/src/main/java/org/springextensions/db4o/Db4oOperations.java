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

    Object execute(Db4oCallback callback) throws DataAccessException;

    Object execute(Db4oCallback callback, boolean exposeNativeContainer) throws DataAccessException;

    //
    // ObjectContainer interface methods
    //

    void activate(final Object object, final int depth);

    void deactivate(final Object object, final int depth);

    void delete(final Object object);

    <T> ObjectSet<T> queryByExample(final Object template);

    Query query();

    <TargetType> ObjectSet<TargetType> query(final Class<TargetType> clazz);

    <TargetType> ObjectSet<TargetType> query(final Predicate<TargetType> predicate);

    <TargetType> ObjectSet<TargetType> query(final Predicate<TargetType> predicate, final QueryComparator<TargetType> comparator);

    <TargetType> ObjectSet<TargetType> query(final Predicate<TargetType> predicate, final Comparator<TargetType> comparator);

    void store(final Object object);

    //
    // ExtObjectContainer interface methods
    //

    void activate(final Object object);

    void deactivate(final Object object);

    void backup(final String path);

    void backup(final Storage targetStorage, final String path);

    void bind(final Object object, final long id);

    // Configuration configure();

    Object descend(final Object object, final String[] path);

    <T> T getByID(final long id);

    <T> T getByUUID(final Db4oUUID uuid);

    long getID(final Object object);

    ObjectInfo getObjectInfo(final Object object);

    Db4oDatabase identity();

    boolean isActive(final Object object);

    boolean isCached(final long id);

    boolean isClosed();

    boolean isStored(final Object object);

    ReflectClass[] knownClasses();

    Object lock();

    ObjectContainer openSession();

    <T> T peekPersisted(final T object, final int depth, final boolean committed);

    void purge();

    void purge(final Object object);

    GenericReflector reflector();

    void refresh(final Object object, final int depth);

    void releaseSemaphore(final String name);

    void store(final Object object, final int depth);

    boolean setSemaphore(final String name, final int waitForAvailability);

    StoredClass storedClass(final Object clazz);

    StoredClass[] storedClasses();

    // SystemInfo systemInfo();

    long version();

}
