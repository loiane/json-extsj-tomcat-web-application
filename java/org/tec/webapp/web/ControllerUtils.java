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
package org.tec.webapp.web;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContext;
import org.tec.webapp.orm.entity.User;
import org.tec.webapp.bean.UserBean;
import org.tec.webapp.service.UserSvc;

/**
 * utils for common controller functionality
 */
public final class ControllerUtils
{
  /** the session key for the SPRING_SECURITY_CONTEXT */
  private static final String SPRING_SECURITY_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT";

  /** the session key for the current user */
  private static final String CURRENT_USER_KEY = User.class.getName();

  /** the logger */
  private static final Log LOGGER = LogFactory.getLog(ControllerUtils.class);

  /**
   * hidden ctor
   */
  private ControllerUtils()
  {
    //noop
  }

  /**
   * get the currently logged on user
   * @param session the current http session
   * @param userSvc the user service
   * @return the current user
   */
  public static UserBean getCurrentUser(HttpSession session, UserSvc userSvc)
  {
    UserBean u = (UserBean) session.getAttribute(CURRENT_USER_KEY);

    if (u == null)
    {
      SecurityContext secctx = (SecurityContext) session.getAttribute(SPRING_SECURITY_CONTEXT_KEY);

      String currentUser = ((org.springframework.security.core.userdetails.User) secctx.getAuthentication().getPrincipal()).getUsername();

      u = userSvc.getUser(currentUser);

      session.setAttribute(CURRENT_USER_KEY, u);
    }

    return u;
  }

  /**
   * clear the current user
   * @param session the current session
   * @return the current user
   */
  public static UserBean clearCurrentUser(HttpSession session)
  {
    UserBean u = (UserBean) session.getAttribute(CURRENT_USER_KEY);

    if (u != null)
    {
      session.removeAttribute(CURRENT_USER_KEY);
    }

    return u;
  }
}