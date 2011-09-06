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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class Closer {

  private static final Logger logger = LoggerFactory.getLogger(Closer.class);

  private Closer() {
  }

  public static void close(Connection closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (SQLException e) {
      logger.error("Could not properly close a Connection", e);
    }
  }

  public static void close(PreparedStatement closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (SQLException e) {
      logger.error("Could not properly close a PreparedStatement", e);
    }
  }

  public static void close(ResultSet closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (SQLException e) {
      logger.error("Could not properly close a ResultSet", e);
    }
  }

}