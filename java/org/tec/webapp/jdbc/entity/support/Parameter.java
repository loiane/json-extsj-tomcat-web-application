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

import org.apache.commons.lang.builder.ToStringBuilder;

import java.sql.JDBCType;

/**
 * Query/Form parameter type that we expect
 */
public class Parameter
{

  /** the data value  */
  protected Object mData = null;

  /** the SQL data type @see java.sql.JDBCType */
  private JDBCType mType = JDBCType.VARCHAR;

  /** the max length used for strings */
  private int mMaxLength = Integer.MAX_VALUE;


  /**
   * VARCHAR type ctor
   * @param data the data
   */
  public Parameter(String data)
  {
    mData = data;
  }

  /**
   * VARCHAR type ctor
   * @param data the string data
   * @param maxLength the max string length
   */
  public Parameter(String data, int maxLength)
  {
    this.mData = data;
    this.mMaxLength = maxLength;
  }

  /**
   * BOOLEAN type ctor add an Boolean parameter
   * @param data the Boolean data
   */
  public Parameter(Boolean data)
  {
    this.mData = data;
    this.mType = JDBCType.BOOLEAN;
  }

  /**
   * INTEGER type ctor add an integer parameter
   * @param data the integer data
   */
  public Parameter(Integer data)
  {
    this.mData = data;
    this.mType = JDBCType.INTEGER;
  }

  /**
   * Long type ctor add an integer parameter
   * @param data the long data
   */
  public Parameter(Long data)
  {
    this.mData = data;
    // TODO verify right type
    this.mType = JDBCType.INTEGER;
  }


  /**
   * get the string rep of the data
   * @return the string rep of the data
   */
  public Object getData()
  {
    return mData;
  }

  /**
   * get the parameter type @see java.sql.Types
   * @return the parameter type
   */
  public JDBCType getType()
  {
    return mType;
  }

  /**
   * get the Maximum character length
   * @return get the Maximum character length
   */
  public int getMaxLength()
  {
    return mMaxLength;
  }

  /**
   * {@inheritDoc}
   */
  @Override()
  public String toString()
  {
    return new ToStringBuilder(this)
      .append("type", mType)
      .append("maxLength", mMaxLength)
      .append("data", mData)
      .toString();
  }
}
