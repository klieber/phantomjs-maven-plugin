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
package com.github.klieber.phantomjs.archive.mapping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.klieber.phantomjs.os.OperatingSystem;
import com.github.klieber.phantomjs.util.VersionUtil;

public class OperatingSystemSpec extends OperatingSystem {

  @JsonCreator
  public OperatingSystemSpec(@JsonProperty("name") String name,
                             @JsonProperty("architecture") String architecture,
                             @JsonProperty("version") String version,
                             @JsonProperty("distribution") String distribution,
                             @JsonProperty("distributionVersion") String distributionVersion) {
    super(name, architecture, version, distribution, distributionVersion);
  }

  public boolean matches(OperatingSystem operatingSystem) {
    return
      contains(operatingSystem.getName(), this.getName()) &&
        withinVersion(operatingSystem.getVersion(), this.getVersion()) &&
        contains(operatingSystem.getArchitecture(), this.getArchitecture()) &&
        contains(operatingSystem.getDistribution(), this.getDistribution()) &&
        withinVersion(operatingSystem.getDistributionVersion(), this.getDistributionVersion());
  }

  private boolean contains(String actualValue, String partialValue) {
    return partialValue == null ||
      (actualValue != null && actualValue.toLowerCase().contains(partialValue.toLowerCase()));
  }

  private boolean withinVersion(String version, String versionSpec) {
    return versionSpec == null ||
      (version != null && VersionUtil.isWithin(version, versionSpec));
  }
}
