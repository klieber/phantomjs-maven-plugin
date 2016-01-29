package com.github.klieber.phantomjs.locate;

import com.github.klieber.phantomjs.archive.PhantomJSArchive;
import com.github.klieber.phantomjs.archive.PhantomJSArchiveBuilder;
import com.github.klieber.phantomjs.download.Downloader;
import com.github.klieber.phantomjs.download.PhantomJsArchiveDownloader;
import com.github.klieber.phantomjs.extract.Extractor;
import com.github.klieber.phantomjs.extract.PhantomJsExtractor;
import com.github.klieber.phantomjs.install.Installer;
import com.github.klieber.phantomjs.install.PhantomJsInstaller;
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
    locators.add(getArchiveLocator(getPhantomJsArchive(),this.options.getOutputDirectory()));
    return locators;
  }

  private Locator getPathLocator(String versionSpec) {
    String systemPath = System.getenv("PATH");
    List<String> paths = Arrays.asList(systemPath.split(File.pathSeparator));
    return new PathLocator(new PhantomJsBinaryResolver(versionSpec),paths);
  }

  private Locator getArchiveLocator(PhantomJSArchive archive, File outputDirectory) {

    Downloader downloader = getDownloader(archive);

    Extractor extractor = new PhantomJsExtractor(archive);

    Installer installer = new PhantomJsInstaller(downloader, extractor);

    return new ArchiveLocator(installer, archive, outputDirectory);
  }

  private Downloader getDownloader(PhantomJSArchive phantomJSArchive) {
    return new PhantomJsArchiveDownloader(
        this.options,
        this.repositoryDetails,
        new ArtifactBuilder(),
        phantomJSArchive
    );
  }

  private PhantomJSArchive getPhantomJsArchive() {
    return new PhantomJSArchiveBuilder(options.getVersion()).build();
  }

  private String getVersionSpec() {
    String version = this.options.getVersion();
    String spec = this.options.getEnforceVersion();

    if (spec == null || Boolean.parseBoolean(spec)) {
      spec = "["+version+"]";
    } else if (Boolean.FALSE.toString().equalsIgnoreCase(spec)) {
      spec = "[0,]";
    } else if (!VersionUtil.isWithin(version, spec)) {
      LOGGER.warn("Version {} is not within requested range: {}", version, spec);
    }
    return spec;
  }
}
