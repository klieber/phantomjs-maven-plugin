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

public class LinuxPhantomJSArchiveTest {

  private static final String ARCH = "x86_64";

  private LinuxPhantomJSArchive archive;

  @Before
  public void before() {
    archive = new LinuxPhantomJSArchive("1.9.2",ARCH);
  }

  @Test
  public void testGetExtension() {
    assertEquals("tar.bz2",archive.getExtension());
  }

  @Test
  public void testGetExecutable() {
    assertEquals("bin/phantomjs",archive.getExecutable());
  }

  @Test
  public void testGetPlatform() {
    assertEquals("linux",archive.getPlatform());
  }

  @Test
  public void testGetArch() {
    assertEquals(ARCH,archive.getArch());
  }
}
