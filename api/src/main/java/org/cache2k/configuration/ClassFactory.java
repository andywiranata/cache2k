package org.cache2k.configuration;

/*
 * #%L
 * cache2k API
 * %%
 * Copyright (C) 2000 - 2016 headissue GmbH, Munich
 * %%
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
 * #L%
 */

import org.cache2k.CacheManager;

import java.io.Serializable;

/**
 * Creates a new instance of the customization based on the class name and the class loader
 * in effect by the cache.
 *
 * @author Jens Wilke
 */
public final class ClassFactory<T> implements CustomizationFactory<T>, Serializable {

  private String className;

  /**
   * Construct a customization factory based on the class name.
   *
   * @param className Fully qualified class name, used to create the class instance
   *                  via a {@link ClassLoader#loadClass(String)}. The class must have
   *                  a default constructor. Not null.
   */
  public ClassFactory(final String className) {
    if (className == null) {
      throw new NullPointerException("className");
    }
    this.className = className;
  }

  @Override
  public T create(final CacheManager manager) throws Exception {
    return (T) manager.getClassLoader().loadClass(className).newInstance();
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) return true;
    if (!(other instanceof ClassFactory)) return false;
    ClassFactory<?> _that = (ClassFactory<?>) other;
    return className.equals(_that.className);
  }

  @Override
  public int hashCode() {
    return className.hashCode();
  }

}
