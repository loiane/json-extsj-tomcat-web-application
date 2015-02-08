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
package org.tec.webapp.bean;

import java.util.List;

import org.tec.webapp.json.JSONSerializable;
/**
 * the user interface
 */
public interface UserBean extends JSONSerializable
{

  /**
   * @return the userId
   */
  long getUserId();

  /**
   * @param userId the userId to set
   */
  void setUserId(long userId);

  /**
   * @return the userName
   */
  String getUserName();

  /**
   * @param userName the userName to set
   */
  void setUserName(String userName);

  /**
   * @return the password
   */
  String getPassword();

  /**
   * @param password the password to set
   */
  void setPassword(String password);

  /**
   * @return the email
   */
  String getEmail();

  /**
   * @param email the email to set
   */
  void setEmail(String email);

  /**
   * @return the enabled
   */
  boolean getEnabled();

  /**
   * @param enabled the enabled to set
   */
  void setEnabled(boolean enabled);

  /**
   * @return the userRoles
   */
  List<UserRoleBean> getUserRoles();

  /**
   * @param userRoles the userRoles to set
   */
  void setUserRoles(List<UserRoleBean> userRoles);

  /**
   * whether this is an anonymous user
   * @return true if user is anonymous
   */
  boolean isAnonymous();

}