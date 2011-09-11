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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class TypeStringVarchar extends AbstractType<String> {

  public TypeStringVarchar() {
    super();
  }

  public TypeStringVarchar(ResultSet rs) throws SQLException {
    super(rs);
  }

  public TypeStringVarchar(String value) {
    super(value);
  }

  @Override
  public String getTable() {
    return "COMUNS_RELATIONAL.TYPE_VARCHAR";
  }

  @Override
  String getValue(ResultSet rs) throws SQLException {
    return rs.getString("VALUE");
  }

  @Override
  Insert setValue(Insert insert) {
    return insert.value("VALUE", value);
  }

}