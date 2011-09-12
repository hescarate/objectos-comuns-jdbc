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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import br.com.objectos.comuns.relational.search.SQLBuilder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class JdbcSQLBuilderExecGuice implements JdbcSQLBuilderExec {

  private final SQLProvider<Connection> connections;

  @Inject
  public JdbcSQLBuilderExecGuice(SQLProvider<Connection> connections) {
    this.connections = connections;
  }

  @Override
  public <T> List<T> list(ResultSetLoader<T> loader, SQLBuilder sql) {
    List<T> result = null;

    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {

      connection = connections.get();
      stmt = new JdbcConfigure(sql).prepare(connection);
      rs = stmt.executeQuery();
      Iterator<ResultSet> iterator = new ResultSetIterator(rs);
      Iterator<T> loaded = Iterators.transform(iterator, new LoaderFunction<T>(loader));
      result = ImmutableList.copyOf(loaded);

    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    } finally {
      Closer.close(connection);
      Closer.close(stmt);
      Closer.close(rs);
    }

    return result;
  }

  @Override
  public <T> T single(ResultSetLoader<T> loader, SQLBuilder sql) {
    T result = null;

    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {

      connection = connections.get();
      stmt = new JdbcConfigure(sql).prepare(connection);
      rs = stmt.executeQuery();
      result = rs.next() ? loader.load(rs) : null;

    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    } finally {
      Closer.close(connection);
      Closer.close(stmt);
      Closer.close(rs);
    }

    return result;
  }

}