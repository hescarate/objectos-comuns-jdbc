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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.google.common.base.Throwables;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class PreparedStatementWrapper implements Stmt {

  private final PreparedStatement stmt;

  public PreparedStatementWrapper(PreparedStatement stmt) {
    this.stmt = stmt;
  }

  // OK, this is a fine example where Scala would be just great... pg 176 of
  // Programming in Scala 2nd ed... (or I think, can't memorize page numbers...)
  @Override
  public void setNull(int index, int sqlType) {
    try {
      stmt.setNull(index, sqlType);
    } catch (SQLException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public void setDate(int index, Date value) {
    try {
      java.sql.Date sql = value != null ? new java.sql.Date(value.getTime()) : null;
      stmt.setDate(index, sql);
    } catch (SQLException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public void setTimestamp(int index, Date value) {
    try {
      java.sql.Timestamp sql = value != null ? new java.sql.Timestamp(value.getTime()) : null;
      stmt.setTimestamp(index, sql);
    } catch (SQLException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public void setString(int index, String value) {
    try {
      stmt.setString(index, value);
    } catch (SQLException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public void setObject(int index, Object value) {
    try {
      stmt.setObject(index, value);
    } catch (SQLException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public void setBigDecimal(int index, BigDecimal value) {
    try {
      stmt.setBigDecimal(index, value);
    } catch (SQLException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public void setDouble(int index, Double value) {
    try {
      stmt.setDouble(index, value);
    } catch (SQLException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public void setFloat(int index, Float value) {
    try {
      stmt.setFloat(index, value);
    } catch (SQLException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public void setInt(int index, Integer value) {
    try {
      stmt.setInt(index, value);
    } catch (SQLException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public void setLong(int index, Long value) {
    try {
      stmt.setLong(index, value);
    } catch (SQLException e) {
      throw Throwables.propagate(e);
    }
  }

}