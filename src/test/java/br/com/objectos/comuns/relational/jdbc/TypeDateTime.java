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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.joda.time.DateTime;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class TypeDateTime extends AbstractType<DateTime> {

  public TypeDateTime() {
    super();
  }

  public TypeDateTime(DateTime value) {
    super(value);
  }

  public TypeDateTime(ResultSet rs) throws SQLException {
    super(rs);
  }

  @Override
  public String getTable() {
    return "COMUNS_RELATIONAL.TYPE_DATETIME";
  }

  @Override
  DateTime getValue(ResultSet rs) throws SQLException {
    Timestamp date = rs.getTimestamp("VALUE");
    return date != null ? new DateTime(date) : null;
  }

  @Override
  Insert setValue(Insert insert) {
    return insert.value("VALUE", value);
  }

}