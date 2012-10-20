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

import com.db4o.config.CommonConfiguration;
import com.db4o.config.ObjectClass;
import com.db4o.config.ObjectTranslator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * author: olli
 */
public class ObjectClassConfigurerTest {

    private CommonConfiguration commonConfiguration;

    private CommonConfigurer commonConfigurer;

    private ObjectClass objectClass;

    private ObjectClassConfigurer objectClassConfigurer;

    @BeforeMethod
    public void setup() {
        objectClass = mock(ObjectClass.class);

        commonConfiguration = mock(CommonConfiguration.class);
        when(commonConfiguration.objectClass(Object.class)).thenReturn(objectClass);


        commonConfigurer = mock(CommonConfigurer.class);
        when(commonConfigurer.getConfiguration()).thenReturn(commonConfiguration);

        objectClassConfigurer = new ObjectClassConfigurer(commonConfigurer, Object.class);
    }

    @Test
    public void testGetObjectClass() {
        Assert.assertSame(objectClass, objectClassConfigurer.getObjectClass());
    }

    @Test
    public void testSetCallConstructor() {
        boolean callConstructor = true;
        objectClassConfigurer.setCallConstructor(callConstructor);
        verify(objectClass).callConstructor(callConstructor);
    }

    @Test
    public void testSetCascadeOnActivate() {
        boolean cascadeOnActivate = true;
        objectClassConfigurer.setCascadeOnActivate(cascadeOnActivate);
        verify(objectClass).cascadeOnActivate(cascadeOnActivate);
    }

    @Test
    public void testSetCascadeOnDelete() {
        boolean cascadeOnDelete = true;
        objectClassConfigurer.setCascadeOnDelete(cascadeOnDelete);
        verify(objectClass).cascadeOnDelete(cascadeOnDelete);
    }

    @Test
    public void testSetCascadeOnUpdate() {
        boolean cascadeOnUpdate = true;
        objectClassConfigurer.setCascadeOnUpdate(cascadeOnUpdate);
        verify(objectClass).cascadeOnUpdate(cascadeOnUpdate);
    }

    @Test
    public void testSetGenerateUUIDs() {
        boolean generateUUIDs = true;
        objectClassConfigurer.setGenerateUUIDs(generateUUIDs);
        verify(objectClass).generateUUIDs(generateUUIDs);
    }

    @Test
    public void testSetIndexed() {
        boolean indexed = true;
        objectClassConfigurer.setIndexed(indexed);
        verify(objectClass).indexed(indexed);
    }

    @Test
    public void testSetMaximumActivationDepth() {
        int maximumActivationDepth = 123;
        objectClassConfigurer.setMaximumActivationDepth(maximumActivationDepth);
        verify(objectClass).maximumActivationDepth(maximumActivationDepth);
    }

    @Test
    public void testSetMinimumActivationDepth() {
        int minimumActivationDepth = 12;
        objectClassConfigurer.setMinimumActivationDepth(minimumActivationDepth);
        verify(objectClass).minimumActivationDepth(minimumActivationDepth);
    }

    @Test
    public void testSetPersistStaticFieldValuesToTrue() {
        boolean persistStaticFieldValues = true;
        objectClassConfigurer.setPersistStaticFieldValues(persistStaticFieldValues);
        verify(objectClass).persistStaticFieldValues();
    }

    @Test
    public void testSetPersistStaticFieldValuesToFalse() {
        boolean persistStaticFieldValues = false;
        objectClassConfigurer.setPersistStaticFieldValues(persistStaticFieldValues);
        verify(objectClass, never()).persistStaticFieldValues();
    }

    @Test
    public void testSetRename() {
        String rename = "";
        objectClassConfigurer.setRename(rename);
        verify(objectClass).rename(rename);
    }

    @Test
    public void testSetStoreTransientFields() {
        boolean storeTransientFields = true;
        objectClassConfigurer.setStoreTransientFields(storeTransientFields);
        verify(objectClass).storeTransientFields(storeTransientFields);
    }

    @Test
    public void testSetTranslate() {
        ObjectTranslator objectTranslator = mock(ObjectTranslator.class);
        objectClassConfigurer.setTranslate(objectTranslator);
        verify(objectClass).translate(objectTranslator);
    }

    @Test
    public void testSetUpdateDepth() {
        int updateDepth = 1234;
        objectClassConfigurer.setUpdateDepth(updateDepth);
        verify(objectClass).updateDepth(updateDepth);
    }

}
