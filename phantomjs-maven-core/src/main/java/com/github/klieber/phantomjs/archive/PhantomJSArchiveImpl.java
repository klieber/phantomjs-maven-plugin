package com.github.klieber.phantomjs.archive;

import com.github.klieber.phantomjs.archive.mapping.ArchiveFormat;

public class PhantomJSArchiveImpl implements PhantomJSArchive {

  private final ArchiveFormat archiveFormat;
  private final String version;

  public PhantomJSArchiveImpl(ArchiveFormat archiveFormat,
                              String version) {
    this.archiveFormat = archiveFormat;
    this.version = version;
  }

  @Override
  public String getExtension() {
    return this.archiveFormat.getExtension();
  }

  @Override
  public String getArchiveName() {
    return applyTemplate(this.archiveFormat.getFileTemplate());
  }

  @Override
  public String getPathToExecutable() {
    return applyTemplate(this.archiveFormat.getExecutableTemplate());
  }

  @Override
  public String getVersion() {
    return version;
  }

  @Override
  public String getClassifier() {
    return this.archiveFormat.getClassifier();
  }

  private String applyTemplate(String template) {
    return template
      .replaceAll("\\{version}", this.version)
      .replaceAll("\\{classifier}", this.archiveFormat.getClassifier())
      .replaceAll("\\{extension}", this.archiveFormat.getExtension());
  }
}
