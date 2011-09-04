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
import java.sql.Statement;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.objectos.comuns.relational.BatchInsert;
import br.com.objectos.comuns.relational.RelationalException;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class BatchInsertJdbc implements BatchInsert {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final SQLProvider<Connection> connections;

  @Inject
  public BatchInsertJdbc(SQLProvider<Connection> connections) {
    this.connections = connections;
  }

  @Override
  public void of(Object entity) {
  }

  @Override
  public void allOf(Iterable<?> entities) {
    Iterator<?> iterator = entities.iterator();
    allOf(iterator);
  }

  @Override
  public void allOf(Iterator<?> entities) {
    Connection conn = null;

    try {
      conn = connections.get();
      conn.setAutoCommit(false);

      Iterator<Insert> inserts = Iterators.transform(entities, new ToInsert());

      while (inserts.hasNext()) {
        Insert insert = inserts.next();
        String sql = insert.toString();
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        Stmt stmt = new PreparedStatementWrapper(statement);
        insert.prepare(stmt);

        statement.executeUpdate();

        GeneratedKeyCallback keyCallback = insert.getKeyCallback();
        if (keyCallback != null) {
          ResultSet rs = statement.getGeneratedKeys();
          keyCallback.set(rs);
          rs.close();
        }
      }

      conn.commit();
    } catch (SQLException e) {
      try {
        conn.rollback();
      } catch (SQLException e1) {
        logger.error("Could not properly rollback the transaction.", e);
      }
      throw new RelationalException("Could not insert entities. More info below.", e);
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          logger.error("Could not properly close the connection.", e);
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