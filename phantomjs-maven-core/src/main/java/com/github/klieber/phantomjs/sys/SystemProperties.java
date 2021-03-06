/*-
 * #%L
 * PhantomJS Maven Core
 * %%
 * Copyright (C) 2013 - 2017 Kyle Lieber
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
package com.github.klieber.phantomjs.sys;

import javax.inject.Named;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Named
public class SystemProperties {

  private static final String OS_NAME = "os.name";
  private static final String OS_VERSION = "os.version";
  private static final String OS_ARCH = "os.arch";
  private static final String ENV_PATH = "PATH";

  public String getOsName() {
    return getSystemProperty(OS_NAME);
  }

  public String getOsVersion() {
    return getSystemProperty(OS_VERSION);
  }

  public String getOsArch() {
    return getSystemProperty(OS_ARCH);
  }

  public List<String> getPath() {
    String systemPath = System.getenv(ENV_PATH);
    return systemPath != null ? Arrays.asList(systemPath.split(File.pathSeparator)) : Collections.<String>emptyList();
  }

  private String getSystemProperty(String name) {
    String property = System.getProperty(name);
    return property != null ? property.toLowerCase() : null;
  }
}
