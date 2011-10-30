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
package org.springextensions.db4o.io;

import com.db4o.ext.Db4oIOException;
import com.db4o.io.Bin;
import com.db4o.io.BinConfiguration;
import com.db4o.io.PagingMemoryBin;
import com.db4o.io.PagingMemoryStorage;
import com.db4o.io.Storage;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

/**
 * <p><code>ResourceStorage</code> reads its initial database content from an <code>org.springframework.core.io.Resource</code>
 * and uses a <code>com.db4o.io.PagingMemoryBin</code> for further reads and writes.</p>
 * <p>Its objects are <strong>not persisted</strong> in the original <code>org.springframework.core.io.Resource</code>.</p>
 * <p/>
 * author: olli
 */
public class ResourceStorage extends PagingMemoryStorage implements Storage {

    protected ResourceLoader resourceLoader;

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * @param uri
     * @return Return whether this resource actually exists in physical form.
     * @see org.springframework.core.io.Resource#exists()
     */
    @Override
    public boolean exists(String uri) {
        Resource resource = resourceLoader.getResource(uri);
        return resource.exists();
    }

    /**
     * opens an <code>org.springframework.core.io.Resource</code> for the given URI initially
     * and uses a <code>com.db4o.io.PagingMemoryBin</code> for further reads and writes
     *
     * @param config
     * @return
     * @throws Db4oIOException
     * @see org.springframework.core.io.ResourceLoader#getResource(java.lang.String)
     */
    @Override
    public Bin open(BinConfiguration config) throws Db4oIOException {
        return super.open(config);
    }

    /**
     * @param config
     * @param pageSize
     * @return
     */
    @Override
    protected Bin produceBin(BinConfiguration config, int pageSize) {
        try {
            Resource resource = resourceLoader.getResource(config.uri());
            byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            final Bin newStorage = new PagingMemoryBin(pageSize, config.initialLength());
            newStorage.write(0, bytes, bytes.length);
            return newStorage;
        } catch (Exception e) {
            throw new Db4oIOException(e.getMessage());
        }
    }

}
