/*******************************************************************************
 * Copyright 2015 org.tec
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
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;
import org.tec.webapp.bean.RoleType;
import org.tec.webapp.bean.UserRoleBean;
import org.tec.webapp.jdbc.bean.UserRole;
import org.tec.webapp.jdbc.entity.BaseEntity;
import org.tec.webapp.jdbc.entity.UserRoleDba;
import org.tec.webapp.jdbc.entity.support.ParameterMap;
import org.tec.webapp.jdbc.entity.support.PreparedStatementBuilder;


/**
 * user role dba implementation
 */
@Component()
public class UserRoleDbaImpl extends BaseEntity implements UserRoleDba
{
  /** Table name */
  protected static final String TABLE = "user_role";

  /** the column user_role_id */
  protected static final String COLUMN_USER_ROLE_ID = "user_role_id";

  /** the column user_id */
  protected static final String COLUMN_USER_ID = "user_id";

  /** the column role */
  protected static final String COLUMN_ROLE = "role";

  /** {@inheritDoc} */
  @Override()
  public Long insert(UserRoleBean userRole)
  {
    ParameterMap params = getParams(userRole);

    PreparedStatementCreator creator = PreparedStatementBuilder
            .getInsertBuilder(TABLE, params, Statement.RETURN_GENERATED_KEYS);

    return insert(creator);
  }

  /** {@inheritDoc} */
  @Override()
  public void delete(UserRoleBean userRole)
  {
    ParameterMap whereParams = new ParameterMap();

    whereParams.put(COLUMN_USER_ROLE_ID, userRole.getUserRoleId());

    PreparedStatementCreator creator = PreparedStatementBuilder
        .getDeleteBuilder(TABLE, whereParams);

    delete(creator);
  }

  /** {@inheritDoc} */
  @Override()
  public List<UserRoleBean> getRoles(Long userId)
  {
    ParameterMap whereParams = new ParameterMap();

    whereParams.put(COLUMN_USER_ID, userId);

    PreparedStatementCreator creator = PreparedStatementBuilder
            .getSelectBuilder(TABLE, WILDCARD_COLUMN, whereParams);

    return (List<UserRoleBean>) query(creator);
  }

  /** {@inheritDoc}  */
  @Override()
  protected Object process(ResultSet rs) throws SQLException
  {
    UserRoleBean userRole = new UserRole();

    userRole.setUserRoleId(rs.getInt(COLUMN_USER_ROLE_ID));
    userRole.setRole(RoleType.valueOf(rs.getString(COLUMN_ROLE)));

    return userRole;
  }

  /** {@inheritDoc} */
  @Override()
  protected ParameterMap getParams(Object bean)
  {
    UserRoleBean userRole = (UserRoleBean) bean;
    ParameterMap params = new ParameterMap();

    if (userRole.getUserRoleId() > 0)
    {
      params.put(COLUMN_USER_ROLE_ID, userRole.getUserRoleId());
    }

    if (userRole.getUser() != null)
    {
      params.put(COLUMN_USER_ID, userRole.getUser().getUserId());
    }
    else
    {
      throw new RuntimeException("User not set");
    }

    params.put(COLUMN_ROLE, userRole.getRole().getName());

    return params;
  }
}
