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
package org.tec.webapp.jdbc.entity;

import java.util.List;

import org.tec.webapp.bean.UserRoleBean;

/**
 * user role data access interface
 */
public interface UserRoleDba
{
  /**
   * insert a new userRole
   * @param userRole the userRole to insert
   * @return the unique identifier
   */
  Long insert(UserRoleBean userRole);

  /**
   * delete a userRole
   * @param userRole the userRole to delete
   */
  void delete(UserRoleBean userRole);

  /**
   * get the list of roles for a given user
   * @param userId the current user id to get roles for
   * @return the list of roles
   */
  List<UserRoleBean> getRoles(Long userId);
}