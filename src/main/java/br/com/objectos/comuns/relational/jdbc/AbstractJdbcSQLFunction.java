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

import br.com.objectos.comuns.relational.search.Element;
import br.com.objectos.comuns.relational.search.HasElements;
import br.com.objectos.comuns.relational.search.HasSQLFunctions;
import br.com.objectos.comuns.relational.search.Join;
import br.com.objectos.comuns.relational.search.OrderProperty;
import br.com.objectos.comuns.relational.search.Where;
import br.com.objectos.comuns.relational.search.WhereProperty;
import br.com.objectos.comuns.relational.search.WhereThis;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
abstract class AbstractJdbcSQLFunction implements HasSQLFunctions, HasElements {

  private final Multimap<Class<?>, Element> elements = LinkedHashMultimap.create();

  @Override
  public void clearOrders() {
    elements.removeAll(OrderProperty.class);
  }

  @Override
  public final Join join(String relationship, String alias) {
    // Join join = new HJoin(JoinType.INNER_JOIN, relationship, alias);
    // elements.put(Join.class, join);
    // return join;
    throw new UnsupportedOperationException();
  }

  @Override
  public final Join leftJoin(String relationship, String alias) {
    // Join join = new HJoin(JoinType.LEFT_OUTER_JOIN, relationship, alias);
    // elements.put(Join.class, join);
    // return join;
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<Element> getElements(Class<?> keyClass) {
    return elements.get(keyClass);
  }

  @Override
  public final WhereThis whereThis() {
    throw new UnsupportedOperationException(
        "Plain Old SQL queries do not support WhereThis conditions");
  }

  @Override
  public final WhereProperty where(String property) {
    WhereProperty where = new JdbcWhereProperty(property);
    elements.put(Where.class, where);
    return where;
  }

  @Override
  public final OrderProperty order(String property) {
    JdbcOrderProperty order = new JdbcOrderProperty(property);
    elements.put(OrderProperty.class, order);
    return order;
  }

  protected boolean containsKey(Class<?> key) {
    return elements.containsKey(key);
  }

  protected void putElement(Class<?> key, Element element) {
    elements.put(key, element);
  }

}