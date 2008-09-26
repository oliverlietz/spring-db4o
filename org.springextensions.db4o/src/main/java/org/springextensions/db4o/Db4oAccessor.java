/**
 * Created on Nov 5, 2005
 *
 * $Id$
 * $Revision$
 */
package org.springextensions.db4o;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;

import com.db4o.ObjectContainer;

/**
 * 
 * Base class for Db4oTemplate and Db4oInterceptor, defining common properties
 * like objectContainer.
 * 
 * <p>
 * Not intended to be used directly. See Db4oTemplate and Db4oInterceptor.
 * 
 * @author Costin Leau
 *
 */
public abstract class Db4oAccessor implements InitializingBean {

	protected final Log logger = LogFactory.getLog(getClass());
	private ObjectContainer objectContainer;

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		if (objectContainer == null)
			throw new IllegalArgumentException("objectContainer is required");
	}
	
	/**
	 * Convert the given Db4o Exception to an appropriate exception from
	 * the <code>org.springframework.dao</code> hierarchy.
	 * <p>
	 * May be overridden in subclasses.
	 * 
	 * @param ex
	 *            RuntimeException that occured
	 * @return the corresponding DataAccessException instance
	 */
	public DataAccessException convertDb4oAccessException(Exception ex) {
		return ObjectServerUtils.translateException(ex);
	}

	/**
	 * @return Returns the objectContainer.
	 */
	public ObjectContainer getObjectContainer() {
		return objectContainer;
	}

	/**
	 * @param objectContainer The objectContainer to set.
	 */
	public void setObjectContainer(ObjectContainer objectContainer) {
		this.objectContainer = objectContainer;
	}
}
