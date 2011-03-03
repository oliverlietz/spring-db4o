/*
 * Copyright 2010-2011 the original author or authors.
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
package org.springextensions.db4o.config;

import com.db4o.config.ObjectClass;
import com.db4o.config.ObjectTranslator;

/**
 * author: olli
 */
public class ObjectClassConfigurer {

    private final ObjectClass objectClass;

    public ObjectClassConfigurer(CommonConfigurer commonConfigurer, Class clazz) {
        objectClass = commonConfigurer.commonConfiguration.objectClass(clazz);
    }

    public ObjectClass getObjectClass() {
        return objectClass;
    }

    /**
     * @see com.db4o.config.ObjectClass#callConstructor(boolean)
     */
    public void setCallConstructor(boolean callConstructor) {
        objectClass.callConstructor(callConstructor);
    }

    /**
     * @see com.db4o.config.ObjectClass#cascadeOnActivate(boolean)
     */
    public void setCascadeOnActivate(boolean cascadeOnActivate) {
        objectClass.cascadeOnActivate(cascadeOnActivate);
    }

    /**
     * @see com.db4o.config.ObjectClass#cascadeOnDelete(boolean)
     */
    public void setCascadeOnDelete(boolean cascadeOnDelete) {
        objectClass.cascadeOnDelete(cascadeOnDelete);
    }

    /**
     * @see com.db4o.config.ObjectClass#cascadeOnUpdate(boolean)
     */
    public void setCascadeOnUpdate(boolean cascadeOnUpdate) {
        objectClass.cascadeOnUpdate(cascadeOnUpdate);
    }

    /**
     * @see com.db4o.config.ObjectClass#enableReplication(boolean)
     */
    public void setEnableReplication(boolean enableReplication) {
        objectClass.enableReplication(enableReplication);
    }

    /**
     * @see com.db4o.config.ObjectClass#generateUUIDs(boolean)
     */
    public void setGenerateUUIDs(boolean generateUUIDs) {
        objectClass.generateUUIDs(generateUUIDs);
    }

    /**
     * @see com.db4o.config.ObjectClass#generateVersionNumbers(boolean)
     */
    public void setGenerateVersionNumbers(boolean generateVersionNumbers) {
        objectClass.generateVersionNumbers(generateVersionNumbers);
    }

    /**
     * @see com.db4o.config.ObjectClass#indexed(boolean)
     */
    public void setIndexed(boolean indexed) {
        objectClass.indexed(indexed);
    }

    /**
     * @see com.db4o.config.ObjectClass#maximumActivationDepth(int)
     */
    public void setMaximumActivationDepth(int maximumActivationDepth) {
        objectClass.maximumActivationDepth(maximumActivationDepth);
    }

    /**
     * @see com.db4o.config.ObjectClass#minimumActivationDepth(int)
     */
    public void setMinimumActivationDepth(int minimumActivationDepth) {
        objectClass.minimumActivationDepth(minimumActivationDepth);
    }

    /**
     * @see com.db4o.config.ObjectClass#persistStaticFieldValues()
     */
    public void setPersistStaticFieldValues(boolean persistStaticFieldValues) {
        if (persistStaticFieldValues) {
            objectClass.persistStaticFieldValues();
        }
    }

    /**
     * @see com.db4o.config.ObjectClass#rename(String)
     */
    public void setRename(String rename) {
        objectClass.rename(rename);
    }

    /**
     * @see com.db4o.config.ObjectClass#storeTransientFields(boolean)
     */
    public void setStoreTransientFields(boolean storeTransientFields) {
        objectClass.storeTransientFields(storeTransientFields);
    }

    /**
     * @see com.db4o.config.ObjectClass#translate(com.db4o.config.ObjectTranslator)
     */
    public void setTranslate(ObjectTranslator objectTranslator) {
        objectClass.translate(objectTranslator);
    }

    /**
     * @see com.db4o.config.ObjectClass#updateDepth(int)
     */
    public void setUpdateDepth(int updateDepth) {
        objectClass.updateDepth(updateDepth);
    }

}
