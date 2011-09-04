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

import java.sql.Connection;
import java.sql.SQLException;

import br.com.objectos.comuns.relational.BatchInsert;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ObjectosComunsRelationalJdbcModule extends AbstractModule {

  private final ComboPooledDataSource dataSource;

  public ObjectosComunsRelationalJdbcModule(ComboPooledDataSource dataSource) {
    this.dataSource = Preconditions.checkNotNull(dataSource);
  }

  @Override
  protected void configure() {
    bind(BatchInsert.class).to(BatchInsertJdbc.class).in(Scopes.SINGLETON);
  }

  @Provides
  public Connection getConnection() {
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      throw Throwables.propagate(e);
    }
  }

}