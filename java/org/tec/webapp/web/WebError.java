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

import net.sf.json.JSONObject;

import org.tec.webapp.json.JSONSerializable;

/**
 * Web Exception json object
 */
public class WebError implements JSONSerializable
{

  /** serial guid */
  private static final long serialVersionUID = 1;

  /** default message */
  protected String mMessage;

  /** the message resource key to use at client */
  //protected String mMessageKey;

  /** error code for recoverable error handling */
  protected ErrorCodes mCode = ErrorCodes.UNRESOLVEABLE_ERROR;

  /** the root error */
  protected transient Throwable mCause = null;

  /**
   * @param message a message describing the error
   * @param cause the cause exception
   */
  public WebError(String message, Throwable cause)
  {
    mMessage = message;
    mCause = cause;
  }

  /**
   * @param message a message describing the error
   * @param errorCode the error code
   */
  public WebError(String message, ErrorCodes errorCode)
  {
    mMessage = message;
    mCode = errorCode;
  }

  /**
   * @return the message
   */
  public String getMessage()
  {
    return mMessage;
  }

  /**
   * @return the code
   */
  public ErrorCodes getCode()
  {
    return mCode;
  }

  /**
   * @return the messageKey
   */
/*
  public String getMessageKey()
  {
    return mMessageKey;
  }
*/
  /**
   * @return the cause
   */
  public Throwable getCause()
  {
    return mCause;
  }

  /**
   * {@inheritDoc}
   */
  public String toJSON()
  {
    return JSONObject.fromObject(this).toString();
  }
}
