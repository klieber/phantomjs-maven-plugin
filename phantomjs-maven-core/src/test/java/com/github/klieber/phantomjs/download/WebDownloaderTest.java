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
package com.github.klieber.phantomjs.download;

import com.github.klieber.phantomjs.archive.PhantomJSArchive;
import org.codehaus.plexus.util.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PhantomJSArchive.class,FileUtils.class})
public class WebDownloaderTest {

  private static final String BASE_URL = "http://example.org";
  private static final String FILE_PATH = "file.zip";

  @Mock
  private PhantomJSArchive phantomJSArchive;

  @Mock
  private File file;

  private WebDownloader downloader;

  @Before
  public void before() {
    mockStatic(FileUtils.class);
    downloader = new WebDownloader(BASE_URL, file);
  }

  @Test
  public void shouldDownload() throws Exception {
    when(phantomJSArchive.getArchiveName()).thenReturn(FILE_PATH);
    when(file.length()).thenReturn(100L);

    downloader.download(phantomJSArchive);

    verifyStatic();
    FileUtils.copyURLToFile(any(URL.class), eq(file));
  }



  @Test
  public void shouldFailDueToEmptyFile() throws Exception {
    when(phantomJSArchive.getArchiveName()).thenReturn(FILE_PATH);
    when(file.length()).thenReturn(0L);

    catchException(downloader).download(phantomJSArchive);
    assertThat(caughtException()).isInstanceOf(DownloadException.class);

    verifyStatic();
    FileUtils.copyURLToFile(any(URL.class), eq(file));
  }

  @Test
  public void shouldFailDueToIOException() throws Exception {
    when(phantomJSArchive.getArchiveName()).thenReturn(FILE_PATH);

    doThrow(new IOException()).when(FileUtils.class);
    FileUtils.copyURLToFile(any(URL.class), eq(file));

    catchException(downloader).download(phantomJSArchive);
    assertThat(caughtException()).isInstanceOf(DownloadException.class);

    verifyStatic();
    FileUtils.copyURLToFile(any(URL.class), eq(file));
  }

  @Test
  public void shouldFailDueToMalformedUrl() throws Exception {
    when(phantomJSArchive.getArchiveName()).thenReturn(FILE_PATH);

    downloader = new WebDownloader("invalid-base-url", file);
    catchException(downloader).download(phantomJSArchive);
    assertThat(caughtException()).isInstanceOf(DownloadException.class);
  }
}
