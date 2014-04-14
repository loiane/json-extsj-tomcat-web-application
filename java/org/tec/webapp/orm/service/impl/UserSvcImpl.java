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
package org.tec.webapp.orm.service.impl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tec.webapp.json.SerializableList;
import org.tec.webapp.orm.dao.UserDao;
import org.tec.webapp.orm.entity.User;
import org.tec.webapp.orm.service.UserSvc;

/**
 * the user service
 *
 * there needs to be a class level transaction annotation
 * to get the session setup correctly
 */
@Transactional()
@Service()
public class UserSvcImpl implements UserSvc
{
  /** the logger */
  protected Log mLogger = LogFactory.getLog(this.getClass());

  /** the user data access object */
  @Autowired()
  protected UserDao mUserDao;

  /**
   * {@inheritDoc}
   */
  @Override()
  @Transactional()
  public void insert(User user)
  {
    mUserDao.insert(user);
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  @Transactional()
  public void update(User user)
  {
    mUserDao.update(user);
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  @Transactional()
  public void updatePassword(User user)
  {
    mUserDao.updatePassword(user);
  }
  /**
   * {@inheritDoc}
   */
  @Override()
  @Transactional()
  public void delete(User user)
  {
    mUserDao.delete(user);
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  public void init()
  {
    Collection<User> users = User.getDefaultUsers();

    for (User u: users)
    {
      User user = mUserDao.getUser(u.getUserName());

      if (user == null)
      {
        mUserDao.insert(u);

        user = mUserDao.getUser(u.getUserName());
        u.setUserId(user.getUserId());

        mUserDao.updatePassword(u);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  @Transactional()
  public User getUser(String userName)
  {
    return mUserDao.getUser(userName);
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  @Transactional()
  public SerializableList<User> getOtherUsers(String userName)
  {
    SerializableList<User> userList = new SerializableList<User>();

    userList.addAll(mUserDao.getOtherUsers(userName));

    return userList;
  }
}
