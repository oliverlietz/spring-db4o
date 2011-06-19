/*
 * Copyright 2011 the original author or authors.
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

import com.db4o.config.IdSystemConfiguration;
import com.db4o.config.IdSystemFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * author: olli
 */
public class IdSystemConfigurerTest {

    private IdSystemConfiguration idSystemConfiguration;

    private IdSystemConfigurer idSystemConfigurer;

    @BeforeMethod
    public void setup() {
        idSystemConfiguration = mock(IdSystemConfiguration.class);
        idSystemConfigurer = new IdSystemConfigurer(idSystemConfiguration);
    }

    @Test
    public void testSetCustomSystem() {
        IdSystemFactory idSystemFactory = mock(IdSystemFactory.class);
        idSystemConfigurer.setCustomSystem(idSystemFactory);
        verify(idSystemConfiguration).useCustomSystem(idSystemFactory);
    }

    @Test
    public void testSetSystemWithSystemInMemory() {
        idSystemConfigurer.setSystem(IdSystemConfigurer.System.InMemory);
        verify(idSystemConfiguration).useInMemorySystem();
        verify(idSystemConfiguration, never()).usePointerBasedSystem();
        verify(idSystemConfiguration, never()).useSingleBTreeSystem();
        verify(idSystemConfiguration, never()).useStackedBTreeSystem();
    }

    @Test
    public void testSetSystemWithSystemPointerBased() {
        idSystemConfigurer.setSystem(IdSystemConfigurer.System.PointerBased);
        verify(idSystemConfiguration).usePointerBasedSystem();
        verify(idSystemConfiguration, never()).useInMemorySystem();
        verify(idSystemConfiguration, never()).useSingleBTreeSystem();
        verify(idSystemConfiguration, never()).useStackedBTreeSystem();
    }

    @Test
    public void testSetSystemWithSystemSingleBTree() {
        idSystemConfigurer.setSystem(IdSystemConfigurer.System.SingleBTree);
        verify(idSystemConfiguration).useSingleBTreeSystem();
        verify(idSystemConfiguration, never()).useInMemorySystem();
        verify(idSystemConfiguration, never()).usePointerBasedSystem();
        verify(idSystemConfiguration, never()).useStackedBTreeSystem();
    }

    @Test
    public void testSetSystemWithSystemStackedBTree() {
        idSystemConfigurer.setSystem(IdSystemConfigurer.System.StackedBTree);
        verify(idSystemConfiguration).useStackedBTreeSystem();
        verify(idSystemConfiguration, never()).useInMemorySystem();
        verify(idSystemConfiguration, never()).usePointerBasedSystem();
        verify(idSystemConfiguration, never()).useSingleBTreeSystem();
    }

}
