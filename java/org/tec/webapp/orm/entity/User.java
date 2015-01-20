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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tec.webapp.json.JSONSerializable;
import org.tec.webapp.bean.RoleType;
import org.tec.webapp.bean.UserBean;
import org.tec.webapp.bean.UserRoleBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the user entity bean
 *
 * table definition
 * my OCD doesn't like the random constraint and index naming.
 * I can also use the names for better error handling
 */
@Entity()
@Table(name = "users", catalog = "webapp",
    indexes = {
      @Index(name = "user_pwd_en_idx", unique = true, columnList = "user_name, password, enabled"),
      @Index(name = "user_idx", unique = true, columnList = "user_name,") },
    uniqueConstraints = {
      @UniqueConstraint(name = "user_name_uc", columnNames = { "user_name" }),
      @UniqueConstraint(name = "enail_uc", columnNames = { "email" }) })
public class User implements JSONSerializable, UserBean
{
  /** the anonymous user name */
  protected static final String ANON_USER_NAME = "anonymous";

  /** the anonymous user */
  public static final UserBean ANON_USER = new User();

  /** the json config to filter out password when sending data to client */
  protected static final JsonConfig JSON_CONFIG = new JsonConfig();

  /**
   * setup json config to filter password and user from json
   */
  static
  {
    ANON_USER.setUserName(ANON_USER_NAME);
    ANON_USER.setUserId(-1);

    JSON_CONFIG.setRootClass(User.class);
    /** this is to filter the password and cyclic user refernce from user role */
    JSON_CONFIG.setJsonPropertyFilter(new PropertyFilter()
    {
      public boolean apply(Object source, String name, Object value)
      {
        boolean filter = (("user".equals(name) || "password".equals(name)) ? true : false);
        return filter;
      }
    });

    /** special processing for generic list */
    JSON_CONFIG.registerJsonBeanProcessor(UserRole.class, new JsonBeanProcessor()
    {
      public JSONObject processBean(Object bean, JsonConfig jsonConfig)
      {
        UserRoleBean ur = (UserRoleBean) bean;
        Map<String, String> m = new HashMap<String, String>();
        m.put("role", ur.getRole().getName());
        m.put("userRoleId", Integer.toString(ur.getUserRoleId()));
        return JSONObject.fromObject(m);
      }
    });
  }

  /** serial guid */
  private static final long serialVersionUID = 1L;

  /** the logger */
  protected transient Log mLogger = LogFactory.getLog(this.getClass());

  /** the user surrogate key */
  @Id()
  @Column(name = "user_id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected int mUserId;

  /** the unique login user name */
  @Column(name = "user_name", length = 32, nullable = false)
  protected String mUserName;

  /**
   * the password for the use given user (MD5 hashed)
   * the column needs to be excluded from the user management cycle
   * there will be a reset path
   */
  @Column(name = "password", length = 32, insertable = false, updatable = false)
//  @Transient()
  protected String mPassword;

  /** the user email */
  @Column(name = "email", length = 64, nullable = false)
  protected String mEmail;

  /** whether the account is enabled */
  @Column(name = "enabled", nullable = false)
  protected boolean mEnabled;

  /** the list of roles assigned to the user */
  // set fetch to not lazy load since it's a small set
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = UserRole.class)
  @JoinColumn(name = "user_id")
  protected List<UserRoleBean> mUserRoles;

  /** {@inheritDoc} */
  @Override()
  public int getUserId()
  {
    return mUserId;
  }

  /** {@inheritDoc} */
  @Override()
  public void setUserId(int userId)
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
    return new ToStringBuilder(this).appendSuper(super.toString())
        .append("mUserId", mUserId)
        .append("mUserName", mUserName)
        .append("mEmail", mEmail)
        .append("mEnabled", mEnabled)
        .append("mUserRoles", mUserRoles).toString();
  }

  /** {@inheritDoc} */
  @Override()
  public String toJSON()
  {
    return JSONObject.fromObject(this, JSON_CONFIG).toString();
  }

  /**
   * @see java.io.serializible
   * @param out the output stream
   * @throws IOException if something goes wrong
   */
  private void writeObject(java.io.ObjectOutputStream out)  throws IOException
  {

  }

  /**
   * @see java.io.serializible
   */
  /**
   * @see java.io.serializible
   * @param in the input stream
   * @throws IOException if something goes wrong
   * @throws ClassNotFoundException if something goes wrong
   */
  private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
  {
    mLogger = LogFactory.getLog(this.getClass());
  }

  /**
   * helper function to convert json to user object
   * @param json the json definition of a user
   * @return the user instance
   */
  public static UserBean jsonToUser(String json)
  {
    UserBean u = (UserBean) JSONObject.toBean(JSONObject.fromObject(json), User.class);

    morphUserRoles(u);

    return u;
  }

  /**
   * converts a json string into a collection of users
   * @param json the json string [{...},{...}]
   * @return the collection of users
   */
  @SuppressWarnings("unchecked")
  public static Collection<User> jsonArrayToUsers(String json)
  {
    Collection<User> users = (Collection<User>) JSONArray.toCollection(JSONArray.fromObject(json), User.class);

    for (UserBean u : users)
    {
      morphUserRoles(u);
    }
    return users;
  }

  /**
   * post process the userRole list since json-lib doesn't
   * handle collection properties in the top level conversion
   * @param user the user to morph the userRoles
   */
  protected static void morphUserRoles(UserBean user)
  {
    List<UserRoleBean> roles = new ArrayList<UserRoleBean>();
    for (Object o : user.getUserRoles())
    {
      MorphDynaBean dynaBean = (MorphDynaBean) o;
      UserRole ur = new UserRole();
      ur.setUser(user);
      ur.setRole(RoleType.valueOf((String) dynaBean.get("role")));
      ur.setUserRoleId(Integer.parseInt((String) dynaBean.get("userRoleId")));
      roles.add(ur);
    }
    user.setUserRoles(roles);
  }
}
