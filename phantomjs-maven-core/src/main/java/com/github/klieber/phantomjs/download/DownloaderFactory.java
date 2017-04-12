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

import com.github.klieber.phantomjs.cache.ArchiveCache;
import com.github.klieber.phantomjs.cache.ArchiveCacheFactory;
import com.github.klieber.phantomjs.resolve.PhantomJsResolverOptions;
import com.github.klieber.phantomjs.resolve.RepositoryDetails;
import com.github.klieber.phantomjs.util.ArtifactBuilder;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DownloaderFactory {

  private final ArtifactBuilder artifactBuilder;
  private final ArchiveCacheFactory archiveCacheFactory;

  @Inject
  public DownloaderFactory(ArtifactBuilder artifactBuilder,
                           ArchiveCacheFactory archiveCacheFactory) {
    this.archiveCacheFactory = archiveCacheFactory;
    this.artifactBuilder = artifactBuilder;
  }

  public Downloader create(PhantomJsResolverOptions.Source source,
                           RepositoryDetails repositoryDetails) {
    return isRepositorySource(source) ?
      createAetherDownloader(repositoryDetails) : createWebDownloader(repositoryDetails);
  }

  private boolean isRepositorySource(PhantomJsResolverOptions.Source source) {
    return PhantomJsResolverOptions.Source.REPOSITORY.equals(source);
  }

  private Downloader createAetherDownloader(RepositoryDetails repositoryDetails) {
    return new AetherDownloader(artifactBuilder, repositoryDetails);
  }

  private Downloader createWebDownloader(RepositoryDetails repositoryDetails) {
    return new WebDownloader(createArchiveCache(repositoryDetails));
  }

  private ArchiveCache createArchiveCache(RepositoryDetails repositoryDetails) {
    return archiveCacheFactory.create(repositoryDetails.getRepositorySystemSession());
  }
}
