package com.github.klieber.phantomjs.archive.mapping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArchiveFormat {

  private final String fileTemplate;
  private final String executableTemplate;
  private final String classifier;
  private final String extension;

  @JsonCreator
  public ArchiveFormat(@JsonProperty("file") String fileTemplate,
                       @JsonProperty("executable") String executableTemplate,
                       @JsonProperty("classifier") String classifier,
                       @JsonProperty("extension") String extension) {
    this.fileTemplate = fileTemplate;
    this.executableTemplate = executableTemplate;
    this.classifier = classifier;
    this.extension = extension;
  }

  public String getFileTemplate() {
    return fileTemplate;
  }

  public String getExecutableTemplate() {
    return executableTemplate;
  }

  public String getClassifier() {
    return classifier;
  }

  public String getExtension() {
    return extension;
  }
}
