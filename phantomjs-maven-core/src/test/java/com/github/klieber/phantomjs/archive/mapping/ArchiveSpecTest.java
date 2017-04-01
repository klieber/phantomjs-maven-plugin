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
package com.github.klieber.phantomjs.archive.mapping;

import com.github.klieber.phantomjs.sys.os.OperatingSystem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArchiveSpecTest {

  private static final String VERSION_SPEC = "[2.0,2.5]";

  @Mock
  private OperatingSystemSpec operatingSystemSpec;

  @Mock
  private OperatingSystem operatingSystem;

  private ArchiveSpec archiveSpec;

  @Before
  public void before() {
    archiveSpec = new ArchiveSpec(VERSION_SPEC, operatingSystemSpec);
  }

  @Test
  public void testGetVersionSpec() {
    assertThat(archiveSpec.getVersionSpec()).isEqualTo(VERSION_SPEC);
  }

  @Test
  public void testGetOperatingSystemSpec() {
    assertThat(archiveSpec.getOperatingSystemSpec()).isEqualTo(operatingSystemSpec);
  }

  @Test
  public void testMatches_OsAndVersionMatches() {
    when(operatingSystemSpec.matches(operatingSystem)).thenReturn(true);
    assertThat(archiveSpec.matches("2.0", operatingSystem)).isTrue();
  }

  @Test
  public void testMatches_OnlyVersionMatches() {
    assertThat(archiveSpec.matches("2.0", operatingSystem)).isFalse();
  }

  @Test
  public void testMatches_NoMatches() {
    assertThat(archiveSpec.matches("1.5", operatingSystem)).isFalse();
  }
}
