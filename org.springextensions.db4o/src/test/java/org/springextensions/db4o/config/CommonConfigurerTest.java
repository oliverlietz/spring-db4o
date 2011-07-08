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

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.db4o.config.Alias;
import com.db4o.config.CommonConfiguration;
import com.db4o.config.ConfigurationItem;
import com.db4o.config.NameProvider;
import com.db4o.config.encoding.StringEncoding;
import com.db4o.reflect.Reflector;
import com.db4o.typehandlers.TypeHandler4;
import com.db4o.typehandlers.TypeHandlerPredicate;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * author: olli
 */
public class CommonConfigurerTest {

    private CommonConfiguration commonConfiguration;

    private CommonConfigurer commonConfigurer;

    @BeforeMethod
    public void setup() {
        commonConfiguration = mock(CommonConfiguration.class);
        commonConfigurer = new CommonConfigurer(commonConfiguration);
    }

    @Test
    public void testGetConfiguration() {
        Assert.assertEquals(commonConfigurer.getConfiguration(), commonConfiguration);
    }

    @Test
    public void testGetDiagnostic() {
        Assert.assertEquals(commonConfigurer.getDiagnostic().diagnosticConfiguration, commonConfiguration.diagnostic());
    }

    @Test
    public void tesGetQueries() {
        Assert.assertEquals(commonConfigurer.getQuery().queryConfiguration, commonConfiguration.queries());
    }

    @Test
    public void testGetEnvironment() {
        Assert.assertEquals(commonConfigurer.getEnvironment().environmentConfiguration, commonConfiguration.environment());
    }

    @Test
    public void testSetAlias() {
        Alias alias = mock(Alias.class);
        commonConfigurer.setAlias(alias);
        verify(commonConfiguration).addAlias(alias);
    }

    @Test
    public void testSetAliases() {
        List<Alias> aliases = Arrays.asList(mock(Alias.class), mock(Alias.class), mock(Alias.class), mock(Alias.class));
        commonConfigurer.setAlias(aliases);
        verify(commonConfiguration, times(aliases.size())).addAlias(any(Alias.class));
    }

    @Test
    public void testSetActivationDepth() {
        int activationDepth = 2;
        commonConfigurer.setActivationDepth(activationDepth);
        verify(commonConfiguration).activationDepth(activationDepth);
    }

    @Test
    public void testSetConfigurationItem() {
        ConfigurationItem configurationItem = mock(ConfigurationItem.class);
        commonConfigurer.setConfigurationItem(configurationItem);
        verify(commonConfiguration).add(configurationItem);
    }

    @Test
    public void testSetConfigurationItems() {
        List<ConfigurationItem> configurationItems = Arrays.asList(mock(ConfigurationItem.class), mock(ConfigurationItem.class), mock(ConfigurationItem.class));
        commonConfigurer.setConfigurationItem(configurationItems);
        verify(commonConfiguration, times(configurationItems.size())).add(any(ConfigurationItem.class));
    }

    @Test
    public void testSetAllowVersionUpdates() {
        boolean allowVersionUpdates = true;
        commonConfigurer.setAllowVersionUpdates(allowVersionUpdates);
        verify(commonConfiguration).allowVersionUpdates(allowVersionUpdates);
    }

    @Test
    public void testSetAutomaticShutDown() {
        boolean automaticShutDown = true;
        commonConfigurer.setAutomaticShutDown(automaticShutDown);
        verify(commonConfiguration).automaticShutDown(automaticShutDown);
    }

    @Test
    public void testSetBTreeNodeSize() {
        int bTreeNodeSize = 256;
        commonConfigurer.setBTreeNodeSize(bTreeNodeSize);
        verify(commonConfiguration).bTreeNodeSize(bTreeNodeSize);
    }

    @Test
    public void testSetCallbacks() {
        boolean callbacks = true;
        commonConfigurer.setCallbacks(callbacks);
        verify(commonConfiguration).callbacks(callbacks);
    }

    @Test
    public void testSetCallConstructors() {
        boolean callConstructors = true;
        commonConfigurer.setCallConstructors(callConstructors);
        verify(commonConfiguration).callConstructors(callConstructors);
    }

    @Test
    public void testSetDetectSchemaChanges() {
        boolean detectSchemaChanges = true;
        commonConfigurer.setDetectSchemaChanges(detectSchemaChanges);
        verify(commonConfiguration).detectSchemaChanges(detectSchemaChanges);
    }

