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
package org.tec.webapp.jdbc.entity.impl;

import java.util.List;
import org.tec.webapp.bean.UserBean;
import org.tec.webapp.jdbc.entity.BaseEntity;

/**
 * the user entity DB access impl
 */
public class UserDbaImpl extends BaseEntity
{

  /**
   * {@inheritDoc}
   */
  public void insert(UserBean user)
  {
  }

  /**
   * {@inheritDoc}
   */
  public void update(UserBean user)
  {
  }

  /**
   * {@inheritDoc}
   * This function is needed because we need to exclude the password
   * from the standard user operations.
   */
  public void updatePassword(UserBean user)
  {
  }

  /**
   * {@inheritDoc}
   */
  public void delete(UserBean user)
  {
  }

  /**
   * {@inheritDoc}
   */
  public UserBean getUser(String userName)
  {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public List<UserBean> getOtherUsers(String userName)
  {
    return (List<UserBean>)  null;
  }
}
