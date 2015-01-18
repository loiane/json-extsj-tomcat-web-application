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
package org.tec.webapp.orm.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tec.security.Checksum;
import org.tec.webapp.orm.dao.UserDao;
import org.tec.webapp.orm.entity.User;
import org.tec.webapp.bean.UserBean;

/**
 * user data access object
 */
@Repository()
public class UserDaoImpl implements UserDao
{
  /** the logger */
  protected Log mLogger = LogFactory.getLog(this.getClass());

  /** the hibernate session factory */
  @Autowired()
  protected SessionFactory mSessionFactory;

  /**
   * {@inheritDoc}
   */
  @Override()
  public void insert(UserBean user)
  {
    Session session = mSessionFactory.getCurrentSession();
    session.persist(user);
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  public void update(UserBean user)
  {
    Session session = mSessionFactory.getCurrentSession();
    session.save(user);
  }

  /**
   * {@inheritDoc}
   * This function is needed because we need to exclude the password
   * from the standard user operations.
   */
  @Override()
  public void updatePassword(UserBean user)
  {
    Session session = mSessionFactory.getCurrentSession();

    Query query = session.createQuery("update User set password = :password where user_id = :user_id");
    query.setParameter("password", Checksum.getMD5(user.getPassword()));
    query.setParameter("user_id", user.getUserId());
    query.executeUpdate();
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  public void delete(UserBean user)
  {
    Session session = mSessionFactory.getCurrentSession();
    session.delete(user);
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  @SuppressWarnings("unchecked")
  public UserBean getUser(String userName)
  {
    Session session = mSessionFactory.getCurrentSession();

    Query q = session.createQuery("from User where user_name = :user_name");
    q.setParameter("user_name", userName);

    List<User> l = (List<User>) q.list();
    if (l.size() == 0)
    {
      return null;
    }
    else
    {
      return l.get(0);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  @SuppressWarnings("unchecked")
  public List<User> getOtherUsers(String userName)
  {
    Session session = mSessionFactory.getCurrentSession();

    Query query = session.createQuery("from User where user_name != :user_name");
    query.setParameter("user_name", userName);

    return (List<User>)  query.list();
  }
}
