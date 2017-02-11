package com.github.klieber.phantomjs.archive.mapping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArchiveMapping {

  private final ArchiveSpec spec;
  private final ArchiveFormat format;

  @JsonCreator
  public ArchiveMapping(@JsonProperty("spec") ArchiveSpec spec,
                        @JsonProperty("archive") ArchiveFormat format) {
    this.spec = spec;
    this.format = format;
  }

  public ArchiveSpec getSpec() {
    return spec;
  }

  public ArchiveFormat getFormat() {
    return format;
  }
}
