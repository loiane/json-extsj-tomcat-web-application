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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tec.security.Checksum;
import org.tec.webapp.bean.UserBean;
import org.tec.webapp.jdbc.bean.User;


/**
 * user entity test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:build/test/spring/mock-config.xml",
    "file:conf/spring/jdbc/database-config.xml"})
public class TestUser
{

  /** the user data access object */
  @Autowired()
  protected UserDba mUserDba;

  /**
   * test insert
   */
  @Test()
  public void insert()
  {
    UserBean user = testUser();

    UserBean actual = mUserDba.getUser(user.getUserName());
    Assert.assertTrue("users doesn match\n" + user + "\n" + actual, user.equals(actual));

    mUserDba.delete(user);

    UserBean deleted = mUserDba.getUser(user.getUserName());
    Assert.assertNull("user still exists " + user, deleted);
  }

  /**
   * test insert
   */
  @Test()
  public void update()
  {
    UserBean user = testUser();

    String pwd = Checksum.getMD5("update");
    user.setEmail("update@dev.nul");
    user.setPassword(pwd);

    mUserDba.update(user);

    mUserDba.updatePassword(user);

    UserBean actual = mUserDba.getUser(user.getUserName());
    Assert.assertTrue("users doesn match\n" + user + "\n" + actual, user.equals(actual));
    Assert.assertTrue("pwd doesn match\n" + user + "\n" + actual, pwd.equals(actual.getPassword()));

    mUserDba.delete(user);

    UserBean deleted = mUserDba.getUser(user.getUserName());
    Assert.assertNull("user still exists " + user, deleted);
  }

  /**
   * create seed user
   * @return the seed user
   */
  protected UserBean testUser()
  {
    UserBean user = new User();

    user.setUserName("junit");
    user.setEmail("junit@test.null");

    Long id = mUserDba.insert(user);
    Assert.assertTrue("id is foobared", id > 0);

    user.setUserId(id.intValue());

    return user;
  }
}
