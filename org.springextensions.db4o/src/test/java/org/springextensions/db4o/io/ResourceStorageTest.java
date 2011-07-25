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
package org.springextensions.db4o.io;

import com.db4o.io.Bin;
import com.db4o.io.BinConfiguration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * author: olli
 */
public class ResourceStorageTest {

    private BinConfiguration binConfiguration;

    private Resource resource;

    private ResourceLoader resourceLoader;

    private ResourceStorage resourceStorage;

    @BeforeClass
    public void setup() {
        String uri = "resource";
        binConfiguration = new BinConfiguration(uri, false, 0, false);

        resource = new ByteArrayResource(new byte[]{0, 1, 2, 3, 4, 5, 6, 7});

        resourceLoader = mock(ResourceLoader.class);
        when(resourceLoader.getResource(uri)).thenReturn(resource);

        resourceStorage = new ResourceStorage();
        resourceStorage.setResourceLoader(resourceLoader);
    }

    @Test
    public void testOpen() {
        Bin bin = resourceStorage.open(binConfiguration);
    }

}
