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
