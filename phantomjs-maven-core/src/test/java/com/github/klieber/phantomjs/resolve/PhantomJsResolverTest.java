/*-
 * #%L
 * PhantomJS Maven Core
 * %%
 * Copyright (C) 2013 - 2017 Kyle Lieber
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
package com.github.klieber.phantomjs.resolve;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhantomJsResolverTest {

  private static final String MOCK_EXECUTABLE_PATH = "/usr/bin/phantomjs";

  @Mock
  private ArchiveResolverFactory archiveResolverFactory;

  @Mock
  private Resolver archiveResolver;

  @Mock
  private PathResolverFactory pathResolverFactory;

  @Mock
  private Resolver pathResolver;

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
    when(options.isCheckSystemPath()).thenReturn(true);

    when(archiveResolverFactory.create(eq(options), isA(RepositoryDetails.class))).thenReturn(archiveResolver);
    when(pathResolverFactory.create(options)).thenReturn(pathResolver);

    when(archiveResolver.resolve()).thenReturn(MOCK_EXECUTABLE_PATH);

    RemoteRepository remoteRepository = new RemoteRepository.Builder("", "", "").build();


    String executable = phantomJsResolver
      .options(options)
      .repositorySystem(repositorySystem)
      .remoteRepositories(Collections.singletonList(remoteRepository))
      .repositorySystemSession(repositorySystemSession)
      .resolve();

    assertThat(executable).isEqualTo(MOCK_EXECUTABLE_PATH);
  }

}
