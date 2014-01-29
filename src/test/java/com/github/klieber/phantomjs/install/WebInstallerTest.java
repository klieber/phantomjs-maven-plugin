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
import com.github.klieber.phantomjs.cache.CachedFile;
import com.github.klieber.phantomjs.config.Configuration;
import com.github.klieber.phantomjs.download.DownloadException;
import com.github.klieber.phantomjs.download.Downloader;
import com.github.klieber.phantomjs.extract.ExtractionException;
import com.github.klieber.phantomjs.extract.Extractor;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PhantomJSArchive.class)
public class WebInstallerTest {

  private static final String PROJECT_ROOT = System.getProperty("user.dir");
  private static final String OUTPUT_DIRECTORY = PROJECT_ROOT+"/target";
  private static final String EXTRACT_TO_PATH = "phantomjs";

  private File phantomJsBinary;

  private File outputDirectory;

  @Mock
  private PhantomJSArchive phantomJSArchive;

  @Mock
  private File archive;

  @Mock
  private Configuration config;

  @Mock
  private CachedFile cachedFile;

  @Mock
  private Downloader downloader;

  @Mock
  private Extractor extractor;

  @Captor
  private ArgumentCaptor<File> extractToFile;

  private WebInstaller webInstaller;

  @Before
  public void before() {
    webInstaller = new WebInstaller(config,cachedFile,downloader,extractor);
    outputDirectory = new File(OUTPUT_DIRECTORY);
    phantomJsBinary = new File(outputDirectory, EXTRACT_TO_PATH);
  }

  @Test
  public void shouldDownloadAndExtract() throws Exception {
    when(config.getPhantomJsArchive()).thenReturn(phantomJSArchive);
    when(config.getOutputDirectory()).thenReturn(outputDirectory);

    when(phantomJSArchive.getExtractToPath()).thenReturn(EXTRACT_TO_PATH);
    when(cachedFile.getFile()).thenReturn(archive);

    assertEquals(phantomJsBinary.getAbsolutePath(), webInstaller.install());

    verify(downloader).download(phantomJSArchive, archive);
    verify(extractor).extract(same(archive),extractToFile.capture());

    assertEquals(phantomJsBinary, extractToFile.getValue());
  }


  @Test
  public void shouldExtract() throws Exception  {
    when(config.getPhantomJsArchive()).thenReturn(phantomJSArchive);
    when(config.getOutputDirectory()).thenReturn(outputDirectory);

    when(phantomJSArchive.getExtractToPath()).thenReturn(EXTRACT_TO_PATH);
    when(cachedFile.getFile()).thenReturn(archive);
    when(archive.exists()).thenReturn(true);

    assertEquals(phantomJsBinary.getAbsolutePath(), webInstaller.install());

    verifyNoMoreInteractions(downloader);
    verify(extractor).extract(same(archive),extractToFile.capture());

    assertEquals(phantomJsBinary, extractToFile.getValue());
  }

  @Test
  public void shouldReturnPreviouslyInstalledPath() throws Exception {
    // Create temporary file
    if (!phantomJsBinary.exists()) {
      phantomJsBinary.createNewFile();
    }

    when(config.getPhantomJsArchive()).thenReturn(phantomJSArchive);
    when(config.getOutputDirectory()).thenReturn(outputDirectory);

    when(phantomJSArchive.getExtractToPath()).thenReturn(EXTRACT_TO_PATH);
    when(cachedFile.getFile()).thenReturn(archive);
    when(archive.exists()).thenReturn(true);

    assertEquals(phantomJsBinary.getAbsolutePath(), webInstaller.install());

    verifyNoMoreInteractions(downloader, extractor);

    // Cleanup temporary file
    if (phantomJsBinary != null && phantomJsBinary.exists()) {
      phantomJsBinary.delete();
    }
  }

  @Test
  public void shouldHandleDownloadException() throws Exception {
    when(config.getPhantomJsArchive()).thenReturn(phantomJSArchive);
    when(config.getOutputDirectory()).thenReturn(outputDirectory);

    when(phantomJSArchive.getExtractToPath()).thenReturn(EXTRACT_TO_PATH);
    when(cachedFile.getFile()).thenReturn(archive);
    doThrow(new DownloadException("error")).when(downloader).download(phantomJSArchive, archive);

    catchException(webInstaller).install();
    assertThat(caughtException(), is(instanceOf(InstallationException.class)));
  }

  @Test
  public void shouldHandleExtractionException() throws Exception {
    when(config.getPhantomJsArchive()).thenReturn(phantomJSArchive);
    when(config.getOutputDirectory()).thenReturn(outputDirectory);

    when(phantomJSArchive.getExtractToPath()).thenReturn(EXTRACT_TO_PATH);
    when(cachedFile.getFile()).thenReturn(archive);

    ExtractionException exception = new ExtractionException("error", new RuntimeException());
    doThrow(exception).when(extractor).extract(same(archive), any(File.class));

    catchException(webInstaller).install();
    assertThat(caughtException(), is(instanceOf(InstallationException.class)));
  }
}
