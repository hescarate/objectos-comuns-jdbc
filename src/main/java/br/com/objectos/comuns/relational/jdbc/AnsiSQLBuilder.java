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

import br.com.objectos.comuns.relational.search.ConfigurationException;
import br.com.objectos.comuns.relational.search.ProjectProperty;
import br.com.objectos.comuns.relational.search.SQLBuilder;
import br.com.objectos.comuns.relational.search.Select;
import br.com.objectos.comuns.relational.search.SelectColumns;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class AnsiSQLBuilder extends AbstractJdbcSQLFunction implements SQLBuilder {

  @Override
  public SelectColumns select(String... columns) {
    JdbcSelectColumns select = new JdbcSelectColumns(columns);
    if (containsKey(SelectColumns.class)) {
      throw new ConfigurationException("Columns were already defined.");
    }
    putElement(SelectColumns.class, select);
    return select;
  }

  @Override
  public Select selectFrom(Class<?> entityClass) {
    throw new UnsupportedOperationException("JDBC SQL Builder does not support JPA-style queries");
  }

  @Override
  public ProjectProperty project(String propertyName) {
    throw new UnsupportedOperationException();
  }

}