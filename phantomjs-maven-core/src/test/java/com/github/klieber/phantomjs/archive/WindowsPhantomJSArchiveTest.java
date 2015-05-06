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

public class WindowsPhantomJSArchiveTest {

  private WindowsPhantomJSArchive archive;

  @Before
  public void before() {
    archive = new WindowsPhantomJSArchive("1.9.2");
  }

  @Test
  public void testGetExtension() {
    assertEquals("zip",archive.getExtension());
  }

  @Test
  public void testGetExecutable() {
    assertEquals("phantomjs.exe", archive.getExecutable());
    assertEquals("bin/phantomjs.exe", new WindowsPhantomJSArchive("2.0.0").getExecutable());
    assertEquals("bin/phantomjs.exe", new WindowsPhantomJSArchive("2.3.0").getExecutable());
  }

  @Test
  public void testGetPlatform() {
    assertEquals("windows",archive.getPlatform());
  }
}
