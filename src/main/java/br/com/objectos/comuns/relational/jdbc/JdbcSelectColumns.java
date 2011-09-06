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

import java.util.List;

import br.com.objectos.comuns.relational.search.SelectColumns;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class JdbcSelectColumns extends AbstractJdbcElement implements SelectColumns {

  private final List<String> columns;

  private String table;

  public JdbcSelectColumns(String... columns) {
    this.columns = ImmutableList.copyOf(columns);
  }

  @Override
  public JdbcSelectColumns from(String table) {
    this.table = table;
    return this;
  }

  @Override
  protected void validateState() {
    Preconditions.checkNotNull(table, "A table to select from MUST be defined.");
  }

  @Override
  public String toString() {
    String cols = Joiner.on(", ").skipNulls().join(columns);
    return String.format("select %s from %s%s", cols, table, New.Line);
  }

}