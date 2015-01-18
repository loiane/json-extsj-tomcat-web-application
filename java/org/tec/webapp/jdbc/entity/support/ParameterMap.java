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
package org.tec.webapp.jdbc.entity.support;

import java.util.LinkedHashMap;

/**
 * the SQL parameter map Uses Linked hash map to guarantee order
 */
public final class ParameterMap extends LinkedHashMap<String, Parameter>
{
  /** serial guid */
  private static final long serialVersionUID = 1L;

  /**
   * put a parameter
   * @param key parameter key (column name)
   * @param value the string value
   * @param maxSize the max column width (used for strings)
   * @return this
   */
  public ParameterMap put(String key, String value, int maxSize)
  {
    if (null != value)
    {
      put(key, new Parameter(value, maxSize));
    }
    return this;
  }

  /**
   * put a parameter
   * @param key parameter key (column name)
   * @param value the string value
   * @return this
   */
  public ParameterMap put(String key, String value)
  {
    if (null != value)
    {
      put(key, new Parameter(value));
    }
    return this;
  }

  /**
   * put a boolean parameter
   * @param key parameter key (column name)
   * @param value the string value
   * @return this
   */
  public ParameterMap put(String key, Boolean value)
  {
    if (null != value)
    {
      put(key, new Parameter(value));
    }
    return this;
  }

  /**
   * put a long parameter
   * @param key parameter key (column name)
   * @param value the value
   * @return this
   */
  public ParameterMap put(String key, Long value)
  {
    if (null != value)
    {
      put(key, new Parameter(value));
    }
    return this;
  }

  /**
   * put a integer parameter
   * @param key parameter key (column name)
   * @param value the value
   * @return this
   */
  public ParameterMap put(String key, Integer value)
  {
    if (null != value)
    {
      put(key, new Parameter(value));
    }
    return this;
  }

  /**
   * put a DateTime parameter
   * @param key parameter key (column name)
   * @param value the value
   * @param type the sql type to map to
   * @return this
   */
/*
  public ParameterMap put(String key, DateTime value, int type)
  {
    if (null != value)
    {
      if (type == ParamType.TIME_HHMM)
      {
        put(key, new ParamType(DateTimeHelper.formatTime(value), type));
      }
      else if (type == Types.DATE)
      {
        put(key, new ParamType(DateTimeHelper.formatDate(value), type));
      }
      else
      {
        put(key, new ParamType(DateTimeHelper.formatTimeStamp(value), type));
      }
    }
    return this;
  }
*/

  /**
   * put a dateTime parameter (typed to timestamp)
   * @param key parameter key (column name)
   * @param value the value
   * @return this
   */
/*
  public ParameterMap put(String key, DateTime value)
  {
    if (null != value)
    {
      put(key, new ParamType(value.toString(), Types.TIMESTAMP));
    }
    return this;
  }
*/
}
