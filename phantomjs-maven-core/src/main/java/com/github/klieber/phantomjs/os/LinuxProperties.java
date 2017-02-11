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
