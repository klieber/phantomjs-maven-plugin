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
package com.github.klieber.phantomjs.extract;

import com.github.klieber.phantomjs.archive.PhantomJSArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PhantomJSArchive.class)
public class PhantomJsExtractorTest {

  private static final String PROJECT_ROOT = System.getProperty("user.dir");

  private static final String PATH_TO_EXECUTABLE = "bin/phantomjs";
  private static final String ARCHIVE_PATH = PROJECT_ROOT+"/src/test/config/test-archive.tar.gz";
  private static final String EXTRACT_TO_PATH = PROJECT_ROOT+"/target/temp/phantomjs";

  @Mock
  private PhantomJSArchive phantomJsArchive;

  private File archive;

  private File extractTo;

  @Mock
  private File extractToParent;

  private PhantomJsExtractor extractor;

  @Before
  public void before() {
    extractor = new PhantomJsExtractor(phantomJsArchive);
    archive = new File(ARCHIVE_PATH);
    extractTo = new File(EXTRACT_TO_PATH);
  }

  @After
  public void after() {
    if (extractTo != null && extractTo.exists()) {
      extractTo.delete();
    }
  }

  @Test
  public void shouldExtract() throws Exception {
    when(phantomJsArchive.getPathToExecutable()).thenReturn(PATH_TO_EXECUTABLE);
    extractor.extract(archive, extractTo);
    assertThat(extractTo.exists()).isTrue();
  }

  @Test
  public void shouldNotExtract() throws Exception {
    extractTo = mock(File.class);

    when(phantomJsArchive.getPathToExecutable()).thenReturn(PATH_TO_EXECUTABLE);
    when(extractTo.getParentFile()).thenReturn(extractToParent);
    when(extractTo.getAbsolutePath()).thenReturn(EXTRACT_TO_PATH);
    when(extractToParent.mkdirs()).thenReturn(false);

    extractor.extract(archive, extractTo);

    verify(extractTo, never()).setExecutable(true);
  }

  @Test
  public void shouldFailToExtract() throws Exception {
    when(phantomJsArchive.getPathToExecutable()).thenReturn(PATH_TO_EXECUTABLE);

    catchException(extractor).extract(new File(PROJECT_ROOT + "/target/doesnotexist"), extractTo);

    assertThat(caughtException()).isInstanceOf(ExtractionException.class);
  }
}
