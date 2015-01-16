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
package org.tec.webapp.web.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.servlet.view.AbstractView;
import org.tec.webapp.json.JSONSerializable;
import org.tec.webapp.web.model.JSONModelAndView;


/**
 * base spring view for application that is json data
 */
public class JSONView extends AbstractView
{
  /** the logger */
  protected Log mLogger = LogFactory.getLog(this.getClass());

  /**
   * {@inheritDoc}
   */
  @Override()
  protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    try
    {
      JSONSerializable json = (JSONSerializable) model.get(JSONModelAndView.ERROR_KEY);

      if (json != null)
      {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "failed to process response");
      }
      else
      {
        json = (JSONSerializable) model.get(JSONModelAndView.JSON_KEY);

        if (json != null)
        {
          if (mLogger.isDebugEnabled())
          {
            mLogger.debug("JSON Response\n" + json.toJSON());
          }
          response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
          PrintWriter pw = response.getWriter();
          pw.write(json.toJSON());
          pw.flush();
        }
        else // null json for controllers that usurp the response or didn't intend to
        {
          mLogger.warn("null json object");
        }
      }
    }
    catch (Exception e)
    {
      mLogger.error("error", e);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "failed to process response");
    }
  }
}
