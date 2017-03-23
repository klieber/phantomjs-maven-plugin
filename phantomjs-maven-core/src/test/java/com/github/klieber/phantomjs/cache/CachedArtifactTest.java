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
package com.github.klieber.phantomjs.cache;

import com.github.klieber.phantomjs.archive.PhantomJSArchive;
import com.github.klieber.phantomjs.util.ArtifactBuilder;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PhantomJSArchive.class,LocalRepository.class})
public class CachedArtifactTest {

  private static final String REPOSITORY_PATH = System.getProperty("java.io.tmpdir");
  private static final String ARTIFACT_PATH = "a/b/artifact-1.2.jar";

  @Mock
  private PhantomJSArchive phantomJSArchive;

  @Mock
  private RepositorySystemSession repositorySystemSession;

  @Mock
  private LocalRepository localRepository;

  @Mock
  private LocalRepositoryManager localRepositoryManager;

  @Mock
  private ArtifactBuilder artifactBuilder;

  @Mock
  private Artifact artifact;

  private File basedir;

  private CachedArtifact cachedArtifact;

  @Before
  public void before() {
    cachedArtifact = new CachedArtifact(phantomJSArchive,artifactBuilder,repositorySystemSession);
    basedir = new File(REPOSITORY_PATH);
  }

  @Test
  public void testGetFile() throws Exception {
    when(artifactBuilder.createArtifact(phantomJSArchive)).thenReturn(artifact);

    when(repositorySystemSession.getLocalRepositoryManager()).thenReturn(localRepositoryManager);
    when(localRepositoryManager.getRepository()).thenReturn(localRepository);
    when(localRepositoryManager.getPathForLocalArtifact(artifact)).thenReturn(ARTIFACT_PATH);
    when(localRepository.getBasedir()).thenReturn(basedir);

    assertThat(cachedArtifact.getFile().getAbsolutePath())
      .isEqualTo(new File(REPOSITORY_PATH, ARTIFACT_PATH).getPath());
  }
}
