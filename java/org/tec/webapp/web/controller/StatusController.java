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

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tec.webapp.json.SerializableList;
import org.tec.webapp.json.SerializableMap;
import org.tec.webapp.orm.service.UserSvc;
import org.tec.webapp.web.WebException;
import org.tec.webapp.web.model.JSONModelAndView;
import org.tec.webapp.web.model.status.StatusBean;

/**
 * Job log controller
 */
@Controller()
@RequestMapping("/status")
public class StatusController
{
  /** the logger */
  protected Log mLogger = LogFactory.getLog(this.getClass());

  /** the servlet context */
  @Autowired()
  protected ServletContext mServletContext;

  /** the data source see hibernate config */
  @Autowired()
  protected DataSource mDataSource;

  /** the hibernate session factory */
  @Autowired()
  protected LocalSessionFactoryBean mSessionFactory;

  /** the password service */
  @Autowired()
  protected UserSvc mUserSvc;

  /**
   * get current system status
   *
   * @ param session the http session
   * @ param request the current request HttpSession session, HttpServletRequest request
   *
   * @return current system status
   */
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView status()
  {
    JSONModelAndView jmv = new JSONModelAndView();
    Connection conn = null;
    try
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("status");
      }
      SerializableList<StatusBean> slist = new SerializableList<StatusBean>();
      SerializableMap<String, SerializableList<StatusBean>> smap = new SerializableMap<String, SerializableList<StatusBean>>();

      smap.put("status", slist);
      // get java information
      slist.add(new StatusBean("java.version", System.getProperty("java.version")));

      //get Servlet information
      slist.add(new StatusBean("server.info", "" + mServletContext.getServerInfo()));
      slist.add(new StatusBean("servlet.version", mServletContext.getMajorVersion() + "." + mServletContext.getMinorVersion()));

      // get database information
      conn = mDataSource.getConnection();
      DatabaseMetaData dmd = conn.getMetaData();
      slist.add(new StatusBean("database.server", dmd.getDatabaseProductName()));
      slist.add(new StatusBean("database.version", dmd.getDatabaseProductVersion()));
      slist.add(new StatusBean("jdbc.driver", dmd.getDriverName()));
      slist.add(new StatusBean("jdbc.driver.version", dmd.getDriverVersion()));

      // spring
      slist.add(new StatusBean("spring.version", SpringVersion.getVersion()));

      //hibernate
      slist.add(new StatusBean("hibernate.version", Version.getVersionString()));
      slist.add(new StatusBean("hibernate.session.factory", mSessionFactory.getClass().getName()));

      mUserSvc.init();

      jmv.setData(smap);
    }
    catch (Throwable e)
    {
      jmv.setError(new WebException("failed to get system status", e));
      mLogger.error("failed to get system status", e);
    }
    finally
    {
      if (conn != null)
      {
        try
        {
          conn.close();
        }
        catch (Throwable e)
        {
          mLogger.error("failed to get system status", e);
        }
      }
    }
    return jmv;
  }
}
