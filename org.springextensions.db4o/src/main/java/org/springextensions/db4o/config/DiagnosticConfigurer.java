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

import java.util.List;

import com.db4o.diagnostic.DiagnosticConfiguration;
import com.db4o.diagnostic.DiagnosticListener;

/**
 * @author olli
 * @see <a href="http://developer.db4o.com/Documentation/Reference/db4o-8.0/java/reference/Content/configuration/common/diagnostics.htm">Diagnostics</a>
 */
public class DiagnosticConfigurer {

    protected DiagnosticConfiguration diagnosticConfiguration;

    public DiagnosticConfigurer(DiagnosticConfiguration diagnosticConfiguration) {
        this.diagnosticConfiguration = diagnosticConfiguration;
    }

    public void setListener(DiagnosticListener diagnosticListener) {
        diagnosticConfiguration.addListener(diagnosticListener);
    }

    public void setListeners(List<DiagnosticListener> diagnosticListeners) {
        for (DiagnosticListener diagnosticListener : diagnosticListeners) {
            diagnosticConfiguration.addListener(diagnosticListener);
        }
    }

}