    @Test
    public void testSetExceptionsOnNotStorable() {
        boolean exceptionsOnNotStorable = true;
        commonConfigurer.setExceptionsOnNotStorable(exceptionsOnNotStorable);
        verify(commonConfiguration).exceptionsOnNotStorable(exceptionsOnNotStorable);
    }

    @Test
    public void testSetInternStrings() {
        boolean internStrings = true;
        commonConfigurer.setInternStrings(internStrings);
        verify(commonConfiguration).internStrings(internStrings);
    }

    @Test
    public void testSetMarkTransient() {
        String markTransient = "";
        commonConfigurer.setMarkTransient(markTransient);
        verify(commonConfiguration).markTransient(markTransient);
    }

    @Test
    public void testSetMarkTransients() {
        List<String> markTransient = Arrays.asList("", "", "", "", "");
        commonConfigurer.setMarkTransient(markTransient);
        verify(commonConfiguration, times(markTransient.size())).markTransient(anyString());
    }

    @Test
    public void testSetMaxStackDepth() {
        int maxStackDepth = 20;
        commonConfigurer.setMaxStackDepth(maxStackDepth);
        verify(commonConfiguration).maxStackDepth(maxStackDepth);
    }

    @Test
    public void testSetMessageLevel() {
        int messageLevel = 1;
        commonConfigurer.setMessageLevel(messageLevel);
        verify(commonConfiguration).messageLevel(messageLevel);
    }

    @Test
    public void testSetOptimizeNativeQueries() {
        boolean optimizeNativeQueries = true;
        commonConfigurer.setOptimizeNativeQueries(optimizeNativeQueries);
        verify(commonConfiguration).optimizeNativeQueries(optimizeNativeQueries);
    }

    @Test
    public void testSetReflectWith() {
        Reflector reflector = mock(Reflector.class);
        commonConfigurer.setReflectWith(reflector);
        verify(commonConfiguration).reflectWith(reflector);
    }

    @Test
    public void testSetOutStream() {
        PrintStream outStream = mock(PrintStream.class);
        commonConfigurer.setOutStream(outStream);
        verify(commonConfiguration).outStream(outStream);
    }

    @Test
    public void testSetStringEncoding() {
        StringEncoding stringEncoding = mock(StringEncoding.class);
        commonConfigurer.setStringEncoding(stringEncoding);
        verify(commonConfiguration).stringEncoding(stringEncoding);
    }

    @Test
    public void testSetTestConstructors() {
        boolean testConstructors = true;
        commonConfigurer.setTestConstructors(testConstructors);
        verify(commonConfiguration).testConstructors(testConstructors);
    }

    @Test
    public void testSetUpdateDepth() {
        int updateDepth = 2;
        commonConfigurer.setUpdateDepth(updateDepth);
        verify(commonConfiguration).updateDepth(updateDepth);
    }

    @Test
    public void testSetWeakReferences() {
        boolean weakReferences = true;
        commonConfigurer.setWeakReferences(weakReferences);
        verify(commonConfiguration).weakReferences(weakReferences);
    }

    @Test
    public void testSetWeakReferenceCollectionInterval() {
        int weakReferenceCollectionInterval = 10000;
        commonConfigurer.setWeakReferenceCollectionInterval(weakReferenceCollectionInterval);
        verify(commonConfiguration).weakReferenceCollectionInterval(weakReferenceCollectionInterval);
    }

    @Test
    public void testSetTypeHandlers() {
        TypeHandlerPredicate typeHandlerPredicate = mock(TypeHandlerPredicate.class);
        TypeHandler4 typeHandler4 = mock(TypeHandler4.class);
        Map<TypeHandlerPredicate, TypeHandler4> typeHandlers = new HashMap<TypeHandlerPredicate, TypeHandler4>();
        typeHandlers.put(typeHandlerPredicate, typeHandler4);
        commonConfigurer.setTypeHandlers(typeHandlers);
        verify(commonConfiguration).registerTypeHandler(typeHandlerPredicate, typeHandler4);
    }

    @Test
    public void testSetNameProvider() {
        NameProvider nameProvider = mock(NameProvider.class);
        commonConfigurer.setNameProvider(nameProvider);
        verify(commonConfiguration).nameProvider(nameProvider);
    }

}
