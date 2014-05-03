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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * returns 401 unauth header to be used with pure js ajax client
 */
public class Http401AuthenticationEntryPoint implements AuthenticationEntryPoint
{
  /** the logger */
  protected Log mLogger = LogFactory.getLog(this.getClass());

  /**
   * {@inheritDoc}
   */
  @Override()
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException
  {
    if (mLogger.isDebugEnabled())
    {
      mLogger.debug("url requires authentication " + request.getRequestURL().toString());
    }
    //send response code to trip error handler to prompt for login
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication required");
  }
}
