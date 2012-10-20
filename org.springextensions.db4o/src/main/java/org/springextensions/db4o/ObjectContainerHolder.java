/*
 * Copyright 2005-2010 the original author or authors.
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

import com.db4o.ObjectContainer;
import org.springframework.transaction.support.ResourceHolderSupport;

/**
 * db4o ObjectContainer holder support
 *
 * @author Costin Leau
 */
public class ObjectContainerHolder extends ResourceHolderSupport {

    private final ObjectContainer objectContainer;

    public ObjectContainerHolder(ObjectContainer container) {
        objectContainer = container;
    }

    public ObjectContainer getObjectContainer() {
        return objectContainer;
    }

}
