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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
public class User implements JSONSerializable
{
  /** the anonymous user name */
  protected static final String ANON_USER_NAME = "anonymous";

  /** the anonymous user */
  public static final User ANON_USER = new User();

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
        UserRole ur = (UserRole) bean;
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
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  protected List<UserRole> mUserRoles;

  /**
   * @return the userId
   */
  public int getUserId()
  {
    return mUserId;
  }

  /**
   * @param userId the userId to set
   */
  public void setUserId(int userId)
  {
    mUserId = userId;
  }

  /**
   * @return the userName
   */
  public String getUserName()
  {
    return mUserName;
  }

  /**
   * @param userName the userName to set
   */
  public void setUserName(String userName)
  {
    mUserName = userName;
  }

  /**
   * @return the password
   */
  public String getPassword()
  {
    return mPassword;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password)
  {
    mPassword = password;
  }

  /**
   * @return the email
   */
  public String getEmail()
  {
    return mEmail;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email)
  {
    mEmail = email;
  }

  /**
   * @return the enabled
   */
  public boolean getEnabled()
  {
    return mEnabled;
  }

  /**
   * @param enabled the enabled to set
   */
  public void setEnabled(boolean enabled)
  {
    mEnabled = enabled;
  }

  /**
   * @return the userRoles
   */
  public List<UserRole> getUserRoles()
  {
    return mUserRoles;
  }

  /**
   * @param userRoles the userRoles to set
   */
  public void setUserRoles(List<UserRole> userRoles)
  {
    mUserRoles = userRoles;
  }

  /**
   * whether this is an anonymous user
   * @return true if user is anonymous
   */
  public boolean isAnonymous()
  {
    return mUserName.equals(ANON_USER_NAME);
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
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
  public static User jsonToUser(String json)
  {
    User u = (User) JSONObject.toBean(JSONObject.fromObject(json), User.class);

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

    for (User u : users)
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
  protected static void morphUserRoles(User user)
  {
    List<UserRole> roles = new ArrayList<UserRole>();
    for (Object o : user.getUserRoles())
    {
      MorphDynaBean dynaBean = (MorphDynaBean) o;
      UserRole ur = new UserRole();
      ur.setUser(user);
      ur.setRole(RoleType.fromName((String) dynaBean.get("role")));
      ur.setUserRoleId(Integer.parseInt((String) dynaBean.get("userRoleId")));
      roles.add(ur);
    }
    user.setUserRoles(roles);
  }

  /**
   * get the list of default user from users.json
   * @return the collection of default users
   */
  public static Collection<User> getDefaultUsers()
  {
    BufferedReader in = null;
    try
    {
      in = new BufferedReader(
          new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/tec/webapp/users.json"),
          StandardCharsets.UTF_8));

//      in = new BufferedReader(new InputStreamReader(new FileInputStream("C:/Users/tcronin/workspace/webapp/conf/WEB-INF/classes/org/tec/webapp/users.json"), StandardCharsets.UTF_8));

      StringBuffer buff = new StringBuffer();

      for (String str = in.readLine(); str != null; str = in.readLine())
      {
        buff.append(str);
      }

      return User.jsonArrayToUsers(buff.toString());
    }
    catch (Throwable t)
    {
      throw new RuntimeException("failed to init users", t);
    }
    finally
    {
      if (in != null)
      {
        try
        {
          in.close();
        }
        catch (IOException e)
        {
          throw new RuntimeException("failed to close tream", e);
        }
      }
    }
  }
}
