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

import java.util.Arrays;
import java.util.List;

import com.db4o.config.EnvironmentConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * author: olli
 */
public class EnvironmentConfigurerTest {

    private EnvironmentConfiguration environmentConfiguration;

    private EnvironmentConfigurer environmentConfigurer;

    @BeforeMethod
    public void setup() {
        environmentConfiguration = mock(EnvironmentConfiguration.class);
        environmentConfigurer = new EnvironmentConfigurer(environmentConfiguration);
    }

    @Test
    public void testSetService() {
        Object service = new Object();
        environmentConfigurer.setService(service);
        verify(environmentConfiguration).add(service);
    }

    @Test
    public void testSetServices() {
        List<Object> services = Arrays.asList(new Object(), new Object(), new Object(), new Object());
        environmentConfigurer.setServices(services);
        verify(environmentConfiguration, times(services.size())).add(any());
    }

}
