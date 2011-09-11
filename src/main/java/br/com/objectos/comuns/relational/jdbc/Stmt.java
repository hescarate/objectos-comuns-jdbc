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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public interface Stmt {

  void setNull(int index, int sqlType);

  void setDate(int index, Date value);
  void setTimestamp(int index, Date value);

  void setString(int index, String value);

  void setObject(int index, Object value);

  void setBigDecimal(int index, BigDecimal value);
  void setDouble(int index, Double value);
  void setFloat(int index, Float value);
  void setInt(int index, Integer value);
  void setLong(int index, Long value);

}