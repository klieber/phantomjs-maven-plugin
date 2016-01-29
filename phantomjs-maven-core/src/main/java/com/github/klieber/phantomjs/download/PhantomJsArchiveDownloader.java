package com.github.klieber.phantomjs.download;

import com.github.klieber.phantomjs.archive.PhantomJSArchive;
import com.github.klieber.phantomjs.cache.CachedArtifact;
import com.github.klieber.phantomjs.cache.CachedFile;
import com.github.klieber.phantomjs.locate.PhantomJsLocatorOptions;
import com.github.klieber.phantomjs.locate.RepositoryDetails;
import com.github.klieber.phantomjs.util.ArtifactBuilder;
import com.github.klieber.phantomjs.util.VersionUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by klieber on 1/28/16.
 */
public class PhantomJsArchiveDownloader implements Downloader {

  private static final String UNABLE_TO_DOWNLOAD = "Unable to download phantomjs binary from ";


  private static final String LEGACY_VERSION = "1.9.2";

  private static final String GOOGLE_CODE = "https://phantomjs.googlecode.com/files/";
  private static final String BITBUCKET = "https://bitbucket.org/ariya/phantomjs/downloads/";

  private final PhantomJsLocatorOptions options;
  private final RepositoryDetails repositoryDetails;
  private final ArtifactBuilder artifactBuilder;
  private final PhantomJSArchive archive;

  public PhantomJsArchiveDownloader(PhantomJsLocatorOptions options,
                                    RepositoryDetails repositoryDetails,
                                    ArtifactBuilder artifactBuilder,
                                    PhantomJSArchive archive) {
    this.options = options;
    this.repositoryDetails = repositoryDetails;
    this.artifactBuilder = artifactBuilder;
    this.archive = archive;
  }

  @Override
  public File download() throws DownloadException {
    return createDownloader().download();
  }

  private Downloader createDownloader() throws DownloadException {

    Downloader downloader;

    if (PhantomJsLocatorOptions.Source.REPOSITORY.equals(options.getSource())) {
      downloader = new RepositoryDownloader(repositoryDetails, artifactBuilder.createArtifact(archive));
    } else {
      ArtifactBuilder artifactBuilder = new ArtifactBuilder();
      CachedFile cachedFile = new CachedArtifact(archive, artifactBuilder, repositoryDetails.getRepositorySystemSession());
      downloader = new WebDownloader(getDownloadURL(), cachedFile.getFile());
    }

    return downloader;
  }

  private URL getDownloadURL() throws DownloadException {
    String baseUrl = getBaseUrl();
    StringBuilder url = new StringBuilder();
    url.append(baseUrl);
    if (!baseUrl.endsWith("/")) {
      url.append("/");
    }
    url.append(archive.getArchiveName());

    try {
      return new URL(url.toString());
    } catch(MalformedURLException e) {
      throw new DownloadException(UNABLE_TO_DOWNLOAD+url, e);
    }
  }

  private String getBaseUrl() {
    String baseUrl = options.getBaseUrl();
    if (baseUrl == null) {
      baseUrl = isLegacyVersion() ? GOOGLE_CODE : BITBUCKET;
    }
    return baseUrl;
  }

  private boolean isLegacyVersion() {
    return VersionUtil.isGreaterThanOrEqualTo(LEGACY_VERSION, options.getVersion());
  }
}
