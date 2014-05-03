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

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tec.webapp.web.WebException;
import org.tec.webapp.web.model.JSONModelAndView;

/**
 * test controller to send unauth response to workout login...
 */
@Controller()
@RequestMapping("/test")
public class TestResponseController
{
  /** the logger */
  protected Log mLogger = LogFactory.getLog(this.getClass());

  /**
   * send 401 response code
   *
   * @param response the http response
   *
   * @return empty model
   */
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView unauth(HttpServletResponse response)
  {
    JSONModelAndView jmv = new JSONModelAndView();

    try
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("sending 401");
      }
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");

    }
    catch (Throwable e)
    {
      jmv.setError(new WebException("failed to set response", e));
    }
    return jmv;
  }

}
