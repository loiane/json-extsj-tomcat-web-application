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
package org.tec.webapp.jdbc.entity.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;
import org.tec.security.Checksum;
import org.tec.webapp.bean.UserBean;
import org.tec.webapp.jdbc.bean.User;
import org.tec.webapp.jdbc.entity.BaseEntity;
import org.tec.webapp.jdbc.entity.UserDba;
import org.tec.webapp.jdbc.entity.support.ParameterMap;
import org.tec.webapp.jdbc.entity.support.PreparedStatementBuilder;

/**
 * the user entity jdbc access impl
 */
@Component()
public class UserDbaImpl extends BaseEntity implements UserDba
{
  /** Table name */
  protected static final String TABLE = "users";

  /** the column user_name */
  protected static final String COLUMN_USER_NAME = "user_name";

  /** the column user_id */
  protected static final String COLUMN_USER_ID = "user_id";

  /** the column password */
  protected static final String COLUMN_PASSWORD = "password";

  /** the column enabled */
  protected static final String COLUMN_ENABLED = "enabled";

  /** the column email */
  protected static final String COLUMN_EMAIL = "email";

  /** {@inheritDoc} */
  public Long insert(UserBean user) throws DataAccessException
  {
    ParameterMap params = getParams(user);

    PreparedStatementCreator creator = PreparedStatementBuilder
            .getInsertBuilder(TABLE, params, Statement.RETURN_GENERATED_KEYS);

    return insert(creator);
  }

  /** {@inheritDoc} */
  public void update(UserBean user)
  {
    ParameterMap params = getParams(user);

    ParameterMap whereParams = new ParameterMap();

    whereParams.put(COLUMN_USER_ID, user.getUserId());

    PreparedStatementCreator creator = PreparedStatementBuilder
            .getUpdateBuilder(TABLE, params, whereParams);

    update(creator);
  }

  /**
   * {@inheritDoc} This function is needed because we need to exclude the
   * password from the standard user operations.
   */
  public void updatePassword(UserBean user)
  {
    ParameterMap params = new ParameterMap();

    //Assumes md5 calc
    params.put(COLUMN_PASSWORD, user.getPassword(), Types.VARCHAR);

    ParameterMap whereParams = new ParameterMap();

    whereParams.put(COLUMN_USER_ID, user.getUserId());

    PreparedStatementCreator creator = PreparedStatementBuilder
            .getUpdateBuilder(TABLE, params, whereParams);

    update(creator);
  }

  /** {@inheritDoc} */
  public void delete(UserBean user)
  {
    ParameterMap whereParams = new ParameterMap();

    whereParams.put(COLUMN_USER_ID, user.getUserId());

    PreparedStatementCreator creator = PreparedStatementBuilder
        .getDeleteBuilder(TABLE, whereParams);

    delete(creator);
  }

  /** {@inheritDoc} */
  public UserBean getUser(String userName)
  {
    ParameterMap whereParams = new ParameterMap();

    whereParams.put(COLUMN_USER_NAME, userName);

    PreparedStatementCreator creator = PreparedStatementBuilder
            .getSelectBuilder(TABLE, WILDCARD_COLUMN, whereParams);

    List<UserBean> l = (List<UserBean>) query(creator);


    return (l != null) ? l.get(0) : null;
  }

  /** {@inheritDoc} */
  public List<UserBean> getOtherUsers(String userName)
  {
    ParameterMap whereParams = new ParameterMap();

    whereParams.put(COLUMN_USER_NAME, userName);

    StringBuilder buff = new StringBuilder();

    buff.append(COLUMN_USER_NAME);
    buff.append(" <> ?");

    PreparedStatementCreator creator = PreparedStatementBuilder
            .getSelectBuilder(TABLE, WILDCARD_COLUMN, whereParams, buff.toString());

    return (List<UserBean>) query(creator);
  }

  /** {@inheritDoc} */
  @Override()
  protected UserBean process(ResultSet rs) throws SQLException
  {
    UserBean user = new User();

    user.setUserId(rs.getInt(COLUMN_USER_ID));
    user.setUserName(rs.getString(COLUMN_USER_NAME));
    //TODO this shouldn't make it passed the server
    user.setPassword(rs.getString(COLUMN_PASSWORD));
    user.setEnabled(rs.getBoolean(COLUMN_ENABLED));
    user.setEmail(rs.getString(COLUMN_EMAIL));

    return user;
  }

  /** {@inheritDoc} */
  @Override()
  protected ParameterMap getParams(Object bean)
  {
    UserBean user = (UserBean) bean;
    ParameterMap params = new ParameterMap();

    if (user.getUserId() > 0)
    {
      params.put(COLUMN_USER_ID, user.getUserId());
    }
    params.put(COLUMN_EMAIL, user.getEmail());
    params.put(COLUMN_USER_NAME, user.getUserName());
    params.put(COLUMN_ENABLED, user.getEnabled());

    //set init pwd to md5 of user name
    //don't set on update, use explicit pwd update function
    if (user.getUserId() == 0)
    {
      params.put(COLUMN_PASSWORD, Checksum.getMD5(user.getUserName()));
    }

    return params;
  }
}
