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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;

import com.db4o.ObjectContainer;

/**
 * Base class for Db4oTemplate and Db4oInterceptor, defining common properties
 * like objectContainer.
 * <p/>
 * <p/>
 * Not intended to be used directly. See Db4oTemplate and Db4oInterceptor.
 *
 * @author Costin Leau
 * @author olli
 */
public abstract class Db4oAccessor implements InitializingBean {

    private ObjectContainer objectContainer;

    public void setObjectContainer(ObjectContainer objectContainer) {
        this.objectContainer = objectContainer;
    }

    public ObjectContainer getObjectContainer() {
        return objectContainer;
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() {
        if (objectContainer == null)
            throw new IllegalArgumentException("objectContainer is required");
    }

    /**
     * Convert the given Db4o Exception to an appropriate exception from
     * the <code>org.springframework.dao</code> hierarchy.
     * <p/>
     * May be overridden in subclasses.
     *
     * @param ex RuntimeException that occured
     * @return the corresponding DataAccessException instance
     */
    public DataAccessException convertDb4oAccessException(Exception ex) {
        return ObjectServerUtils.translateException(ex);
    }

}
