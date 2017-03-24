package com.github.klieber.phantomjs.archive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserProvidedBaseUrlPhantomJSArchiveTest {

  private static final String BASE_URL = "http://example.org/files";
  private static final String ARCHIVE_NAME = "archive-name";
  private static final String CLASSIFIER = "classifier";
  private static final String EXTENSION = "ext";
  private static final String PATH_TO_EXECUTABLE = "bin/phantomjs";
  private static final String VERSION = "2.0.0";

  @Mock
  private PhantomJSArchive delegate;

  private UserProvidedBaseUrlPhantomJSArchive archive;

  @Before
  public void before() {
    archive = new UserProvidedBaseUrlPhantomJSArchive(delegate, BASE_URL);
  }

  @Test
  public void testGetArchiveName() {
    when(delegate.getArchiveName()).thenReturn(ARCHIVE_NAME);
    assertThat(archive.getArchiveName()).isEqualTo(ARCHIVE_NAME);
  }

  @Test
  public void testGetClassifier() {
    when(delegate.getClassifier()).thenReturn(CLASSIFIER);
    assertThat(archive.getClassifier()).isEqualTo(CLASSIFIER);
  }

  @Test
  public void testGetExtension() {
    when(delegate.getExtension()).thenReturn(EXTENSION);
    assertThat(archive.getExtension()).isEqualTo(EXTENSION);
  }

  @Test
  public void testGetPathToExecutable() {
    when(delegate.getPathToExecutable()).thenReturn(PATH_TO_EXECUTABLE);
    assertThat(archive.getPathToExecutable()).isEqualTo(PATH_TO_EXECUTABLE);
  }

  @Test
  public void testGetVersion() {
    when(delegate.getVersion()).thenReturn(VERSION);
    assertThat(archive.getVersion()).isEqualTo(VERSION);
  }

  @Test
  public void testGetBaseUrl() {
    assertThat(archive.getBaseUrl()).isEqualTo(BASE_URL);
  }

  @Test
  public void testGetUrl() {
    when(delegate.getArchiveName()).thenReturn(ARCHIVE_NAME);
    assertThat(archive.getUrl()).isEqualTo(BASE_URL+'/'+ARCHIVE_NAME);
    verify(delegate, never()).getUrl();

  }
}
