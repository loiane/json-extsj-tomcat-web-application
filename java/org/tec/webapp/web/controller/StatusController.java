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
import org.tec.webapp.service.SystemSvc;
import org.tec.webapp.service.UserSvc;
import org.tec.webapp.web.ControllerUtils;
import org.tec.webapp.web.WebError;
import org.tec.webapp.web.model.JSONModelAndView;
import org.tec.webapp.web.model.status.StatusBean;

/**
 * Job log controller
 */
@Controller()
@RequestMapping("/status")
//@Secured("ROLE_USER")
public class StatusController
{
  /** the logger */
  protected Log mLogger = LogFactory.getLog(this.getClass());

  /** the user service */
  @Autowired()
  protected UserSvc mUserSvc;

  /** the system service */
  @Autowired()
  protected SystemSvc mSystemSvc;

  /**
   * get current system status
   *
   * @param session the http session
   *
   * @return current system status
   */
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView getStatus(HttpSession session)
  {
    JSONModelAndView jmv = new JSONModelAndView();
    try
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("get status");
      }

      SerializableMap<String, SerializableList<StatusBean>> smap = new SerializableMap<String, SerializableList<StatusBean>>();

      SerializableList<StatusBean> slist = mSystemSvc.getStatus();

      UserBean currentUser = ControllerUtils.getCurrentUser(session, mUserSvc);

      slist.add(new StatusBean("current.user", currentUser.getUserName()));

      smap.put("status", slist);

      //mUserSvc.init();

      jmv.setData(smap);
    }
    catch (Throwable e)
    {
      jmv.setError(new WebError("failed to get system status", e));
    }
    return jmv;
  }
}
