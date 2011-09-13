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
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.testng.Assert.assertFalse;

import java.sql.SQLException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import br.com.objectos.comuns.relational.RelationalException;
import br.com.objectos.comuns.relational.jdbc.Transactions.AtomicInsertOperation;
import br.com.objectos.comuns.testing.dbunit.DBUnit;

import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
@Guice(modules = RelationalJdbcTestModule.class)
public class AtomicInsertTest {

  @Inject
  private AtomicInsert atomicInsert;

  @Inject
  private DBUnit dbunit;

  @Inject
  private FindSimple findSimple;

  @Inject
  private FindOther findOther;

  @BeforeClass
  public void setup_dbunit() {
    dbunit.loadDefaultDataSet();
  }

  public void cascaded_insert_shoud_work_as_expected() {
    final String val = "CASC";

    atomicInsert.of(new AtomicInsertOperation() {
      @Override
      public void execute(Insertion insertion) throws SQLException {
        Simple simple = new Simple(val);
        insertion.of(simple);

        Other other = new Other(simple, "NEW");
        insertion.of(other);
      }
    });

    Simple simple = findSimple.byString(val);
    assertThat(simple, notNullValue());

    Other other = findOther.bySimple(simple);
    assertThat(other.getSimpleId(), equalTo(simple.getId()));
  }

  public void when_last_fails_first_should_be_rolledbacked() {
    final String firstVal = "FIRST";
    final String secVal = "SEC";

    atomicInsert.of(new AtomicInsertOperation() {
      @Override
      public void execute(Insertion insertion) throws SQLException {
        Simple first = new Simple(firstVal);
        insertion.of(first);

        Other other = new Other(first, "REP");
        insertion.of(other);
      }
    });

    try {
      atomicInsert.of(new AtomicInsertOperation() {
        @Override
        public void execute(Insertion insertion) throws SQLException {
          Simple second = new Simple(secVal);
          insertion.of(second);

          // repeated unique value: should fail
          Other other = new Other(second, "REP");
          insertion.of(other);
        }
      });

      assertFalse(true);
    } catch (RelationalException e) {
    }

    Simple first = findSimple.byString(firstVal);
    Simple second = findSimple.byString(secVal);

    assertThat(first, notNullValue());
    assertThat(second, nullValue());

    Other firstOther = findOther.bySimple(first);
    assertThat(firstOther.getSimpleId(), equalTo(first.getId()));
  }

}