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

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ArchiveResolverFactory {

  private final InstallerFactory installerFactory;
  private final DownloaderFactory downloaderFactory;
  private final ArchiveFactory archiveFactory;

  @Inject
  public ArchiveResolverFactory(InstallerFactory installerFactory,
                                DownloaderFactory downloaderFactory,
                                ArchiveFactory archiveFactory) {
    this.installerFactory = installerFactory;
    this.downloaderFactory = downloaderFactory;
    this.archiveFactory = archiveFactory;
  }

  public Resolver create(PhantomJsResolverOptions options, RepositoryDetails repositoryDetails) {
    Archive archive = archiveFactory.create(options.getVersion(), options.getBaseUrl());
    Installer installer = createInstaller(options, repositoryDetails);
    return new ArchiveResolver(installer, archive);
  }

  private Installer createInstaller(PhantomJsResolverOptions options, RepositoryDetails repositoryDetails) {
    Downloader downloader = downloaderFactory.create(options.getSource(), repositoryDetails);
    return installerFactory.create(downloader, options.getOutputDirectory());
  }
}
