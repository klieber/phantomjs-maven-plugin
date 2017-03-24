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
package com.github.klieber.phantomjs.util;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.VersionRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VersionUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(VersionUtil.class);

  public static boolean isWithin(String version, String versionSpec) {
    boolean within = false;
    try {
      return VersionUtil.isWithin(version, VersionRange.createFromVersionSpec(versionSpec));
    } catch (InvalidVersionSpecificationException e) {
      LOGGER.warn("Invalid version specification: {}", versionSpec);
    }
    return within;
  }

  public static String getVersionSpec(String version, String spec) {
    if (spec == null || Boolean.parseBoolean(spec)) {
      spec = '[' + version + ']';
    } else if (Boolean.FALSE.toString().equalsIgnoreCase(spec)) {
      spec = "[0,]";
    } else if (!VersionUtil.isWithin(version, spec)) {
      LOGGER.warn("Version {} is not within requested range: {}", version, spec);
    }
    return spec;
  }

  private static boolean isLessThanOrEqualTo(ArtifactVersion versionA, ArtifactVersion versionB) {
    return !VersionUtil.isGreaterThan(versionA, versionB);
  }

  private static boolean isGreaterThan(ArtifactVersion versionA, ArtifactVersion versionB) {
    return compare(versionA, versionB) > 0;
  }

  private static boolean isWithin(String version, VersionRange versionRange) {
    ArtifactVersion artifactVersion = new DefaultArtifactVersion(version);
    ArtifactVersion recommendedVersion = versionRange.getRecommendedVersion();
    // treat recommended version as minimum version
    return recommendedVersion != null ?
      VersionUtil.isLessThanOrEqualTo(recommendedVersion, artifactVersion) :
      versionRange.containsVersion(artifactVersion);
  }

  private static int compare(ArtifactVersion versionA, ArtifactVersion versionB) {
    if (versionA == versionB) {
      return 0;
    }
    if (versionA == null) {
      return -1;
    }
    if (versionB == null) {
      return 1;
    }
    return versionA.compareTo(versionB);
  }
}
