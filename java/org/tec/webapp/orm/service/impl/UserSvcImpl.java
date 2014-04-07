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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    BufferedReader in = null;
    try
    {
      in = new BufferedReader(
          new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/tec/webapp/users.json"),
          StandardCharsets.UTF_8));

      StringBuffer buff = new StringBuffer();

      for (String str = in.readLine(); str != null; str = in.readLine())
      {
        buff.append(str);
      }

      Collection<User> users = User.jsonArrayToUsers(buff.toString());

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
          mLogger.error("failed to close stream", e);
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  public User getUser(String userName)
  {
    return mUserDao.getUser(userName);
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  public List<User> getOtherUsers(String userName)
  {
    return mUserDao.getOtherUsers(userName);
  }
}
