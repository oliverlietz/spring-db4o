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

import java.util.List;

import com.db4o.config.EnvironmentConfiguration;

/**
 * @author olli
 */
public class EnvironmentConfigurer {

    protected EnvironmentConfiguration environmentConfiguration;

    public EnvironmentConfigurer(EnvironmentConfiguration environmentConfiguration) {
        this.environmentConfiguration = environmentConfiguration;
    }

    /**
     * @param service
     * @see com.db4o.config.EnvironmentConfiguration#add(Object)
     */
    public void setService(Object service) {
        environmentConfiguration.add(service);
    }

    /**
     * @param services
     * @see com.db4o.config.EnvironmentConfiguration#add(Object)
     */
    public void setServices(List<Object> services) {
        for (Object service : services) {
            environmentConfiguration.add(service);
        }
    }

}
