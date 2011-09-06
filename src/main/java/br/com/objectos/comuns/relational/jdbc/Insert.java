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

import static com.google.common.collect.Maps.newLinkedHashMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Insert {

  private final String table;

  private final Map<String, ParamValue> values = newLinkedHashMap();

  private GeneratedKeyCallback keyCallback;

  public Insert(String table) {
    this.table = table;
  }

  public static Insert into(String table) {
    return new Insert(table);
  }

  public Insert value(String colName, Object value) {
    int index = values.size() + 1;
    ParamValue val; // so we never forget a if condition

    if (value instanceof String) {
      val = new ParamString(index, value);
    } else {
      val = new ParamObject(index, value);
    }

    values.put(colName, val);
    return this;
  }

  public Insert onGeneratedKey(GeneratedKeyCallback callback) {
    this.keyCallback = callback;
    return this;
  }

  public void prepare(Stmt stmt) {
    Collection<ParamValue> vals = values.values();
    List<ParamValue> params = ImmutableList.copyOf(vals);

    for (ParamValue param : params) {
      param.set(stmt);
    }
  }

  GeneratedKeyCallback getKeyCallback() {
    return keyCallback;
  }

  @Override
  public String toString() {
    Set<String> keys = values.keySet();
    String columns = Joiner.on(", ").join(keys);

    Object[] argArr = new Object[keys.size()];
    Arrays.fill(argArr, "?");
    String args = Joiner.on(", ").join(argArr);

    return String.format("INSERT INTO %s (%s) VALUES (%s)", table, columns, args);
  }

}