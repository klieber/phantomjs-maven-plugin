package com.github.klieber.phantomjs.locate;

import com.github.klieber.phantomjs.os.OperatingSystem;
import com.github.klieber.phantomjs.os.OperatingSystemFactory;
import com.github.klieber.phantomjs.archive.PhantomJSArchive;
import com.github.klieber.phantomjs.archive.PhantomJSArchiveBuilder;
import com.github.klieber.phantomjs.cache.CachedArtifact;
import com.github.klieber.phantomjs.cache.CachedFile;
import com.github.klieber.phantomjs.download.Downloader;
import com.github.klieber.phantomjs.download.RepositoryDownloader;
import com.github.klieber.phantomjs.download.RuleBasedDownloader;
import com.github.klieber.phantomjs.download.WebDownloader;
import com.github.klieber.phantomjs.extract.Extractor;
import com.github.klieber.phantomjs.extract.PhantomJsExtractor;
import com.github.klieber.phantomjs.install.Installer;
import com.github.klieber.phantomjs.install.PhantomJsInstaller;
import com.github.klieber.phantomjs.resolve.PhantomJsBinaryResolver;
import com.github.klieber.phantomjs.util.ArtifactBuilder;
import com.github.klieber.phantomjs.util.Predicate;
import com.github.klieber.phantomjs.util.Predicates;
import com.github.klieber.phantomjs.util.VersionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhantomJsLocator implements Locator {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhantomJsLocator.class);

  private final PhantomJsLocatorOptions options;
  private final RepositoryDetails repositoryDetails;

  private static final String LEGACY_VERSION = "1.9.2";

  private static final Predicate<String> IS_LEGACY_VERSION = new Predicate<String>() {
    @Override
    public boolean apply(String version) {
      return VersionUtil.isGreaterThanOrEqualTo(LEGACY_VERSION, version);
    }
  };

  private static final String GOOGLE_CODE = "https://phantomjs.googlecode.com/files/";
  private static final String BITBUCKET = "https://bitbucket.org/ariya/phantomjs/downloads/";

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

    Installer installer = new PhantomJsInstaller(downloader, extractor, outputDirectory);

    return new ArchiveLocator(installer, archive);
  }

  private Downloader getDownloader(PhantomJSArchive phantomJSArchive) {
    ArtifactBuilder artifactBuilder = new ArtifactBuilder();
    CachedFile cachedFile = new CachedArtifact(phantomJSArchive, artifactBuilder, repositoryDetails.getRepositorySystemSession());
    Downloader downloader;
    if (PhantomJsLocatorOptions.Source.REPOSITORY.equals(options.getSource())) {
      downloader = new RepositoryDownloader(artifactBuilder, repositoryDetails);
    } else if (options.getBaseUrl() == null) {
      Map<Downloader, Predicate<String>> rules = new HashMap<Downloader, Predicate<String>>();
      rules.put(new WebDownloader(GOOGLE_CODE, cachedFile.getFile()),IS_LEGACY_VERSION);
      rules.put(new WebDownloader(BITBUCKET, cachedFile.getFile()), Predicates.not(IS_LEGACY_VERSION));
      downloader = new RuleBasedDownloader(rules);
    } else {
      downloader = new WebDownloader(options.getBaseUrl(), cachedFile.getFile());
    }
    return downloader;
  }

  private PhantomJSArchive getPhantomJsArchive() {
    OperatingSystemFactory operatingSystemFactory = new OperatingSystemFactory();
    OperatingSystem operatingSystem = operatingSystemFactory.create();
    return new PhantomJSArchiveBuilder(operatingSystem, options.getVersion()).build();
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
