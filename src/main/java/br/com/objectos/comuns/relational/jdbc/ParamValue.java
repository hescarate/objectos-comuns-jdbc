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
abstract class ParamValue {

  final int index;

  final Object value;

  public ParamValue(int index, Object value) {
    this.index = index;
    this.value = Preconditions.checkNotNull(value);
  }

  public static ParamValue valueOf(int index, Object value) {
    ParamValue val; // so we never forget a if condition

    if (value instanceof BigDecimal) {
      val = new ParamBigDecimal(index, value);

    } else if (value instanceof Date) {
      val = new ParamDate(index, value);

    } else if (value instanceof DateTime) {
      val = new ParamDateTime(index, value);

    } else if (value instanceof Double) {
      val = new ParamDouble(index, value);

    } else if (value instanceof Float) {
      val = new ParamFloat(index, value);

    } else if (value instanceof Integer) {
      val = new ParamInt(index, value);

    } else if (value instanceof LocalDate) {
      val = new ParamLocalDate(index, value);

    } else if (value instanceof Long) {
      val = new ParamLong(index, value);

    } else if (value instanceof String) {
      val = new ParamString(index, value);

    } else {
      val = new ParamObject(index, value);

    }

    return val;
  }

  abstract void set(Stmt stmt);

}