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
import static org.hamcrest.Matchers.nullValue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.relational.BatchInsert;
import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { RelationalJdbcTestModule.class })
public class BatchInsertTypeTest {

  @Inject
  private BatchInsert batchInsert;

  @Inject
  private JdbcSQLBuilderExec exec;

  @Inject
  private DBUnit dbunit;

  @BeforeMethod
  public void reset() {
    dbunit.load(new MiniComunsJdbcTruncateXml());
  }

  @DataProvider
  public Object[][] typesProvider() {
    return new Object[][] { //

        { new TypeBigDecimalDouble(BigDecimal.TEN) }, //
        { new TypeDate(new Date()) }, //
        { new TypeDateTime(new DateTime()) }, //
        { new TypeDouble(Double.valueOf(1.23d)) }, //
        { new TypeFloat(Float.valueOf(2.45f)) }, //
        { new TypeInteger(Integer.valueOf(789)) }, //
        { new TypeLocalDate(new LocalDate()) }, //
        { new TypeLong(Long.valueOf(954l)) }, //
        { new TypeStringVarchar("abc") } //

    };
  }
  @DataProvider
  public Object[][] nullProvider() {
    return new Object[][] { //

        { new TypeBigDecimalDouble() }, //
        { new TypeDate() }, //
        { new TypeDateTime() }, //
        { new TypeDouble() }, //
        { new TypeFloat() }, //
        { new TypeInteger() }, //
        { new TypeLocalDate() }, //
        { new TypeLong() }, //
        { new TypeStringVarchar() } //

    };
  }

  @SuppressWarnings("rawtypes")
  @Test(dataProvider = "typesProvider")
  public void value_should_be_properly_inserted(Type<?> instance) {
    batchInsert.of(instance);

    List<Type<?>> r = findAll(instance);
    assertThat(r.size(), equalTo(1));
    assertThat(r.get(0), equalTo((Type) instance));
  }

  @Test(dataProvider = "nullProvider")
  public void null_value_should_be_treated_correctly(Type<?> instance) {
    batchInsert.of(instance);

    List<Type<?>> r = findAll(instance);
    assertThat(r.size(), equalTo(1));
    assertThat(r.get(0).getValue(), nullValue());
  }

  private List<Type<?>> findAll(Type<?> instance) {
    MysqlSQLBuilder sql = new MysqlSQLBuilder();
    sql.select("*").from(instance.getTable());
    sql.order("ID").ascending();

    ResultSetLoader<?> loader = instance.getLoader();

    @SuppressWarnings("unchecked")
    ResultSetLoader<Type<?>> typeLoader = (ResultSetLoader<Type<?>>) loader;

    return exec.list(typeLoader, sql);
  }

}