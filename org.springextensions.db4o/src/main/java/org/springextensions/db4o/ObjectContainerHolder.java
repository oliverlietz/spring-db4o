/**
 * Created on Nov 12, 2005
 *
 * $Id$
 * $Revision$
 */
package org.springextensions.db4o;

import org.springframework.transaction.support.ResourceHolderSupport;

import com.db4o.ObjectContainer;

/**
 * Db4o objectContainer holder support.
 * 
 * @author Costin Leau
 *
 */
public class ObjectContainerHolder extends ResourceHolderSupport {
	private final ObjectContainer objectContainer;

	/**
	 * 
	 */
	public ObjectContainerHolder(ObjectContainer container) {
		objectContainer = container;

	}

	/**
	 * @return Returns the container.
	 */
	public ObjectContainer getObjectContainer() {
		return objectContainer;
	}

}
