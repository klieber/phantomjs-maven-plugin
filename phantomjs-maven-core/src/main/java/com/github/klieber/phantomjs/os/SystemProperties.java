package com.github.klieber.phantomjs.os;

public class SystemProperties {

  private static final String OS_NAME = "os.name";
  private static final String OS_VERSION = "os.version";
  private static final String OS_ARCH = "os.arch";

  public String getOsName() {
    return getSystemProperty(OS_NAME);
  }

  public String getOsVersion() {
    return getSystemProperty(OS_VERSION);
  }

  public String getOsArch() {
    return getSystemProperty(OS_ARCH);
  }

  private String getSystemProperty(String name) {
    String property = System.getProperty(name);
    return property != null ? property.toLowerCase() : null;
  }
}
