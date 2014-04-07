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
/**
 *
 */
package org.tec.webapp.web.model;

import org.springframework.web.servlet.ModelAndView;
import org.tec.webapp.json.JSONSerializable;
import org.tec.webapp.web.WebException;


/**
 * json based view impl
 */
public class JSONModelAndView extends ModelAndView
{
  /** error bean key */
  public static final String ERROR_KEY = JSONModelAndView.class.getName() + ".ERROR";

  /** json bean key */
  public static final String JSON_KEY = JSONModelAndView.class.getName() + ".JSON";

  /**  the standard view */
  protected static final String VIEW = "json.view";

  /**
   * default ctor
   */
  public JSONModelAndView()
  {
    super(VIEW);
  }

  /**
   * ctor
   * @param data the model data
   */
  public JSONModelAndView(JSONSerializable data)
  {
    this();
    addObject(JSON_KEY, data);
  }

  /**
   * @param data the json data
   */
  public void setData(JSONSerializable data)
  {
    addObject(JSON_KEY, data);
  }

  /**
   * Set error information
   * @param webException the web error
   */
  public void setError(WebException webException)
  {
    addObject(ERROR_KEY, webException);
  }
}
