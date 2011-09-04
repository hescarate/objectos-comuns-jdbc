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

import java.beans.PropertyVetoException;

import br.com.objectos.comuns.sql.JdbcCredentials;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Singleton
public class C3P0DataSourceProvider implements Provider<ComboPooledDataSource> {

  private final JdbcCredentials credentials;

  @Inject
  public C3P0DataSourceProvider(@C3P0 JdbcCredentials credentials) {
    this.credentials = credentials;
  }

  @Override
  public final ComboPooledDataSource get() {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();

    try {

      dataSource.setDriverClass(credentials.getDriverClass());
      dataSource.setJdbcUrl(credentials.getUrl());
      dataSource.setUser(credentials.getUser());
      dataSource.setPassword(credentials.getPassword());

      configureDataSource(dataSource);

    } catch (PropertyVetoException e) {

      // it is the very first set for the driverClass
      // on a side note: that is why immutability is cool.

    }

    return dataSource;
  }

  protected void configureDataSource(ComboPooledDataSource dataSource) {
  }

}