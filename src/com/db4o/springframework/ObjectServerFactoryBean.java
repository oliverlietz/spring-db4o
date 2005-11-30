/**
 * Created on Nov 5, 2005
 *
 * $Id: ObjectServerFactoryBean.java,v 1.1 2005-11-30 12:30:27 costin Exp $
 * $Revision: 1.1 $
 */
package com.db4o.springframework;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import com.db4o.Db4o;
import com.db4o.ObjectServer;

/**
 * FactoryBean for creating ObjectServers. This class adds support for configuring user access through
 * the userAccess property which takes a Properties object with key the user name and value the password.
 * 
 * 
 * @author Costin Leau
 *
 */
public class ObjectServerFactoryBean implements InitializingBean, DisposableBean, FactoryBean {

	private static final Log log = LogFactory.getLog(ObjectServerFactoryBean.class);

	private Properties userAccess;
	private ObjectServer server;
	private Resource databaseFile;
	private int port;

	/**
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	public Object getObject() throws Exception {
		return server;
	}

	/**
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	public Class getObjectType() {
		return (server != null ? server.getClass() : ObjectServer.class);
	}

	/**
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	public boolean isSingleton() {
		return true;
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		if (databaseFile == null)
			throw new IllegalArgumentException("databaseFile is required");
		if (port < 0)
			throw new IllegalArgumentException("port must be greater then or equal to 0");

		System.out.println("Database file is " + databaseFile.getFile().getAbsolutePath());
		server = Db4o.openServer(databaseFile.getFile().getAbsolutePath(), port);
		log.info(Db4o.version());
		log.info("opened db4o server @" + System.identityHashCode(server));

		if (userAccess != null) {
			boolean debug = log.isDebugEnabled();
			for (Iterator iter = userAccess.entrySet().iterator(); iter.hasNext();) {
				Entry entry = (Entry) iter.next();
				server.grantAccess((String) entry.getKey(), (String) entry.getValue());
				if (debug)
					log.debug("grated access to user `"
							+ entry.getKey()
							+ "` with password `"
							+ maskString(((String) entry.getValue()))
							+ "`");
			}
		}
	}

	/**
	 * Utility class used for masking the password with '*'.
	 * @param string
	 * @return
	 */
	private String maskString(String string) {
		StringBuffer buf = new StringBuffer(string.length());
		for (int i = 0; i < string.length(); i++) {
			buf.append('*');
		}
		return buf.toString();
	}

	/**
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	public void destroy() throws Exception {
		log.info("closing object server @" + System.identityHashCode(server));
		server.close();
	}

	/**
	 * @param userAccess The userAccess to set.
	 */
	public void setUserAccess(Properties userAccess) {
		this.userAccess = userAccess;
	}
	
	public void setUserAccessLocation(Resource userAccess) {
		this.userAccess = new Properties();
		try {
			this.userAccess.load(userAccess.getInputStream());
		}
		catch (IOException e) {
			throw new BeanInitializationException("can't find resource", e);
		}
	}

	/**
	 * @return Returns the databaseFile.
	 */
	public Resource getDatabaseFile() {
		return databaseFile;
	}

	/**
	 * @param databaseFile The databaseFile to set.
	 */
	public void setDatabaseFile(Resource databaseFile) {
		this.databaseFile = databaseFile;
	}

	/**
	 * @return Returns the port.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}

}
