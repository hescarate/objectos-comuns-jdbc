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

import java.util.List;

import br.com.objectos.comuns.relational.search.Join;
import br.com.objectos.comuns.relational.search.OrderProperty;
import br.com.objectos.comuns.relational.search.SQLBuilder;
import br.com.objectos.comuns.relational.search.SelectColumns;
import br.com.objectos.comuns.relational.search.WhereProperty;
import br.com.objectos.comuns.relational.search.WhereThis;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
// don't mind the abstract impl...
class SqlImpl implements Sql {

  private final JdbcSQLBuilderExec exec;

  private final SQLBuilder sql;

  public SqlImpl(JdbcSQLBuilderExec exec, SQLBuilder sql) {
    this.exec = exec;
    this.sql = sql;
  }

  @Override
  public <T> List<T> list(ResultSetLoader<T> loader) {
    return exec.list(loader, sql);
  }

  @Override
  public <T> T single(ResultSetLoader<T> loader) {
    return exec.single(loader, sql);
  }

  @Override
  public void clearOrders() {
    sql.clearOrders();
  }

  @Override
  public Join join(String relationship, String alias) {
    return sql.join(relationship, alias);
  }

  @Override
  public Join leftJoin(String relationship, String alias) {
    return sql.leftJoin(relationship, alias);
  }

  @Override
  public SelectColumns select(String... columns) {
    return sql.select(columns);
  }

  @Override
  public WhereThis whereThis() {
    return sql.whereThis();
  }

  @Override
  public WhereProperty where(String propertyName) {
    return sql.where(propertyName);
  }

  @Override
  public OrderProperty order(String propertyName) {
    return sql.order(propertyName);
  }

}