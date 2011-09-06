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
import java.util.Collection;

import br.com.objectos.comuns.relational.search.Element;
import br.com.objectos.comuns.relational.search.OrderProperty;
import br.com.objectos.comuns.relational.search.SQLBuilder;
import br.com.objectos.comuns.relational.search.SelectColumns;
import br.com.objectos.comuns.relational.search.Where;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class JdbcConfigure {

  private final SQLBuilder sql;

  public JdbcConfigure(SQLBuilder sql) {
    this.sql = sql;
  }

  public PreparedStatement prepare(Connection connection) {
    try {

      StringBuilder sql = new StringBuilder();

      makeSql(sql);

      PreparedStatement stmt = connection.prepareStatement(sql.toString());

      CountingStatement countingStatement = new CountingStatement(stmt);
      Collection<Element> whereEls = getElements(Where.class);
      for (Element where : whereEls) {
        where.configure(countingStatement);
      }

      return stmt;

    } catch (SQLException e) {

      throw new SQLRuntimeException(e);

    }
  }

  private void makeSql(StringBuilder sql) {

    makeSelect(sql);

    makeWhere(sql);

    makeOrderBy(sql);

  }

  private void makeSelect(StringBuilder sql) {
    makeKey(sql, SelectColumns.class);
  }

  private void makeWhere(StringBuilder sql) {
    Collection<String> where = getStrings(Where.class);
    if (!where.isEmpty()) {
      sql.append("where ");
      String _where = Joiner.on("and " + New.Line).join(where);
      sql.append(_where);
    }
  }

  private void makeOrderBy(StringBuilder sql) {
    Collection<String> orders = validateAndGetStrings(OrderProperty.class);
    if (!orders.isEmpty()) {
      sql.append("order by ");
      String _orders = Joiner.on(", " + New.Line).join(orders);
      sql.append(_orders);
    }
  }

  private void makeKey(StringBuilder sql, Class<?> key) {
    Collection<Element> els = getElements(key);

    for (Element el : els) {
      sql.append(el.toString());
    }
  }

  private Collection<Element> getElements(Class<?> keyClass) {
    return sql.getElements(keyClass);
  }

  private Collection<String> getStrings(Class<?> keyClass) {
    Collection<Element> els = sql.getElements(keyClass);
    return Collections2.transform(els, Functions.toStringFunction());
  }

  private Collection<String> validateAndGetStrings(Class<?> keyClass) {
    Collection<Element> els = sql.getElements(keyClass);
    return Collections2.transform(els, new ToString());
  }

  private class ToString implements Function<Element, String> {
    @Override
    public String apply(Element input) {
      return input.toString();
    }
  }

}