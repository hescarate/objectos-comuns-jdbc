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

import java.util.List;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.relational.BatchInsert;

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

  public void simple_entity_should_be_inserted_correctly() {
    Simple s0 = new Simple("A");
    Simple s1 = new Simple("B");
    Simple s2 = new Simple("C");

    List<Simple> entities = ImmutableList.of(s0, s1, s2);

    batchInsert.allOf(entities);
  }

}