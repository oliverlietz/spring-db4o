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
package org.springextensions.db4o.example;

import com.db4o.cs.config.ClientConfiguration;
import com.db4o.cs.config.ClientConfigurationItem;
import com.db4o.ext.ExtClient;

/**
 * author: olli
 */
public class NoopClientConfigurationItem implements ClientConfigurationItem {

    @Override
    public void prepare(ClientConfiguration clientConfiguration) {
        //
    }

    @Override
    public void apply(ExtClient extClient) {
        //
    }

}
