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

import com.db4o.config.Alias;
import com.db4o.config.CommonConfiguration;
import com.db4o.config.ConfigurationItem;
import com.db4o.config.EnvironmentConfiguration;
import com.db4o.config.NameProvider;
import com.db4o.config.QueryConfiguration;
import com.db4o.config.encoding.StringEncoding;
import com.db4o.diagnostic.DiagnosticConfiguration;
import com.db4o.reflect.Reflector;
import com.db4o.typehandlers.TypeHandler4;
import com.db4o.typehandlers.TypeHandlerPredicate;

/**
 * @author olli
 */
public class CommonConfigurer {

    protected CommonConfiguration commonConfiguration;

    public CommonConfigurer(CommonConfiguration commonConfiguration) {
        this.commonConfiguration = commonConfiguration;
    }

    public void addAlias(Alias alias) {
        // TODO
        throw new UnsupportedOperationException();
    }

    /**
     * @param activationDepth
     * @see com.db4o.config.CommonConfiguration#activationDepth(int)
     */
    public void setActivationDepth(int activationDepth) {
        commonConfiguration.activationDepth(activationDepth);
    }

    public void add(ConfigurationItem configurationItem) {
        // TODO
        throw new UnsupportedOperationException();
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
     * @return
     * @see com.db4o.config.CommonConfiguration#diagnostic()
     */
    public DiagnosticConfiguration diagnostic() {
        return commonConfiguration.diagnostic();
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
     * @return
     * @see com.db4o.config.CommonConfiguration#queries()
     */
    public QueryConfiguration queries() {
        return commonConfiguration.queries();
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
     * @return
     * @see com.db4o.config.CommonConfiguration#environment()
     */
    public EnvironmentConfiguration environment() {
        return commonConfiguration.environment();
    }

    /**
     * @param nameProvider
     * @see com.db4o.config.CommonConfiguration#nameProvider(com.db4o.config.NameProvider)
     */
    public void setNameProvider(NameProvider nameProvider) {
        commonConfiguration.nameProvider(nameProvider);
    }

}
