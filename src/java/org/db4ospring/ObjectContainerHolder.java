/**
 * Created on Nov 12, 2005
 *
 * $Id: ObjectContainerHolder.java,v 1.1 2005-12-01 14:51:21 costin Exp $
 * $Revision: 1.1 $
 */
package org.db4ospring;

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
