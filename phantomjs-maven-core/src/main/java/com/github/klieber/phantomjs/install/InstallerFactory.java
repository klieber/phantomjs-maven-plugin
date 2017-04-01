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

import com.github.klieber.phantomjs.download.Downloader;
import com.github.klieber.phantomjs.download.DownloaderFactory;
import com.github.klieber.phantomjs.extract.ArchiveExtractor;
import com.github.klieber.phantomjs.resolve.PhantomJsResolverOptions;
import com.github.klieber.phantomjs.resolve.RepositoryDetails;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class InstallerFactory {

  private final ArchiveExtractor archiveExtractor;
  private final DownloaderFactory downloaderFactory;

  @Inject
  public InstallerFactory(ArchiveExtractor archiveExtractor,
                          DownloaderFactory downloaderFactory) {
    this.archiveExtractor = archiveExtractor;
    this.downloaderFactory = downloaderFactory;
  }

  public Installer create(PhantomJsResolverOptions options,
                          RepositoryDetails repositoryDetails) {
    Downloader downloader = downloaderFactory.create(options, repositoryDetails);
    return new PhantomJsInstaller(downloader, archiveExtractor, options.getOutputDirectory());
  }
}
