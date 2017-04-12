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
package com.github.klieber.phantomjs.resolve;

import com.github.klieber.phantomjs.archive.Archive;
import com.github.klieber.phantomjs.archive.ArchiveFactory;
import com.github.klieber.phantomjs.download.Downloader;
import com.github.klieber.phantomjs.download.DownloaderFactory;
import com.github.klieber.phantomjs.install.Installer;
import com.github.klieber.phantomjs.install.InstallerFactory;
import com.github.klieber.phantomjs.test.MockPhantomJsBinary;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArchiveResolverFactoryTest {

  private static final String VERSION = "2.0.0";

  private static final String BASE_URL = "http://example.org/files";

  @ClassRule
  public static final MockPhantomJsBinary phantomJsBinary = new MockPhantomJsBinary(VERSION);

  @Mock
  private InstallerFactory installerFactory;

  @Mock
  private Installer installer;

  @Mock
  private DownloaderFactory downloaderFactory;

  @Mock
  private Downloader downloader;

  @Mock
  private ArchiveFactory archiveFactory;

  @Mock
  private Archive archive;

  @Mock
  private PhantomJsResolverOptions options;

  @Mock
  private RepositoryDetails repositoryDetails;

  @Mock
  private File outputDirectory;

  @InjectMocks
  private ArchiveResolverFactory resolverFactory;

  @Test
  public void testCreate() {
    when(options.getVersion()).thenReturn(VERSION);
    when(options.getOutputDirectory()).thenReturn(outputDirectory);
    when(options.getSource()).thenReturn(PhantomJsResolverOptions.Source.REPOSITORY);
    when(options.getBaseUrl()).thenReturn(BASE_URL);

    when(archiveFactory.create(VERSION, BASE_URL)).thenReturn(archive);
    when(downloaderFactory.create(PhantomJsResolverOptions.Source.REPOSITORY, repositoryDetails))
      .thenReturn(downloader);
    when(installerFactory.create(downloader, outputDirectory)).thenReturn(installer);

    Resolver resolver = resolverFactory.create(options, repositoryDetails);
    assertThat(resolver).isInstanceOf(ArchiveResolver.class);
  }
}
