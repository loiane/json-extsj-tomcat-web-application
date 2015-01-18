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
package org.tec.webapp.orm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.tec.webapp.bean.UserBean;
import org.tec.webapp.bean.RoleType;
import org.tec.webapp.bean.UserRoleBean;

/**
 * user role class
 */
@Entity()
@Table(name = "user_role", catalog = "webapp")
public class UserRole implements Serializable, UserRoleBean
{
  /** serial guid */
  private static final long serialVersionUID = 1L;

  /** the json config to filter out password when sending data to client */
  protected static final JsonConfig JSON_CONFIG = new JsonConfig();

  /** the user role surrogate key */
  @Id()
  @Column(name = "user_role_id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected int mUserRoleId;

  /** the user object tied to this record */
  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  protected UserBean mUser;

  /** the user role type assignment */
  @Column(name = "role", length = 16, nullable = false, updatable = false)
  @Enumerated(EnumType.STRING)
  protected RoleType mRole;

  /**
   * {@inheritDoc}
   */
  @Override()
  public int getUserRoleId()
  {
    return mUserRoleId;
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  public void setUserRoleId(int userRoleId)
  {
    mUserRoleId = userRoleId;
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  public UserBean getUser()
  {
    return mUser;
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  public void setUser(UserBean user)
  {
    mUser = user;
  }

  /**
   * @return the authority
   */
  @Override()
  public RoleType getRole()
  {
    return mRole;
  }

  /**
   * @param role the authority to set
   */
  @Override()
  public void setRole(RoleType role)
  {
    mRole = role;
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  public String toJSON()
  {
    return JSONObject.fromObject(this, JSON_CONFIG).toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  public String toString()
  {
    return new ToStringBuilder(this).appendSuper(super.toString())
        .append("mUser", mUser)
        .append("mRole", mRole).toString();
  }
}
