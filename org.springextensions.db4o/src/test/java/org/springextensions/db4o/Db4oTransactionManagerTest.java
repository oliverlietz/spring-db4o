/*
 * Copyright 2005-2011 the original author or authors.
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.transaction.InvalidIsolationLevelException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Costin Leau
 * @author olli
 */
public class Db4oTransactionManagerTest {

    @Test
    public void testTransactionCommit() throws Exception {
        final ExtObjectContainer container = mock(ExtObjectContainer.class);
        when(container.identity()).thenReturn(null);

        PlatformTransactionManager tm = new Db4oTransactionManager(container);
        TransactionTemplate tt = new TransactionTemplate(tm);

        Assert.assertTrue(!TransactionSynchronizationManager.hasResource(container), "Has no container");
        Assert.assertTrue(!TransactionSynchronizationManager.isSynchronizationActive(), "JTA synchronizations not active");

        tt.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                Assert.assertTrue(TransactionSynchronizationManager.hasResource(container), "Has thread session");
                Db4oTemplate template = new Db4oTemplate(container);
                template.identity();
            }
        });

        Assert.assertTrue(!TransactionSynchronizationManager.hasResource(container), "Has no container");
        Assert.assertTrue(!TransactionSynchronizationManager.isSynchronizationActive(), "JTA synchronizations not active");

        verify(container).commit();
    }

    @Test
    public void testTransactionRollback() throws Exception {
        final ExtObjectContainer container = mock(ExtObjectContainer.class);
        when(container.identity()).thenReturn(null);
        when(container.ext()).thenReturn(container);

        PlatformTransactionManager tm = new Db4oTransactionManager(container);
        TransactionTemplate tt = new TransactionTemplate(tm);

        Assert.assertTrue(!TransactionSynchronizationManager.hasResource(container), "Has no container");
        Assert.assertTrue(!TransactionSynchronizationManager.isSynchronizationActive(), "JTA synchronizations not active");

        try {
            tt.execute(new TransactionCallbackWithoutResult() {
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    Assert.assertTrue(TransactionSynchronizationManager.hasResource(container), "Has thread session");
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

        Assert.assertTrue(!TransactionSynchronizationManager.hasResource(container), "Has no container");
        Assert.assertTrue(!TransactionSynchronizationManager.isSynchronizationActive(), "JTA synchronizations not active");

        verify(container).rollback();
    }

    @Test
    public void testTransactionRollbackOnly() throws Exception {
        final ExtObjectContainer container = mock(ExtObjectContainer.class);
        when(container.identity()).thenReturn(null);
        when(container.ext()).thenReturn(container);

        PlatformTransactionManager tm = new Db4oTransactionManager(container);
        TransactionTemplate tt = new TransactionTemplate(tm);

        Assert.assertTrue(!TransactionSynchronizationManager.hasResource(container), "Has no container");
        Assert.assertTrue(!TransactionSynchronizationManager.isSynchronizationActive());

        try {
            tt.execute(new TransactionCallbackWithoutResult() {
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    Assert.assertTrue(TransactionSynchronizationManager.hasResource(container), "Has thread session");
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

        Assert.assertTrue(!TransactionSynchronizationManager.hasResource(container), "Has no container");
        Assert.assertTrue(!TransactionSynchronizationManager.isSynchronizationActive(), "JTA synchronizations not active");

        verify(container).rollback();
    }

    @Test
    public void testInvalidIsolation() throws Exception {
        final ExtObjectContainer container = mock(ExtObjectContainer.class);

        PlatformTransactionManager tm = new Db4oTransactionManager(container);
        TransactionTemplate tt = new TransactionTemplate(tm);

        Assert.assertTrue(!TransactionSynchronizationManager.hasResource(container), "Has no container");
        Assert.assertTrue(!TransactionSynchronizationManager.isSynchronizationActive(), "JTA synchronizations not active");

        tt.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        try {
            tt.execute(new TransactionCallbackWithoutResult() {
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    Assert.assertTrue(TransactionSynchronizationManager.hasResource(container), "Has thread session");
                    Db4oTemplate template = new Db4oTemplate(container);
                    template.execute(new Db4oCallback() {
                        public Object doInDb4o(ObjectContainer cont) {
                            return null;
                        }
                    });
                }
            });
            Assert.fail("Should have thrown InvalidIsolationLevelException");
        } catch (InvalidIsolationLevelException e) {
            // it's okay
        }

        Assert.assertTrue(!TransactionSynchronizationManager.hasResource(container), "Has no container");
        Assert.assertTrue(!TransactionSynchronizationManager.isSynchronizationActive(), "JTA synchronizations not active");

        // TODO verify(container)....; exception thrown?
    }

}
