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

import com.db4o.config.FreespaceConfiguration;
import com.db4o.config.FreespaceFiller;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * author: olli
 */
public class FreespaceConfigurerTest {

    FreespaceConfiguration freespaceConfiguration;

    FreespaceConfigurer freespaceConfigurer;

    @BeforeMethod
    public void setup() {
        freespaceConfiguration = mock(FreespaceConfiguration.class);
        freespaceConfigurer = new FreespaceConfigurer(freespaceConfiguration);
    }

    @Test
    public void testSetDiscardSmallerThan() {
        int discardSmallerThan = 256;
        freespaceConfigurer.setDiscardSmallerThan(discardSmallerThan);
        verify(freespaceConfiguration).discardSmallerThan(discardSmallerThan);
    }

    @Test
    public void testSetFreespaceFiller() {
        FreespaceFiller freespaceFiller = mock(FreespaceFiller.class);
        freespaceConfigurer.setFreespaceFiller(freespaceFiller);
        verify(freespaceConfiguration).freespaceFiller(freespaceFiller);
    }

    @Test
    public void testSetSystemBTree() {
        freespaceConfigurer.setSystem(FreespaceConfigurer.System.BTree);
        verify(freespaceConfiguration).useBTreeSystem();
        verify(freespaceConfiguration, never()).useRamSystem();
    }

    @Test
    public void testSetSystemRam() {
        freespaceConfigurer.setSystem(FreespaceConfigurer.System.Ram);
        verify(freespaceConfiguration).useRamSystem();
        verify(freespaceConfiguration, never()).useBTreeSystem();
    }

}
