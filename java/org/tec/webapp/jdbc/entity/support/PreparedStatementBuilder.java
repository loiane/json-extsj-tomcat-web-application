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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

/**
 * core class for building prepared statements
 */
public class PreparedStatementBuilder implements PreparedStatementCreator, SqlProvider
{

  /** the logger */
  protected Log mLogger = LogFactory.getLog(this.getClass());

  /**
   * these are to filter funky chars that can cause issues down stream
   */
  private static final String[] INVALID_TEXT_CHARS = new String[]
  {"\u2018", "\u2019", "\u201A", "\u201B", "\u275B", "\u275C", "\u201C", "\u201D", "\u201E", "\u201F", "\u301D", "\u301E", "\u301F", "\u275D", "\u275E", "\uFF02"};

  /**
   * replace funky chars that can cause issues down stream with the below
   */
  private static final String[] CORRECT_TEXT_CHARS = new String[]
  {"'", "'", "'", "'", "'", "'", "\"", "\"", "\"", "\"", "\"", "\"", "\"", "\"", "\"", "\""};

  /** delete statement prefix */
  protected static final String DELETE_FROM_PREFIX = "DELETE FROM ";

  /** the sql string */
  protected String mSql;

  /** the SQL parameter map Insert/Update params */
  protected ParameterMap mParams;

  /** the SQL where parameter map */
  protected ParameterMap mWhereParams;

  /** the SQL flags */
  protected int mFlags = 0;

  /**
   * ctor
   * @param sql the string sql statement
   * @param whereParams the map of where parameters
   */
  public PreparedStatementBuilder(String sql, ParameterMap whereParams)
  {
    this(sql, null, whereParams, 0);
  }

  /**
   * ctor
   * @param sql the string sql statement
   * @param params the map of insert/update parameters
   * @param whereParams the map of where parameters
   * @param flags the Sql flag 0 == none
   */
  public PreparedStatementBuilder(String sql, ParameterMap params, ParameterMap whereParams, int flags)
  {
    this.mSql = sql;
    this.mParams = params;
    this.mWhereParams = whereParams;
    this.mFlags = flags;
  }

  /** {@inheritDoc} */
  @Override()
  public PreparedStatement createPreparedStatement(Connection con) throws SQLException
  {
    PreparedStatement stmt;

    if (mLogger.isDebugEnabled())
    {
      mLogger.debug("SQL: " + mSql);
    }

    if (0 != mFlags)
    {
      stmt = con.prepareStatement(mSql, mFlags);
    }
    else
    {
      stmt = con.prepareStatement(mSql);
    }

    int i = 1;

    // set insert/update parameters
    if (mParams != null)
    {
      for (Parameter value : mParams.values())
      {
        setParameter(stmt, i, value);
        ++i;
      }
    }

    // set where parameters
    if (mWhereParams != null)
    {
      for (Parameter value : mWhereParams.values())
      {
        setParameter(stmt, i, value);
        ++i;
      }
    }

    return stmt;
  }

