package com.github.klieber.phantomjs.locate;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryDetailsTest {

  @Mock
  private RepositorySystem repositorySystem;

  @Mock
  private RepositorySystemSession repositorySystemSession;

  @InjectMocks
  private RepositoryDetails repositoryDetails;

  @Test
  public void testRepositorySystem() {
    repositoryDetails.setRepositorySystem(repositorySystem);
    assertThat(repositoryDetails.getRepositorySystem()).isSameAs(repositorySystem);
  }

  @Test
  public void testRepositorySystemSession() {
    repositoryDetails.setRepositorySystemSession(repositorySystemSession);
    assertThat(repositoryDetails.getRepositorySystemSession()).isSameAs(repositorySystemSession);
  }

  @Test
  public void testRemoteRepository() {
    List<RemoteRepository> remoteRepositories = new ArrayList<>();
    repositoryDetails.setRemoteRepositories(remoteRepositories);
    assertThat(repositoryDetails.getRemoteRepositories()).isEqualTo(remoteRepositories);
  }
}
