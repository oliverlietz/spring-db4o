/*
 * Copyright 2011 the original author or authors.
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
package org.springextensions.db4o.config;

import java.io.IOException;

import com.db4o.config.ConfigScope;
import com.db4o.config.FileConfiguration;
import com.db4o.io.Storage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * author: olli
 */
public class FileConfigurerTest {

    private FileConfiguration fileConfiguration;

    private FileConfigurer fileConfigurer;

    /**
     * run before every method because of testSetDisableCommitRecoveryToFalse
     */
    @BeforeMethod
    public void setup() {
        fileConfiguration = mock(FileConfiguration.class);
        fileConfigurer = new FileConfigurer(fileConfiguration);
    }

    @Test
    public void testGetFreespace() {
        Assert.assertNotNull(fileConfigurer.getFreespace());
    }

    @Test
    public void testSetAsynchronousSync() {
        boolean asynchronousSync = true;
        fileConfigurer.setAsynchronousSync(asynchronousSync);
        verify(fileConfiguration).asynchronousSync(asynchronousSync);
    }

    @Test
    public void testSetBlockSize() {
        int blockSize = 8;
        fileConfigurer.setBlockSize(blockSize);
        verify(fileConfiguration).blockSize(blockSize);
    }

    @Test
    public void testSetDatabaseGrowthSize() {
        int databaseGrowthSize = 4096;
        fileConfigurer.setDatabaseGrowthSize(databaseGrowthSize);
        verify(fileConfiguration).databaseGrowthSize(databaseGrowthSize);
    }

    @Test
    public void testSetDisableCommitRecoveryToTrue() {
        boolean disableCommitRecovery = true;
        fileConfigurer.setDisableCommitRecovery(disableCommitRecovery);
        verify(fileConfiguration).disableCommitRecovery();
    }

    @Test
    public void testSetDisableCommitRecoveryToFalse() {
        boolean disableCommitRecovery = false;
        fileConfigurer.setDisableCommitRecovery(disableCommitRecovery);
        verify(fileConfiguration, never()).disableCommitRecovery();
    }

    @Test
    public void testSetGenerateUUIDsWithScopeDisabled() {
        fileConfigurer.setGenerateUUIDs(FileConfigurer.Scope.disabled);
        verify(fileConfiguration).generateUUIDs(ConfigScope.DISABLED);
        verify(fileConfiguration, never()).generateUUIDs(ConfigScope.INDIVIDUALLY);
        verify(fileConfiguration, never()).generateUUIDs(ConfigScope.GLOBALLY);
    }

    @Test
    public void testSetGenerateUUIDsWithScopeIndividually() {
        fileConfigurer.setGenerateUUIDs(FileConfigurer.Scope.individually);
        verify(fileConfiguration).generateUUIDs(ConfigScope.INDIVIDUALLY);
        verify(fileConfiguration, never()).generateUUIDs(ConfigScope.DISABLED);
        verify(fileConfiguration, never()).generateUUIDs(ConfigScope.GLOBALLY);
    }

    @Test
    public void testSetGenerateUUIDsWithScopeGlobally() {
        fileConfigurer.setGenerateUUIDs(FileConfigurer.Scope.globally);
        verify(fileConfiguration).generateUUIDs(ConfigScope.GLOBALLY);
        verify(fileConfiguration, never()).generateUUIDs(ConfigScope.DISABLED);
        verify(fileConfiguration, never()).generateUUIDs(ConfigScope.INDIVIDUALLY);
    }

    @Test
    public void testSetGenerateCommitTimestamps() {
        boolean generateCommitTimestamps = true;
        fileConfigurer.setGenerateCommitTimestamps(generateCommitTimestamps);
        verify(fileConfiguration).generateCommitTimestamps(generateCommitTimestamps);
    }

    @Test
    public void testSetStorage() {
        Storage storage = mock(Storage.class);
        fileConfigurer.setStorage(storage);
        verify(fileConfiguration).storage(storage);
    }

    @Test
    public void testSetLockDatabaseFile() {
        boolean lockDatabaseFile = true;
        fileConfigurer.setLockDatabaseFile(lockDatabaseFile);
        verify(fileConfiguration).lockDatabaseFile(lockDatabaseFile);
    }

    @Test
    public void testSetReserveStorageSpace() {
        long reserveStorageSpace = Long.MAX_VALUE;
        fileConfigurer.setReserveStorageSpace(reserveStorageSpace);
        verify(fileConfiguration).reserveStorageSpace(reserveStorageSpace);
    }

    @Test
    public void testSetBlobPath() throws IOException {
        String blobPath = "";
        fileConfigurer.setBlobPath(blobPath);
        verify(fileConfiguration).blobPath(blobPath);
    }

    @Test
    public void testSetReadOnly() {
        boolean readOnly = true;
        fileConfigurer.setReadOnly(readOnly);
        verify(fileConfiguration).readOnly(readOnly);
    }

    @Test
    public void testSetRecoveryMode() {
        boolean recoveryMode = true;
        fileConfigurer.setRecoveryMode(recoveryMode);
        verify(fileConfiguration).recoveryMode(recoveryMode);
    }

}
