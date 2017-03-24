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
package com.github.klieber.phantomjs.install;

import com.github.klieber.phantomjs.archive.Archive;
import com.github.klieber.phantomjs.download.DownloadException;
import com.github.klieber.phantomjs.download.Downloader;
import com.github.klieber.phantomjs.download.DownloaderFactory;
import com.github.klieber.phantomjs.extract.ArchiveExtractor;
import com.github.klieber.phantomjs.extract.ExtractionException;
import com.github.klieber.phantomjs.resolve.PhantomJsResolverOptions;
import com.github.klieber.phantomjs.resolve.RepositoryDetails;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhantomJsInstallerTest {

  private static final String EXTRACT_TO_PATH = "phantomjs";

  private File phantomJsBinary;

  private File outputDirectory;

  @Mock
  private Archive archive;

  @Mock
  private File archiveFile;

  @Mock
  private DownloaderFactory downloaderFactory;

  @Mock
  private Downloader downloader;

  @Mock
  private ArchiveExtractor extractor;

  @Mock
  private PhantomJsResolverOptions options;

  @Mock
  private RepositoryDetails repositoryDetails;

  @Captor
  private ArgumentCaptor<File> extractToFile;

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private Installer phantomJsInstaller;

  @Before
  public void before() throws IOException {
    outputDirectory = temporaryFolder.getRoot();

    when(downloaderFactory.create(options, repositoryDetails)).thenReturn(downloader);
    when(options.getOutputDirectory()).thenReturn(outputDirectory);

    phantomJsInstaller = new InstallerFactory(extractor, downloaderFactory).create(options, repositoryDetails);
    phantomJsBinary = new File(outputDirectory, EXTRACT_TO_PATH);
  }

  @Test
  public void shouldDownloadAndExtract() throws Exception {
    when(downloader.download(archive)).thenReturn(archiveFile);

    when(archive.getPathToExecutable()).thenReturn(EXTRACT_TO_PATH);

    assertThat(phantomJsInstaller.install(archive)).isEqualTo(phantomJsBinary.getAbsolutePath());

    verify(extractor).extract(same(archiveFile), eq(EXTRACT_TO_PATH), extractToFile.capture());

    assertThat(extractToFile.getValue()).isEqualTo(phantomJsBinary);
  }

  @Test
  public void shouldReturnPreviouslyInstalledPath() throws Exception {
    phantomJsBinary = temporaryFolder.newFile(EXTRACT_TO_PATH);

    when(archive.getPathToExecutable()).thenReturn(EXTRACT_TO_PATH);

    assertThat(phantomJsInstaller.install(archive)).isEqualTo(phantomJsBinary.getAbsolutePath());

    verifyNoMoreInteractions(downloader, extractor);
  }

  @Test
  public void shouldHandleDownloadException() throws Exception {

    when(archive.getPathToExecutable()).thenReturn(EXTRACT_TO_PATH);
    when(downloader.download(archive)).thenThrow(new DownloadException("error"));

    assertThatThrownBy(() -> phantomJsInstaller.install(archive))
      .isInstanceOf(InstallationException.class);
  }

  @Test
  public void shouldHandleExtractionException() throws Exception {

    when(archive.getPathToExecutable()).thenReturn(EXTRACT_TO_PATH);

    when(downloader.download(archive)).thenReturn(archiveFile);

    ExtractionException exception = new ExtractionException("error", new RuntimeException());
    doThrow(exception).when(extractor).extract(same(archiveFile), eq(EXTRACT_TO_PATH), any(File.class));

    assertThatThrownBy(() -> phantomJsInstaller.install(archive))
      .isInstanceOf(InstallationException.class);
  }
}
