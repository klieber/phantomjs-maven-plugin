package com.github.klieber.phantomjs.archive.mapping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.klieber.phantomjs.os.OperatingSystem;
import com.github.klieber.phantomjs.util.VersionUtil;

public class ArchiveSpec {

  private final String versionSpec;
  private final OperatingSystemSpec operatingSystemSpec;

  @JsonCreator
  public ArchiveSpec(@JsonProperty("version") String versionSpec,
                     @JsonProperty("operatingSystem") OperatingSystemSpec operatingSystemSpec) {
    this.versionSpec = versionSpec;
    this.operatingSystemSpec = operatingSystemSpec;
  }

  public String getVersionSpec() {
    return versionSpec;
  }

  public OperatingSystemSpec getOperatingSystemSpec() {
    return operatingSystemSpec;
  }

  public boolean matches(String version, OperatingSystem operatingSystem) {
    return VersionUtil.isWithin(versionNumberOnly(version), this.versionSpec) &&
      this.operatingSystemSpec.matches(operatingSystem);
  }

  private String versionNumberOnly(String version) {
    return version.replaceAll("[^0-9.]", "");
  }
}
