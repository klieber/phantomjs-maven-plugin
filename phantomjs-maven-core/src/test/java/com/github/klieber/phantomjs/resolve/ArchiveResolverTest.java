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
package com.github.klieber.phantomjs.resolve;

import com.github.klieber.phantomjs.archive.Archive;
import com.github.klieber.phantomjs.install.InstallationException;
import com.github.klieber.phantomjs.install.Installer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArchiveResolverTest {

  private static final String LOCATION = "the-location";

  @Mock
  private Archive archive;

  @Mock
  private Installer installer;

  private ArchiveResolver locator;

  @Before
  public void before() {
    locator = new ArchiveResolver(installer, archive);
  }

  @Test
  public void shouldLocate() throws Exception {
    when(installer.install(archive)).thenReturn(LOCATION);
    assertThat(locator.resolve()).isEqualTo(LOCATION);
  }

  @Test
  public void testLocateShouldHandleException() throws Exception {
    when(installer.install(archive)).thenThrow(new InstallationException("error", new RuntimeException()));
    assertThat(locator.resolve()).isNull();
  }
}
