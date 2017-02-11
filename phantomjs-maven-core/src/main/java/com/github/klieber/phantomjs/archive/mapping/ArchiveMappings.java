package com.github.klieber.phantomjs.archive.mapping;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.List;

public class ArchiveMappings {

  private final List<ArchiveMapping> mappings;

  @JsonCreator
  public ArchiveMappings() {
    this.mappings = new ArrayList<ArchiveMapping>();
  }

  public List<ArchiveMapping> getMappings() {
    return mappings;
  }
}
