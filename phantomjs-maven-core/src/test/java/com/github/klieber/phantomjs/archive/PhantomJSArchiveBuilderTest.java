/*
 * Copyright (c) 2013 Kyle Lieber
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.klieber.phantomjs.archive;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.apis.CatchExceptionHamcrestMatchers.hasMessage;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PhantomJSArchiveBuilderTest {

  private PhantomJSArchiveBuilder builder;

  @Test
  public void testBuildWindowsPhantomJSArchive() {
    builder = new PhantomJSArchiveBuilder("win","","");
    PhantomJSArchive archive = builder.build();
    assertThat(archive,is(instanceOf(WindowsPhantomJSArchive.class)));
  }

  @Test
  public void testBuildMacOSXPhantomJSArchive() {
    builder = new PhantomJSArchiveBuilder("mac","","");
    PhantomJSArchive archive = builder.build();
    assertThat(archive,is(instanceOf(MacOSXPhantomJSArchive.class)));
  }

  @Test
  public void testBuildLinuxPhantomJSArchive() {
    builder = new PhantomJSArchiveBuilder("linux","","");
    PhantomJSArchive archive = builder.build();
    assertThat(archive,is(instanceOf(LinuxPhantomJSArchive.class)));
    assertEquals("i686", archive.getArch());
  }

  @Test
  public void testBuildLinuxPhantomJSArchiveX64() {
    builder = new PhantomJSArchiveBuilder("linux","64","1.9.2");
    PhantomJSArchive archive = builder.build();
    assertThat(archive,is(instanceOf(LinuxPhantomJSArchive.class)));
    assertEquals("x86_64",archive.getArch());
  }

  @Test
  public void testBuildInvalidPlatform() {
    builder = new PhantomJSArchiveBuilder("invalid","","");
    catchException(builder).build();
    assertThat(caughtException(), allOf(
        is(instanceOf(IllegalArgumentException.class)),
        hasMessage("unknown platform: invalid")
    ));
  }

  @Test
  public void testConstructWithSystemValues() {
    builder = new PhantomJSArchiveBuilder("1.9.2");
    assertEquals(System.getProperty("os.name").toLowerCase(), Whitebox.getInternalState(builder,"platform"));
    assertEquals(System.getProperty("os.arch").toLowerCase(), Whitebox.getInternalState(builder,"arch"));
  }
}
