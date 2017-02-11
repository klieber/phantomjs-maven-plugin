/*
 * Copyright (c) 2014 Kyle Lieber
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
package com.github.klieber.phantomjs.install;

import com.github.klieber.phantomjs.archive.PhantomJSArchive;
import com.github.klieber.phantomjs.download.DownloadException;
import com.github.klieber.phantomjs.download.Downloader;
import com.github.klieber.phantomjs.extract.ExtractionException;
import com.github.klieber.phantomjs.extract.Extractor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PhantomJSArchive.class)
public class PhantomJsInstallerTest {

  private static final String EXTRACT_TO_PATH = "phantomjs";

  private File phantomJsBinary;

  private File outputDirectory;

  @Mock
  private PhantomJSArchive phantomJSArchive;

  @Mock
  private File archive;

  @Mock
  private Downloader downloader;

  @Mock
  private Extractor extractor;

  @Captor
  private ArgumentCaptor<File> extractToFile;

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private PhantomJsInstaller phantomJsInstaller;

  @Before
  public void before() throws IOException {
    outputDirectory = temporaryFolder.getRoot();
    phantomJsInstaller = new PhantomJsInstaller(downloader,extractor, outputDirectory);
    phantomJsBinary = new File(outputDirectory, EXTRACT_TO_PATH);
  }

  @Test
  public void shouldDownloadAndExtract() throws Exception {
    when(downloader.download(phantomJSArchive)).thenReturn(archive);

    when(phantomJSArchive.getPathToExecutable()).thenReturn(EXTRACT_TO_PATH);

    assertEquals(phantomJsBinary.getAbsolutePath(), phantomJsInstaller.install(phantomJSArchive));

    verify(extractor).extract(same(archive),extractToFile.capture());

    assertEquals(phantomJsBinary, extractToFile.getValue());
  }

  @Test
  public void shouldReturnPreviouslyInstalledPath() throws Exception {
    phantomJsBinary = temporaryFolder.newFile(EXTRACT_TO_PATH);

    when(phantomJSArchive.getPathToExecutable()).thenReturn(EXTRACT_TO_PATH);

    assertEquals(phantomJsBinary.getAbsolutePath(), phantomJsInstaller.install(phantomJSArchive));

    verifyNoMoreInteractions(downloader, extractor);
  }

  @Test
  public void shouldHandleDownloadException() throws Exception {

    when(phantomJSArchive.getPathToExecutable()).thenReturn(EXTRACT_TO_PATH);
    when(downloader.download(phantomJSArchive)).thenThrow(new DownloadException("error"));

    catchException(phantomJsInstaller).install(phantomJSArchive);
    assertThat(caughtException(), is(instanceOf(InstallationException.class)));
  }

  @Test
  public void shouldHandleExtractionException() throws Exception {

    when(phantomJSArchive.getPathToExecutable()).thenReturn(EXTRACT_TO_PATH);

    when(downloader.download(phantomJSArchive)).thenReturn(archive);

    ExtractionException exception = new ExtractionException("error", new RuntimeException());
    doThrow(exception).when(extractor).extract(same(archive), any(File.class));

    catchException(phantomJsInstaller).install(phantomJSArchive);
    assertThat(caughtException(), is(instanceOf(InstallationException.class)));
  }
}
