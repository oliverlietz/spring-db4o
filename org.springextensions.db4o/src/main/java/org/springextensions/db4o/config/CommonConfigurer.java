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

import java.io.PrintStream;
import java.util.List;

import com.db4o.config.Alias;
import com.db4o.config.CommonConfiguration;
import com.db4o.config.ConfigurationItem;
import com.db4o.config.NameProvider;
import com.db4o.config.encoding.StringEncoding;
import com.db4o.reflect.Reflector;
import com.db4o.typehandlers.TypeHandler4;
import com.db4o.typehandlers.TypeHandlerPredicate;

/**
 * @author olli
 * @see <a href="http://developer.db4o.com/Documentation/Reference/db4o-8.0/java/reference/Content/configuration/common_configuration.htm">Common Configuration</a>
 */
public class CommonConfigurer {

    protected CommonConfiguration commonConfiguration;

    protected QueryConfigurer queryConfigurer;

    protected DiagnosticConfigurer diagnosticConfigurer;

    protected EnvironmentConfigurer environmentConfigurer;

    public CommonConfigurer(CommonConfiguration commonConfiguration) {
        this.commonConfiguration = commonConfiguration;
    }

    public CommonConfiguration getConfiguration() {
        return commonConfiguration;
    }

    public QueryConfigurer getQuery() {
        if (queryConfigurer == null) {
            queryConfigurer = new QueryConfigurer(commonConfiguration.queries());
        }
        return queryConfigurer;
    }

    public DiagnosticConfigurer getDiagnostic() {
        if (diagnosticConfigurer == null) {
            diagnosticConfigurer = new DiagnosticConfigurer(commonConfiguration.diagnostic());
        }
        return diagnosticConfigurer;
    }

    public EnvironmentConfigurer getEnvironment() {
        if (environmentConfigurer == null) {
            environmentConfigurer = new EnvironmentConfigurer(commonConfiguration.environment());
        }
        return environmentConfigurer;
    }

    /**
     * @param alias
     * @see com.db4o.config.CommonConfiguration#addAlias(com.db4o.config.Alias)
     */
    public void setAlias(Alias alias) {
        commonConfiguration.addAlias(alias);
    }

    /**
     * @param aliases
     * @see com.db4o.config.CommonConfiguration#addAlias(com.db4o.config.Alias)
     */
    public void setAlias(List<Alias> aliases) {
        for (Alias alias : aliases) {
            commonConfiguration.addAlias(alias);
        }
    }

    /**
     * @param activationDepth
     * @see com.db4o.config.CommonConfiguration#activationDepth(int)
     */
    public void setActivationDepth(int activationDepth) {
        commonConfiguration.activationDepth(activationDepth);
    }

    /**
     * @param configurationItem
     * @see com.db4o.config.CommonConfiguration#add(com.db4o.config.ConfigurationItem)
     */
    public void setConfigurationItem(ConfigurationItem configurationItem) {
        commonConfiguration.add(configurationItem);
    }

    /**
     * @param configurationItems
     * @see com.db4o.config.CommonConfiguration#add(com.db4o.config.ConfigurationItem)
     */
    public void setConfigurationItem(List<ConfigurationItem> configurationItems) {
        for (ConfigurationItem configurationItem : configurationItems) {
            commonConfiguration.add(configurationItem);
        }
    }

    /**
     * @param allowVersionUpdates
     * @see com.db4o.config.CommonConfiguration#allowVersionUpdates(boolean)
     */
    public void setAllowVersionUpdates(boolean allowVersionUpdates) {
        commonConfiguration.allowVersionUpdates(allowVersionUpdates);
    }

    /**
     * @param automaticShutDown
     * @see com.db4o.config.CommonConfiguration#automaticShutDown(boolean)
     */
    public void setAutomaticShutDown(boolean automaticShutDown) {
        commonConfiguration.automaticShutDown(automaticShutDown);
    }

    /**
     * @param bTreeNodeSize
     * @see com.db4o.config.CommonConfiguration#bTreeNodeSize(int)
     */
    public void setBTreeNodeSize(int bTreeNodeSize) {
        commonConfiguration.bTreeNodeSize(bTreeNodeSize);
    }

    /**
     * @param callbacks
     * @see com.db4o.config.CommonConfiguration#callbacks(boolean)
     */
    public void setCallbacks(boolean callbacks) {
        commonConfiguration.callbacks(callbacks);
    }

    /**
     * @param callConstructors
     * @see com.db4o.config.CommonConfiguration#callConstructors(boolean)
     */
    public void setCallConstructors(boolean callConstructors) {
        commonConfiguration.callConstructors(callConstructors);
    }

