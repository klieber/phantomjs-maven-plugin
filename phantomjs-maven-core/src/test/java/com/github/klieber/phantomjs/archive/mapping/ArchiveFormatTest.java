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

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArchiveFormatTest {

  private static final String BASE_URL_TEMPLATE = "http://example.org/files";
  private static final String FILE_TEMPLATE = "archive-name";
  private static final String EXECUTABLE_TEMPLATE = "archive-{version}/bin/executable";
  private static final String CLASSIFIER = "macos";
  private static final String EXTENSION = "tar.gz";

  private ArchiveFormat archiveFormat;

  @Before
  public void before() {
    archiveFormat = new ArchiveFormat(
      BASE_URL_TEMPLATE,
      FILE_TEMPLATE,
      EXECUTABLE_TEMPLATE,
      CLASSIFIER,
      EXTENSION
    );
  }
  
  @Test
  public void getBaseUrlTemplate() {
    assertThat(archiveFormat.getBaseUrlTemplate()).isEqualTo(BASE_URL_TEMPLATE);
  }

  @Test
  public void getFileTemplate() {
    assertThat(archiveFormat.getFileTemplate()).isEqualTo(FILE_TEMPLATE);
  }

  @Test
  public void getExecutableTemplate() {
    assertThat(archiveFormat.getExecutableTemplate()).isEqualTo(EXECUTABLE_TEMPLATE);
  }

  @Test
  public void getClassifier() {
    assertThat(archiveFormat.getClassifier()).isEqualTo(CLASSIFIER);
  }

  @Test
  public void getExtension() {
    assertThat(archiveFormat.getExtension()).isEqualTo(EXTENSION);
  }

}
