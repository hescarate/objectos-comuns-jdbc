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

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
abstract class ParamValue<T> {

  final int index;

  final T value;

  public ParamValue(int index, T value) {
    this.index = index;
    this.value = value;
  }

  public final void set(Stmt stmt) {
    if (value == null) {
      int sqlType = sqlType();
      stmt.setNull(index, sqlType);

    } else {
      setValue(stmt);

    }
  }

  abstract int sqlType();

  abstract void setValue(Stmt stmt);

  public static ParamValue<?> valueOf(int index, Object value) {
    Preconditions.checkNotNull(value, "Cannot treat null values this way.");

    ParamValue<?> val; // so we never forget a if condition

    if (value instanceof BigDecimal) {
      val = new ParamBigDecimal(index, (BigDecimal) value);

    } else if (value instanceof Date) {
      val = new ParamDate(index, (Date) value);

    } else if (value instanceof DateTime) {
      val = new ParamDateTime(index, (DateTime) value);

    } else if (value instanceof Double) {
      val = new ParamDouble(index, (Double) value);

    } else if (value instanceof Float) {
      val = new ParamFloat(index, (Float) value);

    } else if (value instanceof Integer) {
      val = new ParamInt(index, (Integer) value);

    } else if (value instanceof LocalDate) {
      val = new ParamLocalDate(index, (LocalDate) value);

    } else if (value instanceof Long) {
      val = new ParamLong(index, (Long) value);

    } else if (value instanceof String) {
      val = new ParamString(index, (String) value);

    } else {
      throw new UnsupportedOperationException("Don't know how to treat typeof " + value.getClass());

    }

    return val;
  }

}