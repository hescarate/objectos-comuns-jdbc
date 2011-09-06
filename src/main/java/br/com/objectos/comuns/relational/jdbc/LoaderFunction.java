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

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.common.base.Function;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class LoaderFunction<T> implements Function<ResultSet, T> {

  private final ResultSetLoader<T> loader;

  public LoaderFunction(ResultSetLoader<T> loader) {
    this.loader = loader;
  }

  @Override
  public T apply(ResultSet input) {
    try {
      return loader.load(input);
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

}