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

import com.db4o.config.QueryConfiguration;
import com.db4o.config.QueryEvaluationMode;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * author: olli
 */
public class QueryConfigurerTest {

    private QueryConfiguration queryConfiguration;

    private QueryConfigurer queryConfigurer;

    @BeforeMethod
    public void setup() {
        queryConfiguration = mock(QueryConfiguration.class);
        queryConfigurer = new QueryConfigurer(queryConfiguration);
    }

    @Test
    public void testSetEvaluationModeImmediate() {
        queryConfigurer.setEvaluationMode(QueryConfigurer.EvaluationMode.Immediate);
        verify(queryConfiguration).evaluationMode(QueryEvaluationMode.IMMEDIATE);
        verify(queryConfiguration, never()).evaluationMode(QueryEvaluationMode.LAZY);
        verify(queryConfiguration, never()).evaluationMode(QueryEvaluationMode.SNAPSHOT);
    }

    @Test
    public void testSetEvaluationModeLazy() {
        queryConfigurer.setEvaluationMode(QueryConfigurer.EvaluationMode.Lazy);
        verify(queryConfiguration).evaluationMode(QueryEvaluationMode.LAZY);
        verify(queryConfiguration, never()).evaluationMode(QueryEvaluationMode.IMMEDIATE);
        verify(queryConfiguration, never()).evaluationMode(QueryEvaluationMode.SNAPSHOT);
    }

    @Test
    public void testSetEvaluationModeSnapshot() {
        queryConfigurer.setEvaluationMode(QueryConfigurer.EvaluationMode.Snapshot);
        verify(queryConfiguration).evaluationMode(QueryEvaluationMode.SNAPSHOT);
        verify(queryConfiguration, never()).evaluationMode(QueryEvaluationMode.IMMEDIATE);
        verify(queryConfiguration, never()).evaluationMode(QueryEvaluationMode.LAZY);
    }

}
