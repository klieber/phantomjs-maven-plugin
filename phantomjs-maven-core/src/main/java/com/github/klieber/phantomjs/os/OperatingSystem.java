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
