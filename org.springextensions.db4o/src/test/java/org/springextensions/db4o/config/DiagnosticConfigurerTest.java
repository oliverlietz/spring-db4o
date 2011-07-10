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

import com.db4o.diagnostic.DiagnosticConfiguration;
import com.db4o.diagnostic.DiagnosticListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * author: olli
 */
public class DiagnosticConfigurerTest {

    private DiagnosticConfiguration diagnosticConfiguration;

    private DiagnosticConfigurer diagnosticConfigurer;

    @BeforeMethod
    public void setup() {
        diagnosticConfiguration = mock(DiagnosticConfiguration.class);
        diagnosticConfigurer = new DiagnosticConfigurer(diagnosticConfiguration);
    }

    @Test
    public void testSetListener() {
        DiagnosticListener diagnosticListener = mock(DiagnosticListener.class);
        diagnosticConfigurer.setListener(diagnosticListener);
        verify(diagnosticConfiguration).addListener(diagnosticListener);
    }

    @Test
    public void testSetListeners() {
        List<DiagnosticListener> diagnosticListeners = Arrays.asList(mock(DiagnosticListener.class), mock(DiagnosticListener.class), mock(DiagnosticListener.class));
        diagnosticConfigurer.setListeners(diagnosticListeners);
        verify(diagnosticConfiguration, times(diagnosticListeners.size())).addListener(any(DiagnosticListener.class));
    }

}
