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

import java.sql.SQLException;
import java.util.Iterator;

import br.com.objectos.comuns.relational.RelationalException;
import br.com.objectos.comuns.relational.jdbc.Transactions.AtomicInsertOperation;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.inject.Inject;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class AtomicInsertJdbc implements AtomicInsert {

  private final Transactions transactions;

  @Inject
  public AtomicInsertJdbc(Transactions transactions) {
    this.transactions = transactions;
  }

  @Override
  public void of(AtomicInsertOperation operation) {
    execute(operation);
  }

  @Override
  public void of(final Object entity) {
    AtomicInsertOperation operation = new AtomicInsertOperation() {
      @Override
      public void execute(Insertion insertion) throws SQLException {
        Preconditions.checkArgument(entity instanceof Insertable,
            "Entity must be instanceof Insertable.");
        Insertable insertable = (Insertable) entity;
        insertion.of(insertable);
      }
    };

    execute(operation);
  }

  @Override
  public void allOf(Iterable<?> entities) {
    Iterator<?> iterator = entities.iterator();
    allOf(iterator);
  }

  @Override
  public void allOf(final Iterator<?> entities) {
    AtomicInsertOperation operation = new AtomicInsertOperation() {
      @Override
      public void execute(Insertion insertion) throws SQLException {

        Iterator<Insertable> iterator = Iterators.transform(entities, new ToInsertable());

        while (iterator.hasNext()) {
          Insertable insertable = iterator.next();
          insertion.of(insertable);
        }

      }
    };

    execute(operation);
  }

  private void execute(AtomicInsertOperation operation) {
    try {
      transactions.execute(operation);
    } catch (TransactionRolledbackException e) {
      throw new RelationalException("Could not insert entity(ies). More info below.", e);
    }
  }

  private class ToInsertable implements Function<Object, Insertable> {
    @Override
    public Insertable apply(Object input) {
      Preconditions.checkArgument(input instanceof Insertable,
          "Entity must be instanceof Insertable.");
      return (Insertable) input;
    }
  }

}