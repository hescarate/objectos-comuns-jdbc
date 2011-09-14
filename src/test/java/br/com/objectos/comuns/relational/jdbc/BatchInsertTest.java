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
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.relational.BatchInsert;
import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = { RelationalJdbcTestModule.class })
public class BatchInsertTest {
  
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
  
  public void simple_entity_should_be_inserted_correctly() {
    Simple s0 = new Simple("A");
    Simple s1 = new Simple("B");
    Simple s2 = new Simple("C");
    
    List<Simple> entities = ImmutableList.of(s0, s1, s2);
    
    batchInsert.allOf(entities);
    
    List<Simple> r = findAll();
    assertThat(r.size(), equalTo(3));// 3
    assertThat(r.get(0).getString(), equalTo("A"));
    assertThat(r.get(1).getString(), equalTo("B"));
    assertThat(r.get(2).getString(), equalTo("C"));
  }
  
  public void generated_key_callback_should_properly_populate_id() {
    Simple s0 = new Simple("A");
    Simple s1 = new Simple("B");
    
    List<Simple> entities = ImmutableList.of(s0, s1);
    List<Integer> ids = transform(entities, new ToId());
    assertThat(ids.toString(), equalTo("[null, null]"));
    
    batchInsert.allOf(entities);
    
    ids = transform(entities, new ToId());
    assertThat(ids.get(0), notNullValue());
    assertThat(ids.get(1), notNullValue());
  }
  
  public void single_insert_should_work_properly() {
    Simple s0 = new Simple("A");
    
    batchInsert.of(s0);
    
    List<Simple> r = findAll();
    assertThat(r.size(), equalTo(1));
    assertThat(r.get(0).getString(), equalTo("A"));
  }
  
  private List<Simple> findAll() {
    MysqlSQLBuilder sql = new MysqlSQLBuilder();
    sql.select("*").from("COMUNS_RELATIONAL.SIMPLE");
    sql.order("ID").ascending();
    
    ResultSetLoader<Simple> entityLoader = new SimpleEntityLoader();
    return exec.list(entityLoader, sql);
  }
  
  private class ToId implements Function<Simple, Integer> {
    @Override
    public Integer apply(Simple input) {
      return input.getId();
    }
  }
  
}