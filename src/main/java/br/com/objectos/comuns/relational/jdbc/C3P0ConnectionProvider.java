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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
class C3P0ConnectionProvider implements SQLProvider<Connection> {

  private final ComboPooledDataSource dataSource;

  @Inject
  public C3P0ConnectionProvider(ComboPooledDataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Connection get() throws SQLException {
    return dataSource.getConnection();
  }

}