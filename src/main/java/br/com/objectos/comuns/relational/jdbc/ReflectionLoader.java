/*
 * Copyright 2011 Objectos, Fábrica de Software LTDA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package br.com.objectos.comuns.relational.jdbc;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.common.base.Throwables;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ReflectionLoader<T> implements ResultSetLoader<T> {

  private final Class<? extends T> entityClass;

  public ReflectionLoader(Class<? extends T> entityClass) {
    this.entityClass = entityClass;
  }

  @Override
  public T load(ResultSet rs) throws SQLException {
    try {

      Constructor<? extends T> constructor = entityClass.getConstructor(ResultSet.class);
      return constructor.newInstance(rs);

    } catch (Exception e) {
      throw Throwables.propagate(e);

    }
  }

}