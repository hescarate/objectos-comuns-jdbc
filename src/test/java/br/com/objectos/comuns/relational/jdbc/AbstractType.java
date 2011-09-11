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
abstract class AbstractType<T> implements Type<T> {

  private Integer id;

  final T value;

  public AbstractType() {
    this.value = null;
  }

  public AbstractType(T value) {
    this.value = value;
  }

  public AbstractType(ResultSet rs) throws SQLException {
    this.id = rs.getInt("ID");
    this.value = getValue(rs);
  }

  abstract T getValue(ResultSet rs) throws SQLException;
  abstract Insert setValue(Insert insert);

  public Integer getId() {
    return id;
  }

  @Override
  public T getValue() {
    return value;
  }

  @Override
  public Insert getInsert() {
    Insert insert = Insert //
        .into(getTable()) //
        .onGeneratedKey(new GeneratedKeyCallback() {
          @Override
          public void set(ResultSet rs) throws SQLException {
            id = rs.next() ? rs.getInt(1) : null;
          }
        });

    return setValue(insert);
  }

  @SuppressWarnings({
      "rawtypes", "unchecked" })
  @Override
  public ResultSetLoader<Type<T>> getLoader() {
    Class<? extends AbstractType> entityClass = getClass();
    return new ReflectionLoader<Type<T>>((Class<? extends Type<T>>) entityClass);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this) //
        .add("id", id) //
        .add("value", value) //
        .toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof AbstractType)) {
      return false;
    }
    AbstractType<?> other = (AbstractType<?>) obj;
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }

}