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
import java.sql.Savepoint;
import java.sql.Statement;

import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class TransactionsGuice implements Transactions {

  private final SQLProvider<Connection> connections;

  @Inject
  public TransactionsGuice(SQLProvider<Connection> connections) {
    this.connections = connections;
  }

  @Override
  public void execute(AtomicInsertOperation operation) throws TransactionRolledbackException {

    Connection conn = null;

    try {

      conn = connections.get();
      conn.setAutoCommit(false);
      Savepoint savepoint = conn.setSavepoint();

      Insertion insertion = new InsertionImpl(conn);

      try {

        operation.execute(insertion);

      } catch (SQLException inner) {

        conn.rollback(savepoint);
        throw new TransactionRolledbackException(inner);

      }

      conn.commit();

    } catch (SQLException e) {

      e.printStackTrace();

    } finally {

      Closer.close(conn);

    }

  }

  private class InsertionImpl implements Insertion {

    private final Connection conn;

    public InsertionImpl(Connection conn) {
      this.conn = conn;
    }

    @Override
    public void of(Insertable entity) throws SQLException {

      PreparedStatement statement = null;
      ResultSet rs = null;

      try {

        Insert insert = entity.getInsert();
        String sql = insert.toString();
        statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        Stmt stmt = new PreparedStatementWrapper(statement);
        insert.prepare(stmt);

        statement.executeUpdate();

        GeneratedKeyCallback keyCallback = insert.getKeyCallback();
        if (keyCallback != null) {
          rs = statement.getGeneratedKeys();
          keyCallback.set(rs);
          rs.close();
        }

      } finally {

        Closer.close(statement);
        Closer.close(rs);

      }

    }

  }

}