package com.github.klieber.phantomjs.os;

import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class LinuxPropertiesTest {

  private LinuxProperties linuxProperties = new LinuxProperties(createMockFile());

  @Test
  public void testGetDistribution() {
    assertEquals("ubuntu", linuxProperties.getDistribution());
  }

  @Test
  public void testGetDistributionVersion() {
    assertEquals("16.04", linuxProperties.getDistributionVersion());
  }

  private static File createMockFile() {
    URL resource = LinuxPropertiesTest.class.getResource("/test-linux.properties");
    return new File(resource.getFile());
  }
}
