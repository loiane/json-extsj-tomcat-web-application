/*******************************************************************************
 * Copyright 2015 org.tec
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
package org.tec;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * base test class sets up Spring context (simulate webapp)
 * paths need to be project relative, or relative to ant working folder
 * TODO see if path root can be variablized
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("dist/test/webapps/tec-json-webapp")
@ContextConfiguration(
    locations = {
        "file:dist/test/webapps/tec-json-webapp/WEB-INF/conf/security-config.xml",
        "file:dist/test/webapps/tec-json-webapp/WEB-INF/conf/database-config.xml"})
public abstract class BaseTest
{
  /** project relative web app root */
  public static final String APP_ROOT = System.getProperty("app.root");
}
