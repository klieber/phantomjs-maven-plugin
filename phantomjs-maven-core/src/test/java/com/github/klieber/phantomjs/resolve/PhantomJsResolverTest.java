package com.github.klieber.phantomjs.resolve;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhantomJsResolverTest {

  private static final String MOCK_EXECUTABLE_PATH = "/usr/bin/phantomjs";

  @Mock
  private ResolverFactory resolverFactory;

  @Mock
  private Resolver resolver;

  @Mock
  private PhantomJsResolverOptions options;

  @Mock
  private RepositorySystem repositorySystem;

  @Mock
  private RepositorySystemSession repositorySystemSession;

  @InjectMocks
  private PhantomJsResolver phantomJsResolver;

  @Test
  public void testResolve() {
    when(resolverFactory.create(eq(options), any(RepositoryDetails.class))).thenReturn(resolver);
    when(resolver.resolve()).thenReturn(MOCK_EXECUTABLE_PATH);

    String executable = phantomJsResolver.options(options)
                                         .repositorySystem(repositorySystem)
                                         .repositorySystemSession(repositorySystemSession)
                                         .remoteRepositories(new ArrayList<RemoteRepository>())
                                         .resolve();

    assertThat(executable).isEqualTo(MOCK_EXECUTABLE_PATH);
  }

}
