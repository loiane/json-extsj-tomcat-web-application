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
package org.tec.webapp.jdbc.bean;

import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.tec.webapp.bean.UserBean;
import org.tec.webapp.bean.RoleType;
import org.tec.webapp.bean.UserRoleBean;

/**
 * user role jdbc class
 */
public class UserRole implements UserRoleBean
{
  /** serial guid */
  private static final long serialVersionUID = 1L;

  /** the user role surrogate key */
  protected long mUserRoleId;

  /** the user object tied to this record */
  protected UserBean mUser;

  /** the user role type assignment */
  protected RoleType mRole;

  /** {@inheritDoc} */
  @Override()
  public long getUserRoleId()
  {
    return mUserRoleId;
  }

  /** {@inheritDoc} */
  @Override()
  public void setUserRoleId(long userRoleId)
  {
    mUserRoleId = userRoleId;
  }

  /** {@inheritDoc} */
  @Override()
  public UserBean getUser()
  {
    return mUser;
  }

  /** {@inheritDoc} */
  @Override()
  public void setUser(UserBean user)
  {
    mUser = user;
  }

  /** {@inheritDoc} */
  @Override()
  public RoleType getRole()
  {
    return mRole;
  }

  /** {@inheritDoc} */
  @Override()
  public void setRole(RoleType role)
  {
    mRole = role;
  }

  /** {@inheritDoc} */
  @Override()
  public String toJSON()
  {
    return JSONObject.fromObject(this).toString();
  }

  /** {@inheritDoc} */
  @Override()
  public String toString()
  {
    return new ToStringBuilder(this).appendSuper(super.toString())
        .append("mUser", mUser)
        .append("mRole", mRole).toString();
  }
}
