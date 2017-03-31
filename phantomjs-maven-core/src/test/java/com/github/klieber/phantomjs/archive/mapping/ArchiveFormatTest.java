package com.github.klieber.phantomjs.archive.mapping;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArchiveFormatTest {

  private static final String BASE_URL_TEMPLATE = "http://example.org/files";
  private static final String FILE_TEMPLATE = "archive-name";
  private static final String EXECUTABLE_TEMPLATE = "archive-{version}/bin/executable";
  private static final String CLASSIFIER = "macos";
  private static final String EXTENSION = "tar.gz";

  private ArchiveFormat archiveFormat;

  @Before
  public void before() {
    archiveFormat = new ArchiveFormat(
      BASE_URL_TEMPLATE,
      FILE_TEMPLATE,
      EXECUTABLE_TEMPLATE,
      CLASSIFIER,
      EXTENSION
    );
  }
  
  @Test
  public void getBaseUrlTemplate() {
    assertThat(archiveFormat.getBaseUrlTemplate()).isEqualTo(BASE_URL_TEMPLATE);
  }

  @Test
  public void getFileTemplate() {
    assertThat(archiveFormat.getFileTemplate()).isEqualTo(FILE_TEMPLATE);
  }

  @Test
  public void getExecutableTemplate() {
    assertThat(archiveFormat.getExecutableTemplate()).isEqualTo(EXECUTABLE_TEMPLATE);
  }

  @Test
  public void getClassifier() {
    assertThat(archiveFormat.getClassifier()).isEqualTo(CLASSIFIER);
  }

  @Test
  public void getExtension() {
    assertThat(archiveFormat.getExtension()).isEqualTo(EXTENSION);
  }

}
