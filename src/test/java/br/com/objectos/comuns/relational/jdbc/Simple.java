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

import com.google.common.base.Objects;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Simple implements Insertable {

  private Integer id;

  private final String string;

  public Simple(String string) {
    this.string = string;
  }

  public Simple(ResultSet rs) throws SQLException {
    this.id = rs.getInt("ID");
    this.string = rs.getString("STRING");
  }

  public Integer getId() {
    return id;
  }

  public String getString() {
    return string;
  }

  @Override
  public Insert getInsert() {
    return Insert.into("SIMPLE") //
        .value("STRING", string) //
        .onGeneratedKey(new GeneratedKeyCallback() {
          @Override
          public void set(ResultSet rs) throws SQLException {
            id = rs.next() ? rs.getInt(1) : null;
          }
        });
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this) //
        .add("id", id) //
        .add("string", string) //
        .toString();
  }

}