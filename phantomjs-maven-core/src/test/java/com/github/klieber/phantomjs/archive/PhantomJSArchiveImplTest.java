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

import com.github.klieber.phantomjs.archive.mapping.ArchiveFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhantomJSArchiveImplTest {

  private static final String VERSION = "1.9.2";
  private static final String EXTENSION = "zip";
  private static final String CLASSIFIER = "classifier";

  private static final String ARCHIVE_NAME_WITHOUT_EXTENSION = "phantomjs-"+VERSION+"-"+CLASSIFIER;
  private static final String ARCHIVE_NAME = ARCHIVE_NAME_WITHOUT_EXTENSION + "."+EXTENSION;
  private static final String PATH_TO_EXECUTABLE = ARCHIVE_NAME_WITHOUT_EXTENSION+"/bin/phantomjs";
  private static final String ARCHIVE_FILE_TEMPLATE = "phantomjs-{version}-{classifier}.{extension}";
  private static final String EXECUTABLE_TEMPLATE = "phantomjs-{version}-{classifier}/bin/phantomjs";

  @Mock
  private ArchiveFormat archiveFormat;

  private PhantomJSArchiveImpl archive;

  @Before
  public void before() {
    archive = new PhantomJSArchiveImpl(archiveFormat, VERSION);
  }

  @Test
  public void testGetExtension() {
    when(archiveFormat.getExtension()).thenReturn(EXTENSION);
    assertThat(archive.getExtension()).isEqualTo(EXTENSION);
  }

  @Test
  public void testGetArchiveName() {
    when(archiveFormat.getFileTemplate()).thenReturn(ARCHIVE_FILE_TEMPLATE);
    when(archiveFormat.getClassifier()).thenReturn(CLASSIFIER);
    when(archiveFormat.getExtension()).thenReturn(EXTENSION);
    assertThat(archive.getArchiveName()).isEqualTo(ARCHIVE_NAME);
  }

  @Test
  public void testGetPathToExecutable() {
    when(archiveFormat.getExecutableTemplate()).thenReturn(EXECUTABLE_TEMPLATE);
    when(archiveFormat.getClassifier()).thenReturn(CLASSIFIER);
    when(archiveFormat.getExtension()).thenReturn(EXTENSION);
    assertThat(archive.getPathToExecutable()).isEqualTo(PATH_TO_EXECUTABLE);
  }

  @Test
  public void testGetVersion() {
    assertThat(archive.getVersion()).isEqualTo(VERSION);
  }

  @Test
  public void testGetClassifier() {
    when(archiveFormat.getClassifier()).thenReturn(CLASSIFIER);
    assertThat(archive.getClassifier()).isEqualTo(CLASSIFIER);
  }
}
