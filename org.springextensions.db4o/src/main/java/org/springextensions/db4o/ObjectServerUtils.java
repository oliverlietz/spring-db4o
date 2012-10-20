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

import java.io.IOException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.ext.ObjectNotStorableException;
import com.db4o.ext.OldFormatException;

/**
 * Utils class for ObjectServers. Handles exception translation at the moment.
 *
 * @author Costin Leau
 */
public class ObjectServerUtils {

    /**
     * db4o exception translator - it converts specific db4o unchecked/checked
     * exceptions into unchecked Spring DA exception.
     * <p/>
     * As there is no db4o specific base exception, the Db4oSystemException is
     * used as a marker inside the package for a user specific runtime exception
     * inside the callbacks for example.
     *
     * @param ex
     * @return
     */
    public static DataAccessException translateException(Exception ex) {
        if (ex instanceof DatabaseFileLockedException) {
            return new DataAccessResourceFailureException("database is already locked ", ex);
        }
        if (ex instanceof ObjectNotStorableException)
            return new InvalidDataAccessApiUsageException("object not storable ", ex);

        if (ex instanceof OldFormatException)
            return new DataAccessResourceFailureException("database is in old format", ex);

        if (ex instanceof IOException)
            return new DataAccessResourceFailureException("cannot do backup ", ex);

        // fallback
        return new Db4oSystemException(ex);
    }

    /**
     * utility method used for masking the password with '*'.
     *
     * @param string
     * @return
     */
    public static String maskString(String string) {
        StringBuffer buf = new StringBuffer(string.length());
        for (int i = 0; i < string.length(); i++) {
            buf.append('*');
        }
        return buf.toString();
    }

}
