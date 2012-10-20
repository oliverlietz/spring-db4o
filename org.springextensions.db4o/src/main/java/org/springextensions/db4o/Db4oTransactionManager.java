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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.InvalidIsolationLevelException;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.SmartTransactionObject;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.db4o.ObjectContainer;

/**
 * PlatformTransactionManager implementation for db4o.
 * <p/>
 * <p/>
 * This local strategy is an alternative to executing db4o operations within JTA
 * transactions. Its advantage is the ability to work in any environment,
 * for example a standalone application or a test suite. It is <i>not</i> able
 * to provide XA transactions, for example to share transactions with data
 * access.
 * <p/>
 * <p/>
 * This transaction strategy will typically be used in combination with a single
 * db4o database for all db4o access to save resources, typically in a
 * standalone application.
 *
 * @author Costin Leau
 * @see org.springframework.transaction.support.AbstractPlatformTransactionManager
 */
public class Db4oTransactionManager extends AbstractPlatformTransactionManager implements InitializingBean {

    private ObjectContainer objectContainer;

    private final Logger logger = LoggerFactory.getLogger(Db4oTransactionManager.class);

    /**
     * Create a new Db4oTransactionManager instance.
     */
    public Db4oTransactionManager() {
    }

    /**
     * Create a new Db4oTransactionManager instance.
     *
     * @param container container to manage transactions for
     */
    public Db4oTransactionManager(ObjectContainer container) {
        this.objectContainer = container;
    }

    public void afterPropertiesSet() throws Exception {
        if (getObjectContainer() == null)
            throw new IllegalArgumentException("objectContainer is required");
    }

    protected Object doGetTransaction() throws TransactionException {
        Db4oTransactionObject txObject = new Db4oTransactionObject();
        ObjectContainerHolder containerHolder = (ObjectContainerHolder) TransactionSynchronizationManager.getResource(getObjectContainer());
        txObject.setObjectContainerHolder(containerHolder);
        return txObject;
    }

    protected boolean isExistingTransaction(Object transaction) throws TransactionException {
        return ((Db4oTransactionObject) transaction).hasTransaction();
    }

    protected void doBegin(Object transaction, TransactionDefinition transactionDefinition) throws TransactionException {
        if (transactionDefinition.getIsolationLevel() != TransactionDefinition.ISOLATION_DEFAULT) {
            throw new InvalidIsolationLevelException("Db4o does not support an isolation level concept");
        }

        ObjectContainer container = null;

        try {
            Db4oTransactionObject txObject = (Db4oTransactionObject) transaction;
            if (txObject.getObjectContainerHolder() == null) {
                // use the given container
                container = getObjectContainer();
                logger.debug("Using given objectContainer [{}] for the current thread transaction", container);
                txObject.setObjectContainerHolder(new ObjectContainerHolder(container));
            }

            ObjectContainerHolder containerHolder = txObject.getObjectContainerHolder();

            containerHolder.setSynchronizedWithTransaction(true);

            /*
                * We have no notion of flushing inside a db4o object container
                *
                if (transactionDefinition.isReadOnly() && txObject.isNewObjectContainerHolder()) {
                containerHolder.setReadOnly(true);
                }

                if (!transactionDefinition.isReadOnly() && !txObject.isNewObjectContainerHolder()) {
                if (containerHolder.isReadOnly()) {
                containerHolder.setReadOnly(false);
                }
                }
                */

            // start the transaction
            // no-op
            // Register transaction timeout.
            if (transactionDefinition.getTimeout() != TransactionDefinition.TIMEOUT_DEFAULT) {
                txObject.getObjectContainerHolder().setTimeoutInSeconds(transactionDefinition.getTimeout());
            }

            // Bind the session holder to the thread.
            TransactionSynchronizationManager.bindResource(getObjectContainer(), containerHolder);
        } catch (Exception ex) {
            throw new CannotCreateTransactionException("Could not start db4o object container transaction", ex);
        }
    }

    protected Object doSuspend(Object transaction) {
        Db4oTransactionObject txObject = (Db4oTransactionObject) transaction;
        txObject.setObjectContainerHolder(null);
        ObjectContainerHolder containerHolder = (ObjectContainerHolder) TransactionSynchronizationManager.unbindResource(getObjectContainer());
        return new SuspendedResourcesHolder(containerHolder);
    }

