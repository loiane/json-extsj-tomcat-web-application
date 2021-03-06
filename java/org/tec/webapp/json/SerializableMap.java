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
package org.tec.webapp.json;

import java.util.HashMap;

/**
 * helper class for serializing map data
 *
 * @param <K> the map key name (Type String)
 * @param <V> the map value type
 */
public class SerializableMap<K, V extends JSONSerializable> extends HashMap<String, V> implements JSONSerializable
{

  /** serial guid */
  private static final long serialVersionUID = 1L;

  /**
   * {@inheritDoc}
   */
  @Override()
  public String toJSON()
  {
    if (size() == 0)
    {
      return "{}";
    }
    else
    {
      StringBuffer buff = new StringBuffer();

      buff.append('{');

      for (Entry<String, V> key : this.entrySet())
      {
        if (buff.length() > 2) //appended more than one list object
        {
          buff.append(',');
        }
        buff.append('\"').append(key.getKey()).append("\" : ").append(key.getValue().toJSON());
      }

      buff.append('}');

      return buff.toString();
    }
  }
}
