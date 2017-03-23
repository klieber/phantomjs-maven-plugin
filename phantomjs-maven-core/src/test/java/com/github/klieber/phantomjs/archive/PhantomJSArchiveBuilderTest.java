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
package com.github.klieber.phantomjs.archive;

import com.github.klieber.phantomjs.os.OperatingSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhantomJSArchiveBuilderTest {

  @Mock
  private OperatingSystem operatingSystem;

  private PhantomJSArchiveBuilder builder;

  @Test
  public void testBuildWindowsPhantomJSArchive() {
    when(operatingSystem.getName()).thenReturn("win");
    builder = new PhantomJSArchiveBuilder(operatingSystem, "1.9.0");
    PhantomJSArchive archive = builder.build();
    assertThat(archive.getArchiveName()).isEqualTo("phantomjs-1.9.0-windows.zip");
  }

  @Test
  public void testBuildMacOSXPhantomJSArchive() {
    when(operatingSystem.getName()).thenReturn("mac");
    builder = new PhantomJSArchiveBuilder(operatingSystem, "1.9.0");
    PhantomJSArchive archive = builder.build();
    assertThat(archive.getArchiveName()).isEqualTo("phantomjs-1.9.0-macosx.zip");
  }

  @Test
  public void testBuildMacOSX250PhantomJSArchive() {
    when(operatingSystem.getName()).thenReturn("mac");
    builder = new PhantomJSArchiveBuilder(operatingSystem, "2.5.0-beta");
    PhantomJSArchive archive = builder.build();
    assertThat(archive.getArchiveName()).isEqualTo("phantomjs-2.5.0-beta-macos.zip");
  }

  @Test
  public void testBuildLinuxPhantomJSArchive() {
    when(operatingSystem.getName()).thenReturn("linux");
    when(operatingSystem.getArchitecture()).thenReturn("i686");
    builder = new PhantomJSArchiveBuilder(operatingSystem, "1.9.0");
    PhantomJSArchive archive = builder.build();
    assertThat(archive.getArchiveName()).isEqualTo("phantomjs-1.9.0-linux-i686.tar.bz2");
  }

  @Test
  public void testBuildLinuxPhantomJSArchiveX64() {
    when(operatingSystem.getName()).thenReturn("linux");
    when(operatingSystem.getArchitecture()).thenReturn("x86_64");
    builder = new PhantomJSArchiveBuilder(operatingSystem, "1.9.2");
    PhantomJSArchive archive = builder.build();
    assertThat(archive.getArchiveName()).isEqualTo("phantomjs-1.9.2-linux-x86_64.tar.bz2");
  }

  @Test
  public void testBuildInvalidPlatform() {
    when(operatingSystem.getName()).thenReturn("invalid");
    builder = new PhantomJSArchiveBuilder(operatingSystem, "");
    catchException(builder).build();
    assertThat(caughtException()).isInstanceOf(UnsupportedPlatformException.class);
  }
}
