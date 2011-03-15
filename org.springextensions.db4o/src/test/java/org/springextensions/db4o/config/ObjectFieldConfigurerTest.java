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
import com.db4o.config.ObjectField;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * author: olli
 */
public class ObjectFieldConfigurerTest {

    private ObjectClass objectClass;

    private ObjectField objectField;

    private ObjectFieldConfigurer objectFieldConfigurer;

    @BeforeMethod
    public void setup() throws Exception {
        objectField = mock(ObjectField.class);

        objectClass = mock(ObjectClass.class);
        when(objectClass.objectField("")).thenReturn(objectField);

        objectFieldConfigurer = new ObjectFieldConfigurer(objectClass, "");
    }

    @Test
    public void testGetObjectField() throws Exception {
        Assert.assertSame(objectField, objectFieldConfigurer.getObjectField());
    }

    @Test
    public void testSetCascadeOnActivate() throws Exception {
        boolean cascadeOnActivate = true;
        objectFieldConfigurer.setCascadeOnActivate(cascadeOnActivate);
        verify(objectField).cascadeOnActivate(cascadeOnActivate);
    }

    @Test
    public void testSetCascadeOnDelete() throws Exception {
        boolean cascadeOnDelete = true;
        objectFieldConfigurer.setCascadeOnDelete(cascadeOnDelete);
        verify(objectField).cascadeOnDelete(cascadeOnDelete);
    }

    @Test
    public void testSetCascadeOnUpdate() throws Exception {
        boolean cascadeOnUpdate = true;
        objectFieldConfigurer.setCascadeOnUpdate(cascadeOnUpdate);
        verify(objectField).cascadeOnUpdate(cascadeOnUpdate);
    }

    @Test
    public void testSetIndexed() throws Exception {
        boolean indexed = true;
        objectFieldConfigurer.setIndexed(indexed);
        verify(objectField).indexed(indexed);
    }

    @Test
    public void testSetRename() throws Exception {
        String rename = "";
        objectFieldConfigurer.setRename(rename);
        verify(objectField).rename(rename);
    }

}
