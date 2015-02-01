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

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.tec.BaseTest;
import org.tec.webapp.bean.RoleType;
import org.tec.webapp.bean.UserBean;
import org.tec.webapp.bean.UserRoleBean;
import org.tec.webapp.jdbc.bean.User;
import org.tec.webapp.jdbc.bean.UserRole;

/**
 * user role test class
 */
public class TestUserRole extends BaseTest
{
  /** get context to test programmatic transactions */
  @Autowired()
  protected ApplicationContext mAppContext;

  /** the user data access object */
  @Autowired()
  protected UserDba mUserDba;

  /** the user role data access object */
  @Autowired()
  protected UserRoleDba mUserRoleDba;

  /**
   * test insert
   */
  @Test()
  public void insert()
  {
    UserRoleBean userRole = testUserRole();

    mUserRoleDba.delete(userRole);

    List<UserRoleBean> l = mUserRoleDba.getRoles(new Long(userRole.getUser().getUserId()));

    Assert.assertNull("should be no roles for " + userRole.getUser(), l);
  }

  /**
   * create seed user
   * @return the seed user
   */
  protected UserRoleBean testUserRole()
  {
    DataSourceTransactionManager tmgr = (DataSourceTransactionManager) mAppContext.getBean("transactionManager");

    TransactionStatus tranStat = tmgr.getTransaction(new DefaultTransactionDefinition());

    try
    {
      UserBean user = new User();

      user.setUserName("junit");
      user.setEmail("junit@test.null");

      Long userId = mUserDba.insert(user);
      Assert.assertTrue("id is foobared", userId > 0);

      user.setUserId(userId.intValue());

      UserRoleBean roleBean = new UserRole();

      roleBean.setUser(user);

      roleBean.setRole(RoleType.ROLE_GUEST);

      Long id = mUserRoleDba.insert(roleBean);

      roleBean.setUserRoleId(id.intValue());

      List<UserRoleBean> l = mUserRoleDba.getRoles(new Long(user.getUserId()));

      Assert.assertTrue("should be at least 1 role", l.size() > 0);

      tmgr.commit(tranStat);

      return roleBean;
    }
    catch (Throwable t)
    {
      tmgr.rollback(tranStat);
      throw new RuntimeException("failed to create user role", t);
    }
  }
}
