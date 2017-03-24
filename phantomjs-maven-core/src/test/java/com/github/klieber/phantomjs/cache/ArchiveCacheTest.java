package com.github.klieber.phantomjs.cache;

import com.github.klieber.phantomjs.archive.Archive;
import com.github.klieber.phantomjs.util.ArtifactBuilder;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArchiveCacheTest {

  private static final String REPOSITORY_PATH = System.getProperty("java.io.tmpdir");
  private static final String ARTIFACT_PATH = "a/b/artifact-1.2.jar";

  private static final File BASEDIR = new File(REPOSITORY_PATH);

  @Mock
  private Archive archive;

  @Mock
  private RepositorySystemSession repositorySystemSession;

  @Mock
  private LocalRepositoryManager localRepositoryManager;

  @Mock
  private ArtifactBuilder artifactBuilder;

  @Mock
  private Artifact artifact;

  private ArchiveCache archiveCache;

  @Before
  public void before() {
    archiveCache = new ArchiveCacheFactory(artifactBuilder).create(repositorySystemSession);
  }

  @Test
  public void testGetFile() {
    when(artifactBuilder.createArtifact(archive)).thenReturn(artifact);

    when(repositorySystemSession.getLocalRepositoryManager()).thenReturn(localRepositoryManager);
    when(localRepositoryManager.getRepository()).thenReturn(new LocalRepository(BASEDIR));
    when(localRepositoryManager.getPathForLocalArtifact(artifact)).thenReturn(ARTIFACT_PATH);

    File cachedFile = archiveCache.getFile(archive);

    assertThat(cachedFile.getAbsolutePath()).isEqualTo(new File(REPOSITORY_PATH, ARTIFACT_PATH).getPath());
  }
}
