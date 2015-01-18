/*******************************************************************************
 * Copyright 2014 org.tec
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.tec.webapp.jdbc.entity.support;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;

/**
 * prepared statement callback for inserts that return identity assumes flag
 * Statement.RETURN_GENERATED_KEYS is set when creating statement
 */
public class IdentifierCallback implements PreparedStatementCallback<Long>
{

  /** {@inheritDoc} */
  @Override()
  public Long doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException
  {
    ResultSet generatedKeys = null;
    try
    {
      ps.execute();

      generatedKeys = ps.getGeneratedKeys();

      if (generatedKeys.next())
      {
        return Long.valueOf(generatedKeys.getLong(1));
      }
      else
      {
        throw new SQLException("Unable to insert new record " + ps.toString());
      }
    }
    finally
    {
      if (generatedKeys != null)
      {
        generatedKeys.close();
      }
    }
  }
}
