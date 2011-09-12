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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = RelationalJdbcTestModule.class)
public class MysqlSQLBuilderWhereDuoTest {

  @Inject
  private DBUnit dbunit;

  @Inject
  private Provider<Sql> sqlProvider;

  private Sql sql;
  private final ResultSetLoader<Duo> loader = new ReflectionLoader<Duo>(Duo.class);

  @BeforeMethod
  public void reset() {
    dbunit.load(new MiniComunsJdbcTruncateXml());
    dbunit.load(new MiniComunsJdbcXml());

    sql = sqlProvider.get();
    sql.select("*").from("COMUNS_RELATIONAL.DUO");
    sql.order("ID").ascending();
  }

  public void single_equal_to() {
    sql.where("TYPE").equalTo("A");

    List<Duo> result = sql.list(loader);

    assertThat(result.size(), equalTo(3));
    assertThat(result.get(0).toString(), equalTo("Duo{id=1, a=A, b=A}"));
    assertThat(result.get(1).toString(), equalTo("Duo{id=2, a=A, b=B}"));
    assertThat(result.get(2).toString(), equalTo("Duo{id=3, a=A, b=C}"));
  }

  public void multiple_equal_to() {
    sql.where("TYPE").equalTo("B");
    sql.where("KEY").equalTo("A");

    Duo single = sql.single(loader);

    assertThat(single.toString(), equalTo("Duo{id=4, a=B, b=A}"));
  }

}