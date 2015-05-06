/*
 * Copyright (c) 2015 Kyle Lieber
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.klieber.phantomjs.util;

import org.apache.maven.artifact.versioning.ComparableVersion;

public class VersionUtil {

  public static int compare(String versionA, String versionB) {
    if (versionA == versionB) {
      return 0;
    }
    if (versionA == null) {
      return -1;
    }
    if (versionB == null) {
      return 1;
    }
    return new ComparableVersion(versionA).compareTo(new ComparableVersion(versionB));
  }

  public static boolean isGreaterThan(String versionA, String versionB) {
    return compare(versionA, versionB) > 0;
  }

  public static boolean isLessThan(String versionA, String versionB) {
    return compare(versionA, versionB) < 0;
  }

  public static boolean isEqualTo(String versionA, String versionB) {
    return compare(versionA, versionB) == 0;
  }
}
