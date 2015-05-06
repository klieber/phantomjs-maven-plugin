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
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PhantomJSArchiveTest {

  private static final String VERSION = "1.9.2";
  private static final String EXTENSION = "zip";
  private static final String PLATFORM = "windows";
  private static final String EXECUTABLE = "phantomjs.exe";
  private static final String ARCH = "i686";

  private static final String ARCHIVE_NAME_WITHOUT_EXTENSION = "phantomjs-"+VERSION+"-"+PLATFORM;
  private static final String ARCHIVE_NAME = ARCHIVE_NAME_WITHOUT_EXTENSION + "."+EXTENSION;

  private PhantomJSArchive archive;

  @Before
  public void before() {
    archive = createPhantomJSArchive();
  }

  @Test
  public void testGetArchiveName() {
    assertEquals(ARCHIVE_NAME, archive.getArchiveName());
  }

  @Test
  public void testGetPathToExecutable() {
    assertEquals(ARCHIVE_NAME_WITHOUT_EXTENSION+"/"+EXECUTABLE,archive.getPathToExecutable());
  }

  @Test
  public void testGetExtractToPath() {
    assertEquals(ARCHIVE_NAME_WITHOUT_EXTENSION+"/"+EXECUTABLE,archive.getExtractToPath());
  }

  @Test
  public void testGetExtractToPathWithArch() {
    archive = createPhantomJSArchive(ARCH);
    assertEquals(ARCHIVE_NAME_WITHOUT_EXTENSION+"-"+ARCH+"/"+EXECUTABLE,archive.getExtractToPath());
  }

  @Test
  public void testGetVersion() {
    archive = createPhantomJSArchive();
    assertEquals(VERSION,archive.getVersion());
  }

  @Test
  public void testGetClassifier() {
    archive = createPhantomJSArchive();
    assertEquals(PLATFORM,archive.getClassifier());
    archive = createPhantomJSArchive(ARCH);
    assertEquals(PLATFORM+"-"+ARCH,archive.getClassifier());
  }

  private static PhantomJSArchive createPhantomJSArchive() {
    return new PhantomJSArchive(VERSION) {
      @Override
      public String getExtension() {
        return EXTENSION;
      }

      @Override
      protected String getPlatform() {
        return PLATFORM;
      }

      @Override
      protected String getExecutable() {
        return EXECUTABLE;
      }
    };
  }

  private static PhantomJSArchive createPhantomJSArchive(final String arch) {
    return new PhantomJSArchive(VERSION) {
      @Override
      public String getExtension() {
        return EXTENSION;
      }

      @Override
      protected String getPlatform() {
        return PLATFORM;
      }

      @Override
      protected String getExecutable() {
        return EXECUTABLE;
      }

      @Override
      protected String getArch() {
        return arch;
      }
    };
  }
}
