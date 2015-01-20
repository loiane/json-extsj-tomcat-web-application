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
package org.tec.webapp.web.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tec.webapp.json.SerializableList;
import org.tec.webapp.json.SerializableMap;
import org.tec.webapp.bean.UserBean;
import org.tec.webapp.service.UserSvc;
import org.tec.webapp.web.ControllerUtils;
import org.tec.webapp.web.WebException;
import org.tec.webapp.web.model.JSONModelAndView;

/**
 * user controller for creating editing and deleting users
 */
@Controller()
@RequestMapping("/user_admin")
//@Secured("ROLE_ADMIN")
public class UserAdminController
{
  /** the logger */
  protected Log mLogger = LogFactory.getLog(this.getClass());

  /** the user service */
  @Autowired()
  protected UserSvc mUserSvc;

  /**
   * get list of users excluding the current
   *
   * @param session the http session
   *
   * @return current system status
   */
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView getUsers(HttpSession session)
  {
    JSONModelAndView jmv = new JSONModelAndView();

    try
    {
      UserBean currentUser = ControllerUtils.getCurrentUser(session, mUserSvc);

      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("get users for " + currentUser.getUserName());
      }

      SerializableMap<String, SerializableList<UserBean>> smap = new SerializableMap<String, SerializableList<UserBean>>();

      smap.put("users", mUserSvc.getOtherUsers(currentUser.getUserName()));

      jmv.setData(smap);
    }
    catch (Throwable e)
    {
      jmv.setError(new WebException("failed to get users", e));
    }
    return jmv;
  }
}
