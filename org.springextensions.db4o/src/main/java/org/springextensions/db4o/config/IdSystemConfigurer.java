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

import com.db4o.config.IdSystemConfiguration;
import com.db4o.config.IdSystemFactory;

/**
 * @author olli
 */
public class IdSystemConfigurer {

    protected IdSystemConfiguration idSystemConfiguration;

    public enum System {
        InMemory,
        PointerBased,
        SingleBTree,
        StackedBTree
    }

    public IdSystemConfigurer(IdSystemConfiguration idSystemConfiguration) {
        this.idSystemConfiguration = idSystemConfiguration;
    }

    /**
     * @param factory
     * @see com.db4o.config.IdSystemConfiguration#useCustomSystem(com.db4o.config.IdSystemFactory)
     */
    public void setCustomSystem(IdSystemFactory factory) {
        idSystemConfiguration.useCustomSystem(factory);
    }

    /**
     * @param system
     * @see com.db4o.config.IdSystemConfiguration#useInMemorySystem()
     * @see com.db4o.config.IdSystemConfiguration#usePointerBasedSystem()
     * @see com.db4o.config.IdSystemConfiguration#useSingleBTreeSystem()
     * @see com.db4o.config.IdSystemConfiguration#useStackedBTreeSystem()
     */
    public void setSystem(System system) {
        switch (system) {
            case InMemory:
                idSystemConfiguration.useInMemorySystem();
                break;
            case PointerBased:
                idSystemConfiguration.usePointerBasedSystem();
                break;
            case SingleBTree:
                idSystemConfiguration.useSingleBTreeSystem();
                break;
            case StackedBTree:
                idSystemConfiguration.useStackedBTreeSystem();
                break;
        }
    }

}
