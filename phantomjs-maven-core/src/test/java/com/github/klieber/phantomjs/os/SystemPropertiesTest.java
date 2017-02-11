package com.github.klieber.phantomjs.os;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SystemPropertiesTest {

  private SystemProperties systemProperties = new SystemProperties();

  @Test
  public void getOsName() {
    String original = System.getProperty("os.name");
    System.setProperty("os.name", "MacOs");
    assertEquals("macos", systemProperties.getOsName());
    System.setProperty("os.name", original);
  }

  @Test
  public void getOsVersion() {
    String original = System.getProperty("os.version");
    System.setProperty("os.version", "10");
    assertEquals("10", systemProperties.getOsVersion());
    System.setProperty("os.version", original);
  }

  @Test
  public void getOsArch() {
    String original = System.getProperty("os.arch");
    System.setProperty("os.arch", "x64");
    assertEquals("x64", systemProperties.getOsArch());
    System.setProperty("os.arch", original);
  }

}
