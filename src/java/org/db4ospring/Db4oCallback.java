/**
 * Created on Nov 5, 2005
 *
 * $Id: Db4oCallback.java,v 1.1 2005-12-01 14:51:20 costin Exp $
 * $Revision: 1.1 $
 */
package org.db4ospring;

import com.db4o.ObjectContainer;

/**
 * Callback interface for db4o code. To be used with Db4oTemplate's execute method, 
 * assumably often as anonymous classes within a method implementation. The typical 
 * implementation will call ObjectContainer methods  to perform some operations on 
 * the db4o object container.
 * @author Costin Leau
 *
 */
public interface Db4oCallback {

    /**
     * Called by {@link Db4oTemplate#execute} within an active object container
     * {@link com.db4o.ObjectContainer}. 
     *
     * Allows for returning a result object created within the
     * callback, i.e. a domain object or a collection of domain
     * objects. 
     */
	public Object doInDb4o(ObjectContainer container) throws RuntimeException;
}
