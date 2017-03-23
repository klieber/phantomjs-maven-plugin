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
package com.github.klieber.phantomjs.os;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
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
    OperatingSystem operatingSystem = createMockOs("macos", "10", "x86_64");
    assertThat(operatingSystem.getName()).isEqualTo("macos");
    assertThat(operatingSystem.getVersion()).isEqualTo("10");
    assertThat(operatingSystem.getArchitecture()).isEqualTo("x86_64");
    assertThat(operatingSystem.getDistribution()).isNull();
    assertThat(operatingSystem.getDistributionVersion()).isNull();
  }

  @Test
  public void testCreateWinOperatingSystem() {
    OperatingSystem operatingSystem = createMockOs("win", "7", "x64");
    assertThat(operatingSystem.getName()).isEqualTo("win");
    assertThat(operatingSystem.getVersion()).isEqualTo("7");
    assertThat(operatingSystem.getArchitecture()).isEqualTo("x86_64");
    assertThat(operatingSystem.getDistribution()).isNull();
    assertThat(operatingSystem.getDistributionVersion()).isNull();
  }


  @Test
  public void testCreateLinuxOperatingSystem() {
    when(linuxProperties.getDistribution()).thenReturn("ubuntu");
    when(linuxProperties.getDistributionVersion()).thenReturn("16.04");

    OperatingSystem operatingSystem = createMockOs("linux", "4", "i686");
    assertThat(operatingSystem.getName()).isEqualTo("linux");
    assertThat(operatingSystem.getVersion()).isEqualTo("4");
    assertThat(operatingSystem.getArchitecture()).isEqualTo("i686");
    assertThat(operatingSystem.getDistribution()).isEqualTo("ubuntu");
    assertThat(operatingSystem.getDistributionVersion()).isEqualTo("16.04");
  }

  private OperatingSystem createMockOs(String name, String version, String arch) {
    when(systemProperties.getOsName()).thenReturn(name);
    when(systemProperties.getOsVersion()).thenReturn(version);
    when(systemProperties.getOsArch()).thenReturn(arch);
    return operatingSystemFactory.create();
  }
}
