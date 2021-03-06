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
package org.tec.webapp.service;

import org.tec.webapp.json.SerializableList;
import org.tec.webapp.web.model.status.StatusBean;


/**
 * this is the system service interface
 */
public interface SystemSvc
{
  /**
   * get the system status
   * @return the list of statuses
   */
  SerializableList<StatusBean> getStatus();
}
