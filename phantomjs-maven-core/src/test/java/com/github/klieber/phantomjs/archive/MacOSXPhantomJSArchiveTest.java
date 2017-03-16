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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MacOSXPhantomJSArchiveTest {

  private MacOSXPhantomJSArchive archive;

  @Before
  public void before() {
    archive = new MacOSXPhantomJSArchive("1.9.2");
  }

  @Test
  public void testGetExtension() {
    assertEquals("zip",archive.getExtension());
  }

  @Test
  public void testGetExecutable() {
    assertEquals("bin/phantomjs",archive.getExecutable());
  }

  @Test
  public void testGetPlatform() {
    assertEquals("macosx",archive.getPlatform(null));
  }

  @Test
  public void testGetPlatformLowerThan25() {
    assertEquals("macosx",archive.getPlatform("2.1.1"));
  }

  @Test
  public void testGetPlatformNotSemver() {
    assertEquals("macosx",archive.getPlatform("2.5"));
    assertEquals("macosx",archive.getPlatform("test"));
    assertEquals("macosx",archive.getPlatform(null));
  }

  @Test
  public void testGetPlatformGreaterOrEqual25() {
    assertEquals("macos",archive.getPlatform("2.5.0"));
    assertEquals("macos",archive.getPlatform("2.6.0"));
    assertEquals("macos",archive.getPlatform("2.5.0-beta"));
    assertEquals("macos",archive.getPlatform("3.0.0"));
  }
}
