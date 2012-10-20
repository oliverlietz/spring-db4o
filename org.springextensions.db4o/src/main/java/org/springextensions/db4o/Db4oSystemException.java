/*
 * Copyright 2005-2009 the original author or authors.
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
package org.springextensions.db4o;

import org.springframework.dao.UncategorizedDataAccessException;

/**
 * Runtime (unchecked) exception used for wrapping the db4o exceptions
 * which do not match the Spring DAO exception hierarchy.
 *
 * @author Costin Leau
 */
public class Db4oSystemException extends UncategorizedDataAccessException {

    public Db4oSystemException(String message, Throwable ex) {
        super(message, ex);
    }

    public Db4oSystemException(Throwable ex) {
        super("db4o access exception", ex);
    }

}