    protected void doResume(Object transaction, Object suspendedResources) {
        SuspendedResourcesHolder resourcesHolder = (SuspendedResourcesHolder) suspendedResources;
        if (TransactionSynchronizationManager.hasResource(getObjectContainer())) {
            TransactionSynchronizationManager.unbindResource(getObjectContainer());
        }
        TransactionSynchronizationManager.bindResource(getObjectContainer(), resourcesHolder.getObjectContainerHolder());
    }

    protected void doCommit(DefaultTransactionStatus status) {
        Db4oTransactionObject txObject = (Db4oTransactionObject) status.getTransaction();
        logger.debug("Committing db4o transaction on object container [{}]", txObject.getObjectContainerHolder().getObjectContainer());
        try {
            txObject.getObjectContainerHolder().getObjectContainer().commit();
        } catch (Exception ex) {
            // assumably from commit call to the underlying db4o container
            throw new TransactionSystemException("Could not commit db4o transaction", ex);
        }
    }

    protected void doRollback(DefaultTransactionStatus status) {
        Db4oTransactionObject txObject = (Db4oTransactionObject) status.getTransaction();
        logger.debug("Rolling back db4o transaction on object container [{}]", txObject.getObjectContainerHolder().getObjectContainer());
        try {
            txObject.getObjectContainerHolder().getObjectContainer().rollback();
        } catch (Exception ex) {
            throw new TransactionSystemException("Could not roll back db4o transaction", ex);
        } finally {
            try {
                // TODO: refresh the container somehow
            } catch (Exception e) {
                // we already throw an exception (hold back this one).
            }
        }
    }

    protected void doSetRollbackOnly(DefaultTransactionStatus status) {
        Db4oTransactionObject txObject = (Db4oTransactionObject) status.getTransaction();
        logger.debug("Setting db4o transaction on object container [{}] rollback-only", txObject.getObjectContainerHolder().getObjectContainer());
        txObject.setRollbackOnly();
    }

    protected void doCleanupAfterCompletion(Object transaction) {
        Db4oTransactionObject txObject = (Db4oTransactionObject) transaction;

        // Remove the session holder from the thread.
        TransactionSynchronizationManager.unbindResource(getObjectContainer());

        /*
           ObjectContainer container = txObject.getObjectContainerHolder().getObjectContainer();
           if (txObject.isNewContainerHolder()) {
           if (logger.isDebugEnabled()) {
           logger.debug("Cleaning up db4o object container [" + container + "] after transaction");
           }
           // do nothing
           }
           else {
           if (logger.isDebugEnabled()) {
           logger.debug("Not closing pre-bound db4o object container [" + container + "] after transaction");
           }
           }
           */
        txObject.getObjectContainerHolder().clear();
    }

    /**
     * Internal transaction object.
     *
     * @see org.springframework.transaction.support.SmartTransactionObject
     */
    private static class Db4oTransactionObject implements SmartTransactionObject {

        private ObjectContainerHolder objectContainerHolder;

        public void setObjectContainerHolder(ObjectContainerHolder containerHolder) {
            this.objectContainerHolder = containerHolder;
        }

        public ObjectContainerHolder getObjectContainerHolder() {
            return objectContainerHolder;
        }

        public void setRollbackOnly() {
            getObjectContainerHolder().setRollbackOnly();
        }

        public boolean isRollbackOnly() {
            return getObjectContainerHolder().isRollbackOnly();
        }

        public void flush() {
            // no-op
        }

        /**
         * Db4o executes everything in a transaction.
         *
         * @return
         */
        public boolean hasTransaction() {
            return (objectContainerHolder != null);
        }

    }

    /**
     * Holder for suspended resources. Used internally by doSuspend and
     * doResume.
     */
    private static class SuspendedResourcesHolder {

        private final ObjectContainerHolder objectContainerHolder;

        private SuspendedResourcesHolder(ObjectContainerHolder containerHolder) {
            this.objectContainerHolder = containerHolder;
        }

        private ObjectContainerHolder getObjectContainerHolder() {
            return objectContainerHolder;
        }

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
