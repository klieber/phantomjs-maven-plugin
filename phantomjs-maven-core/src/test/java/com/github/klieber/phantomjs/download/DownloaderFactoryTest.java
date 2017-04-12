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

import com.github.klieber.phantomjs.cache.ArchiveCacheFactory;
import com.github.klieber.phantomjs.resolve.PhantomJsResolverOptions;
import com.github.klieber.phantomjs.resolve.RepositoryDetails;
import com.github.klieber.phantomjs.util.ArtifactBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DownloaderFactoryTest {

  @Mock
  private ArtifactBuilder artifactBuilder;

  @Mock
  private ArchiveCacheFactory archiveCacheFactory;

  @Mock
  private PhantomJsResolverOptions options;

  @Mock
  private RepositoryDetails repositoryDetails;

  @InjectMocks
  private DownloaderFactory downloaderFactory;

  @Test
  public void testGetDownloader_RepositoryDownloader() {
    assertThat(downloaderFactory.create(PhantomJsResolverOptions.Source.REPOSITORY, repositoryDetails))
      .isInstanceOf(AetherDownloader.class);
  }

  @Test
  public void testGetDownloader_WebDownloader() {
    assertThat(downloaderFactory.create(PhantomJsResolverOptions.Source.URL, repositoryDetails))
      .isInstanceOf(WebDownloader.class);
  }

}
