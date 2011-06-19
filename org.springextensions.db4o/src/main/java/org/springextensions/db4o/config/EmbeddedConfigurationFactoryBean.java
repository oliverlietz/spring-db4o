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

import com.db4o.Db4oEmbedded;
import com.db4o.config.EmbeddedConfiguration;

/**
 * @author olli
 */
public class EmbeddedConfigurationFactoryBean { // implements FactoryBean<EmbeddedConfiguration> { https://jira.springframework.org/browse/OSGI-808

    private EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();

    private CommonConfigurer commonConfigurer;

    private FileConfigurer fileConfigurer;

    private IdSystemConfigurer idSystemConfigurer;

    public EmbeddedConfigurationFactoryBean() {
    }

    public EmbeddedConfiguration getConfiguration() throws Exception {
        return configuration;
    }

    /**
     * @return
     * @throws Exception
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public EmbeddedConfiguration getObject() throws Exception {
        return configuration;
    }

    /**
     * @return
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<EmbeddedConfiguration> getObjectType() {
        return EmbeddedConfiguration.class;
    }

    /**
     * @return
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * @return
     * @see com.db4o.config.EmbeddedConfiguration#common()
     */
    public CommonConfigurer getCommon() {
        if (commonConfigurer == null) {
            commonConfigurer = new CommonConfigurer(configuration.common());
        }
        return commonConfigurer;
    }

    /**
     * @return
     * @see com.db4o.config.EmbeddedConfiguration#file()
     */
    public FileConfigurer getFile() {
        if (fileConfigurer == null) {
            fileConfigurer = new FileConfigurer(configuration.file());
        }
        return fileConfigurer;
    }

    /**
     * @return
     * @see com.db4o.config.EmbeddedConfiguration#idSystem()
     */
    public IdSystemConfigurer getIdSystem() {
        if (idSystemConfigurer == null) {
            idSystemConfigurer = new IdSystemConfigurer(configuration.idSystem());
        }
        return idSystemConfigurer;
    }

    /* TODO
    public void addConfigurationItem(EmbeddedConfigurationItem embeddedConfigurationItem) {
        configuration.addConfigurationItem(embeddedConfigurationItem);
    }
    */

}
