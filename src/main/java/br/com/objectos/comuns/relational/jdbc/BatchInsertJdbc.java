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
import java.sql.SQLException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.objectos.comuns.bancodedados.Insercao;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class BatchInsertJdbc implements Insercao {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final Provider<Connection> connections;

  public BatchInsertJdbc(Provider<Connection> connections) {
    this.connections = connections;
  }

  @Override
  public void de(Object entity) {
  }

  @Override
  public void deTodos(Iterable<?> entities) {
  }

  @Override
  public void deTodos(Iterator<?> entities) {
    Connection conn = null;

    try {
      conn = connections.get();
      conn.setAutoCommit(false);

      Iterator<Insert> inserts = Iterators.transform(entities, new ToInsert());

      while (inserts.hasNext()) {
        Insert insert = inserts.next();
        String sql = insert.toString();
        PreparedStatement statement = conn.prepareStatement(sql);

        Stmt stmt = new PreparedStatementWrapper(statement);
        insert.prepare(stmt);

        statement.executeUpdate();
      }
    } catch (SQLException e) {

    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          logger.error("", e);
        }
      }
    }
  }

  private class ToInsert implements Function<Object, Insert> {
    @Override
    public Insert apply(Object input) {
      Preconditions.checkArgument(input instanceof Insertable,
          "Entity must be instanceof Insertable.");
      Insertable insertable = (Insertable) input;
      return insertable.getInsert();
    }
  }

}