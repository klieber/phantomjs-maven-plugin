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
