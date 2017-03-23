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

public class OperatingSystem {

  private final String name;
  private final String architecture;
  private final String version;
  private final String distribution;
  private final String distributionVersion;

  public OperatingSystem(String name,
                         String architecture,
                         String version) {
    this(name, architecture, version, null, null);
  }

  public OperatingSystem(String name,
                         String architecture,
                         String version,
                         String distribution,
                         String distributionVersion) {
    this.name = name;
    this.architecture = architecture;
    this.version = version;
    this.distribution = distribution;
    this.distributionVersion = distributionVersion;
  }

  public String getName() {
    return this.name;
  }

  public String getArchitecture() {
    return this.architecture;
  }

  public String getVersion() {
    return this.version;
  }

  public String getDistribution() {
    return this.distribution;
  }

  public String getDistributionVersion() {
    return this.distributionVersion;
  }

  @Override
  public String toString() {
    return "OperatingSystem{" +
      "name='" + name + '\'' +
      ", architecture='" + architecture + '\'' +
      ", version='" + version + '\'' +
      ", distribution='" + distribution + '\'' +
      ", distributionVersion='" + distributionVersion + '\'' +
      '}';
  }
}
