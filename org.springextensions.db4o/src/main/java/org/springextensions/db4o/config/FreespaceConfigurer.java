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

import com.db4o.config.FreespaceConfiguration;
import com.db4o.config.FreespaceFiller;

/**
 * @author olli
 * @see <a href="http://developer.db4o.com/Documentation/Reference/db4o-8.0/java/reference/Content/configuration/file/freespace_configuration.htm">Freespace Configuration</a>
 */
public class FreespaceConfigurer {

    protected FreespaceConfiguration freespaceConfiguration;

    public enum System {
        BTree,
        Ram
    }

    public FreespaceConfigurer(FreespaceConfiguration freespaceConfiguration) {
        this.freespaceConfiguration = freespaceConfiguration;
    }

    /**
     * @param byteCount
     * @see com.db4o.config.FreespaceConfiguration#discardSmallerThan(int)
     */
    public void setDiscardSmallerThan(int byteCount) {
        freespaceConfiguration.discardSmallerThan(byteCount);
    }

    /**
     * @param freespaceFiller
     * @see com.db4o.config.FreespaceConfiguration#freespaceFiller(com.db4o.config.FreespaceFiller)
     */
    public void setFreespaceFiller(FreespaceFiller freespaceFiller) {
        freespaceConfiguration.freespaceFiller(freespaceFiller);
    }

    /**
     * @param system the freespace system to use, one of <code>BTree</code> or <code>Ram</code>
     * @see com.db4o.config.FreespaceConfiguration#useBTreeSystem()
     * @see com.db4o.config.FreespaceConfiguration#useRamSystem()
     */
    public void setSystem(System system) {
        switch (system) {
            case BTree:
                freespaceConfiguration.useBTreeSystem();
                break;
            case Ram:
                freespaceConfiguration.useRamSystem();
                break;
        }
    }

}
