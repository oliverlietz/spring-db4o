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

import com.db4o.config.QueryConfiguration;
import com.db4o.config.QueryEvaluationMode;

/**
 * @author olli
 * @see <a href="http://developer.db4o.com/Documentation/Reference/db4o-8.0/java/reference/Content/configuration/common/query_modes.htm">Query Modes</a>
 */
public class QueryConfigurer {

    protected QueryConfiguration queryConfiguration;

    public enum EvaluationMode {
        Immediate,
        Lazy,
        Snapshot
    }

    public QueryConfigurer(QueryConfiguration queryConfiguration) {
        this.queryConfiguration = queryConfiguration;
    }

    public void setEvaluationMode(EvaluationMode evaluationMode) {
        switch (evaluationMode) {
            case Immediate:
                queryConfiguration.evaluationMode(QueryEvaluationMode.IMMEDIATE);
                break;
            case Lazy:
                queryConfiguration.evaluationMode(QueryEvaluationMode.LAZY);
                break;
            case Snapshot:
                queryConfiguration.evaluationMode(QueryEvaluationMode.SNAPSHOT);
                break;
        }
    }

}
