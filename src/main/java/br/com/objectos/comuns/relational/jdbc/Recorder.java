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

import static com.google.common.collect.Lists.newArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.com.objectos.comuns.relational.search.OrderProperty;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class Recorder {

  private final StringBuilder sql = new StringBuilder();

  private final List<Object> parameters = newArrayList();

  private final List<String> orders = newArrayList();

  public PreparedStatement prepare(Connection connection) {
    try {

      PreparedStatement stmt = connection.prepareStatement(sql.toString());

      for (int i = 0; i < parameters.size(); i++) {
      }

      return stmt;

    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  public StringBuilder newLine() {
    // and append a trailing space 'just in case' someone forgets it
    return sql.append(" ").append(New.Line);
  }

  public StringBuilder sql(String str) {
    return sql.append(str);
  }

  public void addOrder(OrderProperty order) {
    orders.add(order.toString());
  }

}