  /**
   * Set a prepared statement field based on type
   *
   * @param stmt PrepapredStatement
   * @param index index of the parameter in the stmt
   * @param param the param value and metadata
   */
  protected void setParameter(PreparedStatement stmt, int index, Parameter param)
  {
    try
    {
      if (param.getData() == null)
      {
        stmt.setNull(index, param.getType().getVendorTypeNumber());
      }
      else
      {
        switch (param.getType())
        {
          case BOOLEAN :
            stmt.setBoolean(index, (Boolean) param.getData());
            break;
          case DATE :
            /*
             * java.sql.Date date = TextHelper.parseDate(param.getData()); if
             * (null == date) { throw new
             * SQLException("failed to set parameter: stmt=" + stmt + " index="
             * + index + " param=" + param); } stmt.setDate(index, date);
             */
            break;
          case TIME :
            /*
             * Time time = TextHelper.parseTime(param.getData()); if (null ==
             * time) { throw new SQLException("failed to set parameter: stmt=" +
             * stmt + " index=" + index + " param=" + param); }
             * stmt.setTime(index, time);
             */
            break;
          case TIMESTAMP :
            /*
             * Timestamp ts = TextHelper.parseTimestamp(param.getData()); if
             * (null == ts) { throw new
             * SQLException("failed to set parameter: stmt=" + stmt + " index="
             * + index + " param=" + param); } stmt.setTimestamp(index, ts);
             */
            break;
          case INTEGER :
            if (param.getData() instanceof Long)
            {
              Long l = (Long) param.getData();
              stmt.setLong(index, l);
            }
            else
            {
              Integer i = (Integer) param.getData();
              stmt.setInt(index, i);
            }
            break;
          case FLOAT :
            Float f = (Float) param.getData();
            stmt.setFloat(index, f);
            break;
          default : // set string for non explicit types
            String tmp = StringUtils.replaceEachRepeatedly((String) param.getData(), INVALID_TEXT_CHARS, CORRECT_TEXT_CHARS);
            stmt.setString(index, tmp);
            break;
        }
      }
    }
    catch (Throwable e)
    {
      throw new RuntimeException("failed to process parameter " + param, e);
    }
  }

  /** {@inheritDoc} */
  @Override()
  public String getSql()
  {
    return mSql;
  }

  /**
   * Build INSERT for PreparedStatementBuilder based on data provided
   *
   * @param tablename String the table to insert into
   * @param params the map of parameters
   * @param flags the Sql flag 0 == none
   *
   * @return PreparedStatementBuilder to insert
   */
  public static PreparedStatementBuilder getInsertBuilder(String tablename, ParameterMap params, int flags)
  {
    if (0 == params.size())
    {
      throw new RuntimeException("No parameters provided to insert. " + tablename);
    }

    StringBuilder buf = new StringBuilder();
    buf.append("INSERT INTO ");
    buf.append(tablename);
    buf.append('(');

    // Build both column names and ?s
    StringBuilder paramQs = new StringBuilder();

    for (Iterator<String> itCol = params.keySet().iterator(); itCol.hasNext();)
    {
      buf.append(itCol.next());
      paramQs.append('?');
      if (itCol.hasNext())
      {
        buf.append(',');
        paramQs.append(',');
      }
    }

    buf.append(") VALUES (");
    buf.append(paramQs);
    buf.append(')');

    return new PreparedStatementBuilder(buf.toString(), params, null, flags);
  }

  /**
   * Build prepared statement will build where with all columns anded
   *
   * @param tablename the table to update
   * @param params the map of update params
   * @param whereParams the map of where params
   * @return PreparedStatementBuilder to update
   */
  public static PreparedStatementBuilder getUpdateBuilder(String tablename, ParameterMap params, ParameterMap whereParams)
  {
    // Nothing to update
    if (0 == params.size())
    {
      throw new RuntimeException("No parameters provided to update. " + tablename);
    }

    StringBuilder whereBuf = new StringBuilder();
    if (whereParams != null && whereParams.size() > 0)
    {
      for (Iterator<String> itCol = whereParams.keySet().iterator(); itCol.hasNext();)
      {
        String key = itCol.next();
        whereBuf.append(key);
        whereBuf.append("=?");
        if (itCol.hasNext())
        {
          whereBuf.append(" AND ");
        }
      }
    }

    return getUpdateBuilder(tablename, params, whereParams, whereBuf.toString());
  }

  /**
   * Build prepared statement based on
   *
   * @param tablename the table to update
   * @param params the map of update params
   * @param whereParams the map of where params
   * @param whereClause the string where clause
   * @return PreparedStatementBuilder to update
   */
  public static PreparedStatementBuilder getUpdateBuilder(String tablename, ParameterMap params, ParameterMap whereParams, String whereClause)
  {
    // Nothing to update
    if (0 == params.size())
    {
      throw new RuntimeException("No parameters provided to update. " + tablename);
    }

    // Build the actual prepared statement
    StringBuilder buf = new StringBuilder();
    buf.append("UPDATE ");
    buf.append(tablename);
    buf.append(" SET ");

    // Build both column names and ?s
    for (Iterator<String> itCol = params.keySet().iterator(); itCol.hasNext();)
    {
      String key = itCol.next();
      buf.append(key);
      buf.append("=?");
      if (itCol.hasNext())
      {
        buf.append(',');
      }
    }

    if (whereClause != null && whereClause.length() > 0)
    {
      buf.append(" WHERE ").append(whereClause);
    }

    return new PreparedStatementBuilder(buf.toString(), params, whereParams, 0);
  }