    /**
     * @param detectSchemaChanges
     * @see com.db4o.config.CommonConfiguration#detectSchemaChanges(boolean)
     */
    public void setDetectSchemaChanges(boolean detectSchemaChanges) {
        commonConfiguration.detectSchemaChanges(detectSchemaChanges);
    }

    /**
     * @param exceptionsOnNotStorable
     * @see com.db4o.config.CommonConfiguration#exceptionsOnNotStorable(boolean)
     */
    public void setExceptionsOnNotStorable(boolean exceptionsOnNotStorable) {
        commonConfiguration.exceptionsOnNotStorable(exceptionsOnNotStorable);
    }

    /**
     * @param internStrings
     * @see com.db4o.config.CommonConfiguration#internStrings(boolean)
     */
    public void setInternStrings(boolean internStrings) {
        commonConfiguration.internStrings(internStrings);
    }

    /**
     * @param markTransient
     * @see com.db4o.config.CommonConfiguration#markTransient(String)
     */
    public void setMarkTransient(String markTransient) {
        commonConfiguration.markTransient(markTransient);
    }

    /**
     * @param markTransients
     * @see com.db4o.config.CommonConfiguration#markTransient(String)
     */
    public void setMarkTransient(List<String> markTransients) {
        for (String markTransient : markTransients) {
            commonConfiguration.markTransient(markTransient);
        }
    }

    /**
     * @param maxStackDepth
     * @see com.db4o.config.CommonConfiguration#maxStackDepth(int)
     */
    public void setMaxStackDepth(int maxStackDepth) {
        commonConfiguration.maxStackDepth(maxStackDepth);
    }

    /**
     * @param messageLevel
     * @see com.db4o.config.CommonConfiguration#messageLevel(int)
     */
    public void setMessageLevel(int messageLevel) {
        commonConfiguration.messageLevel(messageLevel);
    }

    /**
     * @param optimizeNativeQueries
     * @see com.db4o.config.CommonConfiguration#optimizeNativeQueries(boolean)
     */
    public void setOptimizeNativeQueries(boolean optimizeNativeQueries) {
        commonConfiguration.optimizeNativeQueries(optimizeNativeQueries);
    }

    /**
     * @param reflector
     * @see com.db4o.config.CommonConfiguration#reflectWith(com.db4o.reflect.Reflector)
     */
    public void setReflectWith(Reflector reflector) {
        commonConfiguration.reflectWith(reflector);
    }

    /**
     * @param printStream
     * @see com.db4o.config.CommonConfiguration#outStream(java.io.PrintStream)
     */
    public void setOutStream(PrintStream printStream) {
        commonConfiguration.outStream(printStream);
    }

    /**
     * @param stringEncoding
     * @see com.db4o.config.CommonConfiguration#stringEncoding(com.db4o.config.encoding.StringEncoding)
     */
    public void setStringEncoding(StringEncoding stringEncoding) {
        commonConfiguration.stringEncoding(stringEncoding);
    }

    /**
     * @param testConstructors
     * @see com.db4o.config.CommonConfiguration#testConstructors(boolean)
     */
    public void setTestConstructors(boolean testConstructors) {
        commonConfiguration.testConstructors(testConstructors);
    }

    /**
     * @param updateDepth
     * @see com.db4o.config.CommonConfiguration#updateDepth(int)
     */
    public void setUpdateDepth(int updateDepth) {
        commonConfiguration.updateDepth(updateDepth);
    }

    /**
     * @param weakReferences
     * @see com.db4o.config.CommonConfiguration#weakReferences(boolean)
     */
    public void setWeakReferences(boolean weakReferences) {
        commonConfiguration.weakReferences(weakReferences);
    }

    /**
     * @param weakReferenceCollectionInterval
     *
     * @see com.db4o.config.CommonConfiguration#weakReferenceCollectionInterval(int)
     */
    public void setWeakReferenceCollectionInterval(int weakReferenceCollectionInterval) {
        commonConfiguration.weakReferenceCollectionInterval(weakReferenceCollectionInterval);
    }

    public void registerTypeHandler(TypeHandlerPredicate typeHandlerPredicate, TypeHandler4 typeHandler4) {
        // TODO
        throw new UnsupportedOperationException();
    }

    /**
     * @param nameProvider
     * @see com.db4o.config.CommonConfiguration#nameProvider(com.db4o.config.NameProvider)
     */
    public void setNameProvider(NameProvider nameProvider) {
        commonConfiguration.nameProvider(nameProvider);
    }

}
