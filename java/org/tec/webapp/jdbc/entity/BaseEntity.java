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
package org.tec.webapp.jdbc.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.tec.webapp.jdbc.entity.support.IdentifierCallback;
import org.tec.webapp.jdbc.entity.support.ParameterMap;

/**
 * base entity class to hold common code JdbcDaoSupport
 */
public abstract class BaseEntity
{
  /** the logger */
  protected Log mLogger = LogFactory.getLog(this.getClass());

  /** used for building wildcard select */
  protected static final Set<String> WILDCARD_COLUMN = new HashSet<String>(Arrays.asList("*"));

  /** generic id column name */
  protected static final String ID_COLUMN = "id";

  /** resultset row mapper */
  protected RowMapper mMapper = new RowMapper()
  {
    /** {@inheritDoc} */
    @Override()
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException
    {
      return process(rs);
    }
  };

  /** resultset extractor */
  protected ResultSetExtractor mExtractor = new ResultSetExtractor()
  {
    /** {@inheritDoc} */
    @Override()
    public Object extractData(ResultSet rs) throws SQLException
    {
      List l = new ArrayList();

      while (rs.next())
      {
        l.add(process(rs));
      }

      if (l.size() > 0)
      {
        return l;
      }
      else
      {
        return null;
      }
    }
  };

  /** jdbc template for dba operations */
  @Autowired()
  protected JdbcTemplate mJdbcTemplate;

  /**
   * convert a resultset to a bean
   * @param rs the resultset to convert
   * @return the populated bean
   * @throws SQLException if processing fails
   */
  protected abstract Object process(ResultSet rs) throws SQLException;

  /**
   * Populate ParamsTypes for insert or update.
   * @param bean UserApiKey
   * @return the tree map of ParamType
   */
  protected abstract ParameterMap getParams(Object bean);

  /**
   * execute update statement
   * @param creator the prepared statement creator
   * @return rows effected
   * @throws DataAccessException if processing fails
   */
  protected int update(PreparedStatementCreator creator) throws DataAccessException
  {
    return mJdbcTemplate.update(creator);
  }

  /**
   * execute delete statement
   * @param creator the prepared statement creator
   * @return rows effected
   * @throws DataAccessException if processing fails
   */
  protected int delete(PreparedStatementCreator creator) throws DataAccessException
  {
    return mJdbcTemplate.update(creator);
  }

  /**
   * execute insert statement Assumes Statement.RETURN_GENERATED_KEYS is set on
   * statement
   * @param creator the prepared statement creator
   * @return the identifier for the new record
   * @throws DataAccessException if processing fails
   */
  protected Long insert(PreparedStatementCreator creator) throws DataAccessException
  {
    return mJdbcTemplate.execute(creator, new IdentifierCallback());
  }

  /**
   * execute a sql query
   * @param creator the prepared statement creator
   * @return a bean instance returned from @see process
   * @throws DataAccessException if processing fails
   */
  protected Object query(PreparedStatementCreator creator) throws DataAccessException
  {
    return mJdbcTemplate.query(creator, mExtractor);
  }
}
