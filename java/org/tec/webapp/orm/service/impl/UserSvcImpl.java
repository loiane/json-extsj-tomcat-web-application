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

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tec.webapp.json.SerializableList;
import org.tec.webapp.orm.dao.UserDao;
import org.tec.webapp.bean.UserBean;
import org.tec.webapp.service.UserSvc;

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
  private Log mLogger = LogFactory.getLog(this.getClass());

  /** the user data access object */
  @Autowired()
  protected UserDao mUserDao;

  /**
   * {@inheritDoc}
   */
  @Override()
  @Transactional()
  public void insert(UserBean user)
  {
    if (mLogger.isDebugEnabled())
    {
      mLogger.debug("inserting " + user);
    }

    mUserDao.insert(user);
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  @Transactional()
  public void update(UserBean user)
  {
    if (mLogger.isDebugEnabled())
    {
      mLogger.debug("updating " + user);
    }

    mUserDao.update(user);
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  @Transactional()
  public void updatePassword(UserBean user)
  {
    if (mLogger.isDebugEnabled())
    {
      mLogger.debug("updating password " + user);
    }

    mUserDao.updatePassword(user);
  }
  /**
   * {@inheritDoc}
   */
  @Override()
  @Transactional()
  public void delete(UserBean user)
  {
    if (mLogger.isDebugEnabled())
    {
      mLogger.debug("deleting " + user);
    }

    mUserDao.delete(user);
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  @Transactional()
  public UserBean getUser(String userName)
  {
    if (mLogger.isDebugEnabled())
    {
      mLogger.debug("getting " + userName);
    }

    return mUserDao.getUser(userName);
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  @Transactional()
  public SerializableList<UserBean> getOtherUsers(String userName)
  {
    if (mLogger.isDebugEnabled())
    {
      mLogger.debug("getting others " + userName);
    }

    SerializableList<UserBean> userList = new SerializableList<UserBean>();

    userList.addAll(mUserDao.getOtherUsers(userName));

    return userList;
  }
}
