package com.github.klieber.phantomjs.os;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OperatingSystemFactoryTest {

  @Mock
  private SystemProperties systemProperties;

  @Mock
  private LinuxProperties linuxProperties;

  @InjectMocks
  private OperatingSystemFactory operatingSystemFactory;

  @Test
  public void testCreateMacOperatingSystem() {
    OperatingSystem operatingSystem = createMockOs("macos", "10","x86_64");
    assertEquals("macos", operatingSystem.getName());
    assertEquals("10", operatingSystem.getVersion());
    assertEquals("x86_64", operatingSystem.getArchitecture());
    assertNull(operatingSystem.getDistribution());
    assertNull(operatingSystem.getDistributionVersion());
  }

  @Test
  public void testCreateWinOperatingSystem() {
    OperatingSystem operatingSystem = createMockOs("win", "7", "x64");
    assertEquals("win", operatingSystem.getName());
    assertEquals("7", operatingSystem.getVersion());
    assertEquals("x86_64", operatingSystem.getArchitecture());
    assertNull(operatingSystem.getDistribution());
    assertNull(operatingSystem.getDistributionVersion());
  }


  @Test
  public void testCreateLinuxOperatingSystem() {
    when(linuxProperties.getDistribution()).thenReturn("ubuntu");
    when(linuxProperties.getDistributionVersion()).thenReturn("16.04");

    OperatingSystem operatingSystem = createMockOs("linux", "4", "i686");
    assertEquals("linux", operatingSystem.getName());
    assertEquals("4", operatingSystem.getVersion());
    assertEquals("i686", operatingSystem.getArchitecture());
    assertEquals("ubuntu", operatingSystem.getDistribution());
    assertEquals("16.04", operatingSystem.getDistributionVersion());
  }

  private OperatingSystem createMockOs(String name, String version, String arch) {
    when(systemProperties.getOsName()).thenReturn(name);
    when(systemProperties.getOsVersion()).thenReturn(version);
    when(systemProperties.getOsArch()).thenReturn(arch);
    return operatingSystemFactory.create();
  }
}
