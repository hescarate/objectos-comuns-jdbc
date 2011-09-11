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

import br.com.objectos.comuns.sql.PropertiesJdbcCredentialsProvider;
import br.com.objectos.comuns.testing.dbunit.DbunitModuleBuilder;

import com.google.inject.AbstractModule;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class RelationalJdbcTestModule extends AbstractModule {

  @Override
  protected void configure() {
    PropertiesJdbcCredentialsProvider credentials;
    credentials = new PropertiesJdbcCredentialsProvider(getClass());

    install(new DbunitModuleBuilder() //
        .jdbc(credentials) //
        .withMysql() //
        .build());

    install(new RelationalJdbcModuleBuilder() //
        .withC3P0(credentials) //
        .build());
  }

}