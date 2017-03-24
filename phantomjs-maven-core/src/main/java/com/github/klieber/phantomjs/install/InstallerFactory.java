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
