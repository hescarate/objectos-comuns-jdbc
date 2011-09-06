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

import br.com.objectos.comuns.relational.search.OrderProperty;

import com.google.common.base.Preconditions;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class JdbcOrderProperty extends AbstractJdbcElement implements OrderProperty {

  private final String path;

  private boolean ascending = true;

  public JdbcOrderProperty(String path) {
    this.path = Preconditions.checkNotNull(path);
  }

  @Override
  protected void validateState() {
  }

  @Override
  public void ascending() {
    ascending = true;
  }

  @Override
  public void ascending(boolean onOrOff) {
    ascending = onOrOff;
  }

  @Override
  public void descending() {
    ascending = false;
  }

  @Override
  public void descending(boolean onOrOff) {
    ascending = !onOrOff;
  }

  @Override
  public String toString() {
    String mode = ascending ? "asc" : "desc";
    return String.format("%s %s", path, mode);
  }

}