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

public class OperatingSystemFactory {

  private SystemProperties systemProperties;
  private LinuxProperties linuxProperties;

  public OperatingSystemFactory() {
    this(new SystemProperties(), new LinuxProperties());
  }

  public OperatingSystemFactory(SystemProperties systemProperties,
                                LinuxProperties linuxProperties) {
    this.systemProperties = systemProperties;
    this.linuxProperties = linuxProperties;
  }

  public OperatingSystem create() {
    String name = systemProperties.getOsName();
    String architecture = getArchitecture();
    String version = systemProperties.getOsVersion();

    return isLinux(name) ? createLinuxOS(name, architecture, version) : createOS(name, architecture, version);
  }

  private String getArchitecture() {
    String arch = systemProperties.getOsArch();
    String architecture = null;
    if (arch != null) {
      architecture = arch.contains("64") ? "x86_64" : "i686";
    }
    return architecture;
  }

  private boolean isLinux(String name) {
    return name.contains("nux");
  }

  private OperatingSystem createOS(String name,
                                   String architecture,
                                   String version) {
    return new OperatingSystem(
      name,
      architecture,
      version
    );
  }

  private OperatingSystem createLinuxOS(String name,
                                        String architecture,
                                        String version) {
    String distribution = linuxProperties.getDistribution();
    String distributionVersion = linuxProperties.getDistributionVersion();

    return new OperatingSystem(
      name,
      architecture,
      version,
      distribution,
      distributionVersion
    );
  }
}