  /**
   * Build prepared delete statement
   *
   * @param tablename the table to update
   * @param whereParams the map of where params
   * @return PreparedStatementBuilder to update
   */
  public static PreparedStatementBuilder getDeleteBuilder(String tablename, ParameterMap whereParams)
  {
    StringBuilder whereBuf = new StringBuilder();
    if (whereParams != null && whereParams.size() > 0)
    {
      for (Iterator<String> itCol = whereParams.keySet().iterator(); itCol.hasNext();)
      {
        String key = itCol.next();
        whereBuf.append(key);
        whereBuf.append("=?");
        if (itCol.hasNext())
        {
          whereBuf.append(" AND ");
        }
      }
    }

    return getDeleteBuilder(tablename, whereParams, whereBuf.toString());
  }

  /**
   * Build delete prepared statement based on
   *
   * @param tablename the table to update
   * @param whereParams the map of where params
   * @param whereClause the string where clause
   * @return PreparedStatementBuilder to update
   */
  public static PreparedStatementBuilder getDeleteBuilder(String tablename, ParameterMap whereParams, String whereClause)
  {

    // Build the actual prepared statement
    StringBuilder buf = new StringBuilder();
    buf.append("DELETE FROM ");
    buf.append(tablename);

    if (whereClause != null && whereClause.length() > 0)
    {
      buf.append(" WHERE ").append(whereClause);
    }

    return new PreparedStatementBuilder(buf.toString(), null, whereParams, 0);
  }

  /**
   * Build prepared statement for Select (and for where clause)
   *
   * @param tablename the table to select from
   * @param columns the set of column names
   * @param whereParams the map of where params
   * @return PreparedStatementBuilder to select
   */
  public static PreparedStatementBuilder getSelectBuilder(String tablename, Set<String> columns, ParameterMap whereParams)
  {
    // Nothing to update
    if (0 == columns.size())
    {
      throw new RuntimeException("No parameters provided to update. " + tablename);
    }

    StringBuilder whereBuf = new StringBuilder();
    if (whereParams != null && whereParams.size() > 0)
    {
      for (Iterator<String> itCol = whereParams.keySet().iterator(); itCol.hasNext();)
      {
        String key = itCol.next();
        whereBuf.append(key);
        whereBuf.append("=?");
        if (itCol.hasNext())
        {
          whereBuf.append(" AND ");
        }
      }
    }

    return getSelectBuilder(tablename, columns, whereParams, whereBuf.toString());
  }

  /**
   * Build prepared statement for Select
   *
   * @param tablename the table to select from
   * @param columns the set of column names
   * @param whereParams the map of where params
   * @param whereClause the string where clause
   * @return PreparedStatementBuilder to select
   */
  public static PreparedStatementBuilder getSelectBuilder(String tablename, Set<String> columns, ParameterMap whereParams, String whereClause)
  {
    // Nothing to update
    if (0 == columns.size())
    {
      throw new RuntimeException("No columns provided to select. " + tablename);
    }

    // Build the actual prepared statement
    StringBuilder buf = new StringBuilder();
    buf.append("SELECT ");

    for (Iterator<String> itCol = columns.iterator(); itCol.hasNext();)
    {
      buf.append(itCol.next());
      if (itCol.hasNext())
      {
        buf.append(',');
      }
    }

    buf.append(" FROM ");
    buf.append(tablename);

    if (whereClause != null && whereClause.length() > 0)
    {
      buf.append(" WHERE ").append(whereClause);
    }

    return new PreparedStatementBuilder(buf.toString(), null, whereParams, 0);
  }
}