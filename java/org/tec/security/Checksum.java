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
package org.tec.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class is an encapsulation of Security checksum operations
 */
public final class Checksum
{
  /**
   * MD5 encryption algorithm
   */
  public static final String ALGORITHM_MD5 = "MD5";

  /**
   * SHA1 encryption algorithm
   */
  public static final String ALGORITHM_SHA1 = "SHA";

  /**
   * HMAC-SHA1 encryption algorithm
   */
  public static final String ALGORITHM_HMAC_SHA1 = "HmacSHA1";

  /**
   * Unicode encoding
   */
  public static final String ENCODING_UTF8 = "UTF-8";

  /**
   * Used to convert dec int into hex string
   */
  private static final String[] HEX_MAP  = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

  /**
   * hide constructor
   */
  private Checksum()
  {
  }

  /**
   * Calculate an MD5 checksum from a string
   *
   * @param data the String to calulated the checksum on
   *
   * @return the md5 checksum  Hex encoded
   */
  public static String getMD5(String data)
  {
    return toHexString(hash(ALGORITHM_MD5, data));
  }

  /**
   * Calculate an SHA checksum from a string
   *
   * @param data the String to calulated the checksum on
   *
   * @return the SHA checksum  Hex encoded
   */
  public static String getSHA1(String data)
  {
    return toHexString(hash(ALGORITHM_SHA1, data));
  }

  /**
   * Generates an HMAC-SHA1 checksum for a given key and value.
   *
   * @param key the key to use in the checksum
   * @param data the data to use in the checksum
   * @return an HMAC-SHA1 checksum
   */
  public static String getHmacSHA1(String key, String data)
  {
    return toHexString(hashMac(ALGORITHM_HMAC_SHA1, key, data));
  }

  /**
   * Gets the current used checksum (wrapper)
   *
   * @param data Input data
   * @return Current checksum used
   */
  public static String getChecksum(String data)
  {
    return getSHA1(data);
  }

  /**
   * Create the hash message authentication code based on the given algorithm.
   *
   * @param algorithm the name of the secret-key algorithm to be associated
   *                  with the given key material
   * @param key the key material of the secret key
   * @param data the String to calulate the checksum on
   *
   * @return the hash message authentication code (HMAC) in byte array form
   */
  protected static byte[] hashMac(String algorithm, String key, String data)
  {
    try
    {
      SecretKeySpec hmac = new SecretKeySpec(key.getBytes(), algorithm);
      Mac mac = Mac.getInstance(hmac.getAlgorithm());
      mac.init(hmac);
      return mac.doFinal(data.getBytes());
    }
    catch (InvalidKeyException ike)
    {
      throw new RuntimeException(key + " invalid key\n", ike);
    }
    catch (NoSuchAlgorithmException nae)
    {
      throw new RuntimeException(ALGORITHM_HMAC_SHA1 + " not supported\n", nae);
    }
  }

  /**
   * Create the hash based on the given algoritm
   *
   * @param algorithm the method of encryption
   * @param data the String to calulated the checksum on
   *
   * @return the the hash in byte array form
   */
  protected static byte[] hash(String algorithm, String data)
  {
    try
    {
      MessageDigest md5 = MessageDigest.getInstance(algorithm);
      try
      {
        byte[] bytes = data.getBytes(ENCODING_UTF8);
        md5.update(bytes, 0, bytes.length);
      }
      catch (UnsupportedEncodingException uee)
      {
        throw new RuntimeException(ENCODING_UTF8 + " not supported\n", uee);
      }

      return md5.digest();
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new RuntimeException(algorithm + " not supported\n", e);
    }
  }

  /**
   * Convert a byte array into a String
   *
   * @param data the byte array
   *
   * @return the string representing the byte array
   */
  protected static String toHexString(byte[] data)
  {
    StringBuffer result = new StringBuffer();

    for (int cnt = 0; cnt < data.length; cnt++)
    {
      int l = ((data[cnt]) & 0xF0) >> 4;
      int r = (data[cnt]) & 0x0F;
      result.append(HEX_MAP[l] + HEX_MAP[r]);
    }

    return result.toString();
  }

  /**
   * this is used for unit testing this class
   *
   * @param args Not currently used
   *
   * @exception Exception thrown if anything goes wrong
   */
  public static void main(String[] args) throws Exception
  {
    String arg = "guest";
    //System.out.println("test");
    System.out.println("MD5               [" + getMD5(arg) + "]");
    //System.out.println("SHA               [" + getSHA1(arg) + "]");
    //System.out.println("HMAC-SHA1         [" + getHmacSHA1(args[0], args[1]) + "]");
  }
}


