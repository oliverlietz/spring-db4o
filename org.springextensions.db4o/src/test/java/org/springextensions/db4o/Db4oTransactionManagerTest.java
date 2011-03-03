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

import com.db4o.ObjectContainer;
import com.db4o.ext.ExtObjectContainer;
import org.easymock.MockControl;
import org.springframework.transaction.InvalidIsolationLevelException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class Db4oTransactionManagerTest {

    @Test
    public void testTransactionCommit() throws Exception {
        MockControl containerControl = MockControl.createControl(ExtObjectContainer.class);
        final ExtObjectContainer container = (ExtObjectContainer) containerControl.getMock();

        containerControl.expectAndReturn(container.identity(), null);
        container.commit();
        containerControl.replay();

        PlatformTransactionManager tm = new Db4oTransactionManager(container);
        TransactionTemplate tt = new TransactionTemplate(tm);
        AssertJUnit.assertTrue("Has no container", !TransactionSynchronizationManager.hasResource(container));
        AssertJUnit.assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isSynchronizationActive());

        tt.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                AssertJUnit.assertTrue("Has thread session", TransactionSynchronizationManager.hasResource(container));
                Db4oTemplate template = new Db4oTemplate(container);
                template.identity();
            }
        });

        AssertJUnit.assertTrue("Has no container", !TransactionSynchronizationManager.hasResource(container));
        AssertJUnit.assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isSynchronizationActive());

        containerControl.verify();
    }

    @Test
    public void testTransactionRollback() throws Exception {
        MockControl containerControl = MockControl.createControl(ExtObjectContainer.class);
        final ExtObjectContainer container = (ExtObjectContainer) containerControl.getMock();

        containerControl.expectAndReturn(container.ext(), container);
        containerControl.expectAndReturn(container.identity(), null);
        container.rollback();
        containerControl.replay();

        PlatformTransactionManager tm = new Db4oTransactionManager(container);
        TransactionTemplate tt = new TransactionTemplate(tm);
        AssertJUnit.assertTrue("Has no container", !TransactionSynchronizationManager.hasResource(container));
        AssertJUnit.assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isSynchronizationActive());

        try {
            tt.execute(new TransactionCallbackWithoutResult() {
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    AssertJUnit.assertTrue("Has thread session", TransactionSynchronizationManager.hasResource(container));
                    Db4oTemplate template = new Db4oTemplate(container);
                    template.execute(new Db4oCallback() {
                        public Object doInDb4o(ObjectContainer cont) {
                            cont.ext().identity();
                            throw new RuntimeException();
                        }

                    });
                }
            });
        } catch (RuntimeException e) {
            // it's okay
        }

        AssertJUnit.assertTrue("Has no container", !TransactionSynchronizationManager.hasResource(container));
        AssertJUnit.assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isSynchronizationActive());

        containerControl.verify();
    }

    @Test
    public void testTransactionRollbackOnly() throws Exception {
        MockControl containerControl = MockControl.createControl(ExtObjectContainer.class);
        final ExtObjectContainer container = (ExtObjectContainer) containerControl.getMock();

        containerControl.expectAndReturn(container.ext(), container);
        containerControl.expectAndReturn(container.identity(), null);
        container.rollback();
        containerControl.replay();

        PlatformTransactionManager tm = new Db4oTransactionManager(container);
        TransactionTemplate tt = new TransactionTemplate(tm);
        AssertJUnit.assertTrue("Has no container", !TransactionSynchronizationManager.hasResource(container));
        AssertJUnit.assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isSynchronizationActive());

        try {
            tt.execute(new TransactionCallbackWithoutResult() {
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    AssertJUnit.assertTrue("Has thread session", TransactionSynchronizationManager.hasResource(container));
                    Db4oTemplate template = new Db4oTemplate(container);
                    template.execute(new Db4oCallback() {
                        public Object doInDb4o(ObjectContainer cont) {
                            cont.ext().identity();
                            return null;
                        }

                    });
                    status.setRollbackOnly();
                }
            });
        } catch (RuntimeException e) {
            // it's okay
        }

        AssertJUnit.assertTrue("Has no container", !TransactionSynchronizationManager.hasResource(container));
        AssertJUnit.assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isSynchronizationActive());

        containerControl.verify();
    }


    @Test
    public void testInvalidIsolation() throws Exception {
        MockControl containerControl = MockControl.createControl(ExtObjectContainer.class);
        final ExtObjectContainer container = (ExtObjectContainer) containerControl.getMock();

        containerControl.replay();

        PlatformTransactionManager tm = new Db4oTransactionManager(container);
        TransactionTemplate tt = new TransactionTemplate(tm);
        AssertJUnit.assertTrue("Has no container", !TransactionSynchronizationManager.hasResource(container));
        AssertJUnit.assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isSynchronizationActive());

        tt.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        try {
            tt.execute(new TransactionCallbackWithoutResult() {
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    AssertJUnit.assertTrue("Has thread session", TransactionSynchronizationManager.hasResource(container));
                    Db4oTemplate template = new Db4oTemplate(container);
                    template.execute(new Db4oCallback() {
                        public Object doInDb4o(ObjectContainer cont) {
                            return null;
                        }
                    });
                }
            });
            AssertJUnit.fail("Should have thrown InvalidIsolationLevelException");

        } catch (InvalidIsolationLevelException e) {
            // it's okay
        }

        AssertJUnit.assertTrue("Has no container", !TransactionSynchronizationManager.hasResource(container));
        AssertJUnit.assertTrue("JTA synchronizations not active", !TransactionSynchronizationManager.isSynchronizationActive());

        containerControl.verify();
    }

}
