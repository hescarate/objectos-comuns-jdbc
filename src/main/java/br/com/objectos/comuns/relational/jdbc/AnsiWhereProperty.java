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

import java.util.Collection;

import br.com.objectos.comuns.relational.search.MatchMode;
import br.com.objectos.comuns.relational.search.WhereProperty;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class AnsiWhereProperty extends AbstractJdbcWhere implements WhereProperty {

  final String property;

  String where;

  Object value;

  public AnsiWhereProperty(String property) {
    this.property = Preconditions.checkNotNull(property);
  }

  @Override
  protected void configure(CountingStatement stmt) {
    if (isAtivo()) {
      stmt.set(value);
    }
  }

  @Override
  public void equalTo(Object value) {
    this.value = value;
    if (isSet(value)) {
      where = String.format("%s = ?", property);
    }
  }

  @Override
  public void notEqualTo(Object value) {
    this.value = value;
    if (isSet(value)) {
      where = String.format("%s <> ?", property);
    }
  }

  @Override
  public void like(String value) {
    this.value = value;
    if (isSet(value)) {
      where = String.format("%s like '%%' || ? || '%%'", property);
    }
  }

  @Override
  public void like(String value, MatchMode matchMode) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void endsWith(String value) {
    this.value = value;
    if (isSet(value)) {
      where = String.format("%s like '%%' || ?", property);
    }
  }

  @Override
  public void startsWith(String value) {
    this.value = value;
    if (isSet(value)) {
      where = String.format("%s like ? || '%%'", property);
    }
  }

  @Override
  public void ge(Object value) {
    this.value = value;
    if (isSet(value)) {
      where = String.format("%s >= ?", property);
    }
  }

  @Override
  public void gt(Object value) {
    this.value = value;
    if (isSet(value)) {
      where = String.format("%s > ?", property);
    }
  }

  @Override
  public void lt(Object value) {
    this.value = value;
    if (isSet(value)) {
      where = String.format("%s < ?", property);
    }
  }

  @Override
  public void le(Object value) {
    this.value = value;
    if (isSet(value)) {
      where = String.format("%s <= ?", property);
    }
  }

  @Override
  public void in(@SuppressWarnings("rawtypes") Collection values) {
    this.value = values;
    if (isSet(value)) {
      where = String.format("%s in ?", property);
    }
  }

  @Override
  public void isNull() {
    isSet(null); // there are no parameters to set
    where = String.format("%s is null", property);
  }

  @Override
  public void isNotNull() {
    isSet(null); // there are no parameters to set
    where = String.format("%s is not null", property);
  }

  @Override
  public void sqlRestriction(String sql) {
    isSet(null); // there are no parameters to set
    where = sql;
  }

  @Override
  public String toString() {
    return where;
  }

}