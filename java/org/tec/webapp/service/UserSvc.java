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
package org.tec.webapp.service;

import org.tec.webapp.json.SerializableList;
import org.tec.webapp.orm.entity.User;
import org.tec.webapp.bean.UserBean;

/**
 * the user service interface
 */
public interface UserSvc
{
  /**
   * insert a new user
   * @param user the user to insert
   */
  void insert(UserBean user);

  /**
   * update a user
   * @param user the user to update
   */
  void update(UserBean user);

  /**
   * update the user password
   * @param user the user to update
   */
  void updatePassword(UserBean user);

  /**
   * delete a user
   * @param user the user to delete
   */
  void delete(UserBean user);

  /**
   * get user by id
   * @param userName the user name
   * @return the user for the given name
   */
  UserBean getUser(String userName);

  /**
   * get the list of the other users
   * @param userName the current user to exclude from the list
   * @return the list of other users
   */
  SerializableList<User> getOtherUsers(String userName);
}
