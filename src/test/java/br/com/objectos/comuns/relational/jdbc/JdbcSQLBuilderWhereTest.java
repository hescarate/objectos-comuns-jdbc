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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = RelationalJdbcTestModule.class)
public class JdbcSQLBuilderWhereTest {

  @Inject
  private JdbcSQLBuilderExec exec;

  @Inject
  private DBUnit dbunit;

  private final ResultSetLoader<Simple> entityLoader = new SimpleEntityLoader();

  @BeforeMethod
  public void reset() {
    dbunit.load(new MiniComunsJdbcTruncateXml());
    dbunit.load(new MiniComunsJdbcXml());
  }

  public void where_equal_should_work_properly() {
    JdbcSQLBuilder sql = new JdbcSQLBuilder();
    sql.select("*").from("COMUNS_RELATIONAL.SIMPLE");
    sql.where("STRING").equalTo("C");

    List<Simple> result = exec.list(entityLoader, sql);

    assertThat(result.size(), equalTo(1));
    assertThat(result.get(0).toString(), equalTo("Simple{id=1, string=C}"));
  }

}