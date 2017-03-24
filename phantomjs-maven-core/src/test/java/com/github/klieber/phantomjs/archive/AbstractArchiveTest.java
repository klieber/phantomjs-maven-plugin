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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractArchiveTest {

  private static final String BASE_URL = "http://example.org/files";
  private static final String ARCHIVE_NAME = "phantomjs-2.0.zip";

  private static final String FULL_URL = BASE_URL + '/' + ARCHIVE_NAME;

  @Test
  public void getUrlWithNoSlashAtEndOfBaseUrl() {
    AbstractArchive archive = new MockArchive(BASE_URL, ARCHIVE_NAME);
    assertThat(archive.getUrl()).isEqualTo(FULL_URL);
  }

  @Test
  public void getUrlWithSlashAtEndOfBaseUrl() {
    AbstractArchive archive = new MockArchive(BASE_URL+'/', ARCHIVE_NAME);
    assertThat(archive.getUrl()).isEqualTo(FULL_URL);
  }

  static class MockArchive extends AbstractArchive {

    private final String baseUrl;
    private final String archiveName;

    public MockArchive(String baseUrl, String archiveName) {

      this.baseUrl = baseUrl;
      this.archiveName = archiveName;
    }

    @Override
    protected String getBaseUrl() {
      return baseUrl;
    }

    @Override
    public String getExtension() {
      return null;
    }

    @Override
    public String getArchiveName() {
      return archiveName;
    }

    @Override
    public String getPathToExecutable() {
      return null;
    }

    @Override
    public String getVersion() {
      return null;
    }

    @Override
    public String getClassifier() {
      return null;
    }
  }
}
