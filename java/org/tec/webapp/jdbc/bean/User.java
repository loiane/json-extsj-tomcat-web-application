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
package org.tec.webapp.jdbc.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.tec.webapp.bean.UserBean;
import org.tec.webapp.bean.UserRoleBean;
import org.tec.webapp.orm.entity.UserRole;

/**
 * user class for jdbc layer
 */
public class User implements UserBean
{

  /** serial guid */
  private static final long serialVersionUID = 1L;

  /** the anonymous user name */
  protected static final String ANON_USER_NAME = "anonymous";

  /** the anonymous user */
  public static final UserBean ANON_USER = new User();

  /** the json config to filter out password when sending data to client */
  protected static final JsonConfig JSON_CONFIG = new JsonConfig();

  /** the user surrogate key */
  protected long mUserId;

  /** the unique login user name */
  protected String mUserName = ANON_USER_NAME;

  /**
   * the password for the use given user (MD5 hashed) the column needs to be
   * excluded from the user management cycle there will be a reset path
   */
  protected String mPassword;

  /** the user email */
  protected String mEmail;

  /** whether the account is enabled */
  protected boolean mEnabled;

  /** the list of roles assigned to the user */
  protected List<UserRoleBean> mUserRoles;

  static
  {
    JSON_CONFIG.setRootClass(User.class);

    /** this is to filter the password and cyclic user reference from user role */
    JSON_CONFIG.setJsonPropertyFilter(new PropertyFilter()
    {
      public boolean apply(Object source, String name, Object value)
      {
        return (("user".equals(name) || "password".equals(name)) ? true : false);
      }
    });

    /** special processing for generic list from json */
    JSON_CONFIG.registerJsonBeanProcessor(UserRole.class, new JsonBeanProcessor()
    {
      public JSONObject processBean(Object bean, JsonConfig jsonConfig)
      {
        UserRoleBean ur = (UserRoleBean) bean;
        Map<String, String> m = new HashMap<String, String>();
        m.put("role", ur.getRole().getName());
        m.put("userRoleId", Long.toString(ur.getUserRoleId()));
        return JSONObject.fromObject(m);
      }
    });
  }

  /** {@inheritDoc} */
  @Override()
  public long getUserId()
  {
    return mUserId;
  }

  /** {@inheritDoc} */
  @Override()
  public void setUserId(long userId)
  {
    mUserId = userId;
  }

  /** {@inheritDoc} */
  @Override()
  public String getUserName()
  {
    return mUserName;
  }

  /** {@inheritDoc} */
  @Override()
  public void setUserName(String userName)
  {
    mUserName = userName;
  }

  /** {@inheritDoc} */
  @Override()
  public String getPassword()
  {
    return mPassword;
  }

  /** {@inheritDoc} */
  @Override()
  public void setPassword(String password)
  {
    mPassword = password;
  }

  /** {@inheritDoc} */
  @Override()
  public String getEmail()
  {
    return mEmail;
  }

  /** {@inheritDoc} */
  @Override()
  public void setEmail(String email)
  {
    mEmail = email;
  }

  /** {@inheritDoc} */
  @Override()
  public boolean getEnabled()
  {
    return mEnabled;
  }

  /** {@inheritDoc} */
  @Override()
  public void setEnabled(boolean enabled)
  {
    mEnabled = enabled;
  }

  /** {@inheritDoc} */
  @Override()
  public List<UserRoleBean> getUserRoles()
  {
    return mUserRoles;
  }

  /** {@inheritDoc} */
  @Override()
  public void setUserRoles(List<UserRoleBean> userRoles)
  {
    mUserRoles = userRoles;
  }

  /** {@inheritDoc} */
  @Override()
  public boolean isAnonymous()
  {
    return mUserName.equals(ANON_USER_NAME);
  }

  /** {@inheritDoc} */
  @Override()
  public String toString()
  {
    return new ToStringBuilder(this)
      .appendSuper(super.toString())
      .append("mUserId", mUserId)
      .append("mUserName", mUserName)
      .append("mEmail", mEmail)
      .append("mEnabled", mEnabled)
      .append("mUserRoles", mUserRoles).toString();
  }

  /** {@inheritDoc} */
  @Override()
  public boolean equals(Object obj)
  {
    if (obj == null)
    {
      return false;
    }
    if (obj == this)
    {
      return true;
    }
    if (!(obj instanceof User))
    {
      return false;
    }

    User that = (User) obj;
    return new EqualsBuilder()
      .append(mUserName, that.mUserName)
      .append(mEmail, that.mEmail)
      .append(mEnabled, that.mEnabled)
      .isEquals();
  }

  /** {@inheritDoc} */
  @Override()
  public int hashCode()
  {
    return new HashCodeBuilder(17, 37).
      append(mUserName).
      append(mEmail).
      toHashCode();
  }

  /** {@inheritDoc} */
  @Override()
  public String toJSON()
  {
    return JSONObject.fromObject(this, JSON_CONFIG).toString();
  }
}
