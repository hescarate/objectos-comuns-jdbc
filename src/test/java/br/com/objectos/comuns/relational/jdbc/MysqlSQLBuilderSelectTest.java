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

import static com.google.common.collect.Lists.transform;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.common.base.Functions;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = RelationalJdbcTestModule.class)
public class MysqlSQLBuilderSelectTest {

  @Inject
  private DBUnit dbunit;

  @Inject
  private Provider<Sql> sqlProvider;

  private final ResultSetLoader<Simple> entityLoader = new SimpleEntityLoader();

  @BeforeMethod
  public void reset() {
    dbunit.load(new MiniComunsJdbcTruncateXml());
    dbunit.load(new MiniComunsJdbcXml());
  }

  public void select_columns_should_return_records() {
    Sql sql = sqlProvider.get();
    sql.select("*").from("COMUNS_RELATIONAL.SIMPLE");
    sql.order("ID").ascending();

    List<Simple> result = sql.list(entityLoader);

    assertThat(result.size(), equalTo(3));

    List<String> strings = transform(result, Functions.toStringFunction());
    assertThat(strings.get(0), equalTo("Simple{id=1, string=CDE}"));
    assertThat(strings.get(1), equalTo("Simple{id=2, string=BCD}"));
    assertThat(strings.get(2), equalTo("Simple{id=3, string=ABC}"));
  }

  public void select_with_explicit_column_names_should_be_ok() {
    Sql sql = sqlProvider.get();
    sql.select("ID", "STRING").from("COMUNS_RELATIONAL.SIMPLE");
    sql.order("ID").ascending();

    List<Simple> result = sql.list(entityLoader);

    assertThat(result.size(), equalTo(3));

    List<String> strings = transform(result, Functions.toStringFunction());
    assertThat(strings.get(0), equalTo("Simple{id=1, string=CDE}"));
    assertThat(strings.get(1), equalTo("Simple{id=2, string=BCD}"));
    assertThat(strings.get(2), equalTo("Simple{id=3, string=ABC}"));
  }

  public void ordering_ascending_should_be_ok() {
    Sql sql = sqlProvider.get();
    sql.select("ID", "STRING").from("COMUNS_RELATIONAL.SIMPLE");
    sql.order("STRING").ascending();

    List<Simple> result = sql.list(entityLoader);

    assertThat(result.size(), equalTo(3));

    List<String> strings = transform(result, Functions.toStringFunction());
    assertThat(strings.get(0), equalTo("Simple{id=3, string=ABC}"));
    assertThat(strings.get(1), equalTo("Simple{id=2, string=BCD}"));
    assertThat(strings.get(2), equalTo("Simple{id=1, string=CDE}"));
  }

  public void ordering_descending_should_be_ok() {
    Sql sql = sqlProvider.get();
    sql.select("ID", "STRING").from("COMUNS_RELATIONAL.SIMPLE");
    sql.order("STRING").descending();

    List<Simple> result = sql.list(entityLoader);

    assertThat(result.size(), equalTo(3));

    List<String> strings = transform(result, Functions.toStringFunction());
    assertThat(strings.get(0), equalTo("Simple{id=1, string=CDE}"));
    assertThat(strings.get(1), equalTo("Simple{id=2, string=BCD}"));
    assertThat(strings.get(2), equalTo("Simple{id=3, string=ABC}"));
  }

  public void sum_aggregate_should_be_ok() {
    Sql sql = sqlProvider.get();
    sql.select("sum(ID)").from("COMUNS_RELATIONAL.SIMPLE");

    Long result = sql.single(new LongLoader());

    assertThat(result, equalTo(Long.valueOf(1 + 2 + 3)));
  }

}