package com.github.klieber.phantomjs.archive;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractPhantomJSArchiveTest {

  private static final String BASE_URL = "http://example.org/files";
  private static final String ARCHIVE_NAME = "phantomjs-2.0.zip";

  private static final String FULL_URL = BASE_URL + '/' + ARCHIVE_NAME;

  @Test
  public void getUrlWithNoSlashAtEndOfBaseUrl() {
    AbstractPhantomJSArchive archive = new MockPhantomJSArchive(BASE_URL, ARCHIVE_NAME);
    assertThat(archive.getUrl()).isEqualTo(FULL_URL);
  }

  @Test
  public void getUrlWithSlashAtEndOfBaseUrl() {
    AbstractPhantomJSArchive archive = new MockPhantomJSArchive(BASE_URL+'/', ARCHIVE_NAME);
    assertThat(archive.getUrl()).isEqualTo(FULL_URL);
  }

  static class MockPhantomJSArchive extends AbstractPhantomJSArchive {

    private final String baseUrl;
    private final String archiveName;

    public MockPhantomJSArchive(String baseUrl, String archiveName) {

      this.baseUrl = baseUrl;
      this.archiveName = archiveName;
    }

    @Override
    protected String getBaseUrl() {
      return baseUrl;
    }

    @Override
    public String getExtension() {
      return null;
    }

    @Override
    public String getArchiveName() {
      return archiveName;
    }

    @Override
    public String getPathToExecutable() {
      return null;
    }

    @Override
    public String getVersion() {
      return null;
    }

    @Override
    public String getClassifier() {
      return null;
    }
  }
}
