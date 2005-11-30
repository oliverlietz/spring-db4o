/**
 * Created on Nov 5, 2005
 *
 * $Id: Db4oCallback.java,v 1.1 2005-11-30 12:30:28 costin Exp $
 * $Revision: 1.1 $
 */
package com.db4o.springframework;

import com.db4o.ObjectContainer;

/**
 * @author Costin Leau
 *
 */
public interface Db4oCallback {

	public Object doInDb4o(ObjectContainer container) throws RuntimeException;
}
