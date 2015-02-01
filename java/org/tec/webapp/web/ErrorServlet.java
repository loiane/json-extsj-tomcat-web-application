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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * servlet to trap errors
 */
@WebServlet("/error")
public class ErrorServlet extends HttpServlet
{
  /** serial guid */
  private static final long serialVersionUID = 1L;

  /** the logger */
  protected static final Log LOGGER = LogFactory.getLog(ErrorServlet.class);

  /**
   * hand get request
   * @param request the request instance
   * @param response the response instance
   * @throws ServletException if processing fails
   * @throws IOException if processing fails
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    processError(request, response);
  }

  /**
   * hand get request
   * @param request the request instance
   * @param response the response instance
   * @throws ServletException if processing fails
   * @throws IOException if processing fails
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    processError(request, response);
  }

  /**
   * process the error
   * @param request the request instance
   * @param response the response instance
   * @throws IOException if processing fails
   */
  protected void processError(HttpServletRequest request, HttpServletResponse response) throws IOException
  {
    Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
    Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    String requestUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

    LOGGER.error("failed to process " + requestUri + " error code: " + statusCode);

    WebError we;
    if (throwable != null)
    {
      LOGGER.error("error", throwable);
      we = new WebError(throwable.getMessage(), ErrorCodes.UNRESOLVEABLE_ERROR);
    }
    else
    {
      we = new WebError("error", ErrorCodes.UNRESOLVEABLE_ERROR);
    }

    PrintWriter pw = null;
    try
    {
      response.setStatus(statusCode != null ? statusCode : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
      pw = response.getWriter();
      pw.write(we.toJSON());
      pw.flush();
    }
    finally
    {
      if (pw != null)
      {
        pw.close();
      }
    }
  }
}
