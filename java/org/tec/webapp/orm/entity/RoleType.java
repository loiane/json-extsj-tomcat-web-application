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

/**
 * the role type used for access
 */
public enum RoleType
{
  /** the admin role */
  ROLE_ADMIN("ROLE_ADMIN"),
  /** the user role */
  ROLE_USER("ROLE_USER"),
  /** the guest role */
  ROLE_GUEST("ROLE_GUEST");

  /**
   * role name
   */
  private final String mRoleName;

  /**
   * @param roleName construct the role name
   */
  RoleType(final String roleName)
  {
    mRoleName = roleName;
  }

  /**
   * @return the role name
   */
  public String getName()
  {
    return mRoleName;
  }

  /**
   * get RoleType from a name
   * @param name which will return the RoleType
   * @return RoleType
   */
  public static RoleType fromName(final String name)
  {
    for (final RoleType roleType : RoleType.values())
    {
      if (roleType.getName().equalsIgnoreCase(name))
      {
        return roleType;
      }
    }

    throw new IllegalArgumentException("no role found for name " + name);
  }
}
