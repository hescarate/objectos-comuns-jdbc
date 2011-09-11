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

import br.com.objectos.comuns.relational.search.SQLBuilder;

import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class SqlProvider implements Provider<Sql> {

  private final JdbcSQLBuilderExec exec;

  private final Provider<SQLBuilder> sqlProvider;

  public SqlProvider(JdbcSQLBuilderExec exec, Provider<SQLBuilder> sqlProvider) {
    this.exec = exec;
    this.sqlProvider = sqlProvider;
  }

  @Override
  public Sql get() {
    SQLBuilder sql = sqlProvider.get();
    return new SqlImpl(exec, sql);
  }

}