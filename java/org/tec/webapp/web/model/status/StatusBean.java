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
package org.tec.webapp.web.model.status;

import java.io.Serializable;

/**
 * status value bean
 */
public class StatusBean implements Serializable
{
  /** serial guid */
  private static final long serialVersionUID = 1L;

  /** bean name */
  protected String mName;

  /** bean value */
  protected String mValue;

  /**
   * ctor
   * @param name bean name
   * @param value bean value
   */
  public StatusBean(String name, String value)
  {
    mName = name;
    mValue = value;
  }

  /**
   * get name
   * @return name
   */
  public String getName()
  {
    return mName;
  }

  /**
   * set name
   * @param name name
   */
  public void setName(String name)
  {
    this.mName = name;
  }

  /**
   * get value
   * @return value
   */
  public String getValue()
  {
    return mValue;
  }

  /**
   * set value
   * @param value value
   */
  public void setValue(String value)
  {
    this.mValue = value;
  }
}
