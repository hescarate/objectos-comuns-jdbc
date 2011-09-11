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
import java.util.Date;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class TypeDate extends AbstractType<Date> {

  public TypeDate() {
    super();
  }

  public TypeDate(Date value) {
    super(value);
  }

  public TypeDate(ResultSet rs) throws SQLException {
    super(rs);
  }

  @Override
  public String getTable() {
    return "COMUNS_RELATIONAL.TYPE_DATETIME";
  }

  @Override
  Date getValue(ResultSet rs) throws SQLException {
    Timestamp stamp = rs.getTimestamp("VALUE");
    return stamp != null ? new Date(stamp.getTime()) : null;
  }

  @Override
  Insert setValue(Insert insert) {
    return insert.value("VALUE", value);
  }

}