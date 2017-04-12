package com.github.klieber.phantomjs.cache;

import com.github.klieber.phantomjs.archive.Archive;
import com.github.klieber.phantomjs.util.ArtifactBuilder;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.repository.LocalRepositoryManager;

import java.io.File;

public class AetherArchiveCache implements ArchiveCache {

  private final ArtifactBuilder artifactBuilder;
  private final RepositorySystemSession repositorySystemSession;

  public AetherArchiveCache(ArtifactBuilder artifactBuilder,
                            RepositorySystemSession repositorySystemSession) {
    this.artifactBuilder = artifactBuilder;
    this.repositorySystemSession = repositorySystemSession;
  }

  @Override
  public File getFile(Archive archive) {
    Artifact artifact = artifactBuilder.createArtifact(archive);
    LocalRepositoryManager manager = repositorySystemSession.getLocalRepositoryManager();
    return new File(manager.getRepository().getBasedir(), manager.getPathForLocalArtifact(artifact));
  }
}
