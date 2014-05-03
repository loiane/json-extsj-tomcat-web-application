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
package org.tec.security.spring;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


/**
 * set 401 response on failure
 *
 */
public class AuthenticationFailure implements AuthenticationFailureHandler
{
  /** the logger */
  protected Log mLogger = LogFactory.getLog(this.getClass());

  /**
   * {@inheritDoc}
   */
  @Override()
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
  {
    if (mLogger.isDebugEnabled())
    {
      mLogger.debug("failed login for " + request.getParameter("j_username"));
    }

    Writer out = response.getWriter();
    try
    {
      out.write("{success:false}");
    }
    catch (IOException e)
    {
      mLogger.error("failed to write to response", e);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "processing failed");
    }
    finally
    {
      out.close();
    }
  }
}
