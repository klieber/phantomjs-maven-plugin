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
package com.github.klieber.phantomjs.os;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LinuxProperties {

  private static final Logger LOGGER = LoggerFactory.getLogger(LinuxProperties.class);

  private static final String OS_RELEASE_PROPERTIES_FILE = "/etc/os-release";
  private static final String DISTRIBUTION_NAME = "ID";
  private static final String DISTRIBUTION_VERSION_ID = "VERSION_ID";

  private final File file;

  private Properties linuxProperties;

  public LinuxProperties() {
    this(new File(OS_RELEASE_PROPERTIES_FILE));
  }

  public LinuxProperties(File file) {
    this.file = file;
    this.linuxProperties = null;
  }

  public String getDistribution() {
    return getProperty(DISTRIBUTION_NAME);
  }

  public String getDistributionVersion() {
    return getProperty(DISTRIBUTION_VERSION_ID);
  }

  private String getProperty(String name) {
    String property = getLinuxProperties().getProperty(name);
    return property != null ? property.replaceAll("^\"(.*)\"$", "$1").toLowerCase() : null;
  }

  private Properties getLinuxProperties() {
    if (linuxProperties == null) {
      linuxProperties = readProperties();
    }
    return linuxProperties;
  }

  private Properties readProperties() {
    Properties properties = new Properties();
    try {
      if (this.file.exists() && this.file.canRead()) {
        FileInputStream in = new FileInputStream(this.file);
        properties.load(in);
        in.close();
      }
    } catch (IOException e) {
      LOGGER.trace("unable to read linux os linuxProperties", e);
    }
    return properties;
  }
}
