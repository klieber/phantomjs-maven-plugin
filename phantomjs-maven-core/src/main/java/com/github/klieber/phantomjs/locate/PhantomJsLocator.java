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
package com.github.klieber.phantomjs.locate;

import com.github.klieber.phantomjs.archive.PhantomJSArchive;
import com.github.klieber.phantomjs.archive.PhantomJSArchiveBuilder;
import com.github.klieber.phantomjs.archive.UserProvidedBaseUrlPhantomJSArchive;
import com.github.klieber.phantomjs.cache.CachedArtifact;
import com.github.klieber.phantomjs.cache.CachedFile;
import com.github.klieber.phantomjs.download.Downloader;
import com.github.klieber.phantomjs.download.RepositoryDownloader;
import com.github.klieber.phantomjs.download.WebDownloader;
import com.github.klieber.phantomjs.extract.Extractor;
import com.github.klieber.phantomjs.extract.PhantomJsExtractor;
import com.github.klieber.phantomjs.install.Installer;
import com.github.klieber.phantomjs.install.PhantomJsInstaller;
import com.github.klieber.phantomjs.os.OperatingSystem;
import com.github.klieber.phantomjs.os.OperatingSystemFactory;
import com.github.klieber.phantomjs.resolve.PhantomJsBinaryResolver;
import com.github.klieber.phantomjs.util.ArtifactBuilder;
import com.github.klieber.phantomjs.util.VersionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhantomJsLocator implements Locator {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhantomJsLocator.class);

  private final PhantomJsLocatorOptions options;
  private final RepositoryDetails repositoryDetails;

  public PhantomJsLocator(PhantomJsLocatorOptions options,
                          RepositoryDetails repositoryDetails) {
    this.options = options;
    this.repositoryDetails = repositoryDetails;
  }

  @Override
  public String locate() {
    Locator locator = new CompositeLocator(getLocators());
    return locator.locate();
  }

  private List<Locator> getLocators() {
    List<Locator> locators = new ArrayList<Locator>();
    if (this.options.isCheckSystemPath()) {
      locators.add(getPathLocator(getVersionSpec()));
    }
    locators.add(getArchiveLocator(getPhantomJsArchive(), this.options.getOutputDirectory()));
    return locators;
  }

  private Locator getPathLocator(String versionSpec) {
    String systemPath = System.getenv("PATH");
    List<String> paths = Arrays.asList(systemPath.split(File.pathSeparator));
    return new PathLocator(new PhantomJsBinaryResolver(versionSpec), paths);
  }

  private Locator getArchiveLocator(PhantomJSArchive archive, File outputDirectory) {

    Downloader downloader = getDownloader(archive);

    Extractor extractor = new PhantomJsExtractor(archive);

    Installer installer = new PhantomJsInstaller(downloader, extractor, outputDirectory);

    return new ArchiveLocator(installer, archive);
  }

  private Downloader getDownloader(PhantomJSArchive phantomJSArchive) {
    ArtifactBuilder artifactBuilder = new ArtifactBuilder();
    Downloader downloader;
    if (PhantomJsLocatorOptions.Source.REPOSITORY.equals(options.getSource())) {
      downloader = new RepositoryDownloader(artifactBuilder, repositoryDetails);
    } else {
      CachedFile cachedFile = new CachedArtifact(
        phantomJSArchive,
        artifactBuilder,
        repositoryDetails.getRepositorySystemSession()
      );
      downloader = new WebDownloader(cachedFile.getFile());
    }
    return downloader;
  }

  private PhantomJSArchive getPhantomJsArchive() {
    OperatingSystemFactory operatingSystemFactory = new OperatingSystemFactory();
    OperatingSystem operatingSystem = operatingSystemFactory.create();
    PhantomJSArchive phantomJSArchive = new PhantomJSArchiveBuilder(operatingSystem, options.getVersion()).build();

    if (options.getBaseUrl() != null) {
      phantomJSArchive = new UserProvidedBaseUrlPhantomJSArchive(phantomJSArchive, options.getBaseUrl());
    }

    return phantomJSArchive;
  }

  private String getVersionSpec() {
    String version = this.options.getVersion();
    String spec = this.options.getEnforceVersion();

    if (spec == null || Boolean.parseBoolean(spec)) {
      spec = "[" + version + "]";
    } else if (Boolean.FALSE.toString().equalsIgnoreCase(spec)) {
      spec = "[0,]";
    } else if (!VersionUtil.isWithin(version, spec)) {
      LOGGER.warn("Version {} is not within requested range: {}", version, spec);
    }
    return spec;
  }
}
