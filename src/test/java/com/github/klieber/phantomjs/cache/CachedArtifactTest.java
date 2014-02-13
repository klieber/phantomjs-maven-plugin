/*
 * Copyright (c) 2014 Kyle Lieber
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.klieber.phantomjs.cache;

import com.github.klieber.phantomjs.archive.PhantomJSArchive;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.repository.RepositorySystem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PhantomJSArchive.class)
public class CachedArtifactTest {

  private static final String VERSION = "1.2";
  private static final String EXTENSION = "tar.gz";
  private static final String CLASSIFIER = "linux";

  private static final String REPOSITORY_PATH = "/tmp";
  private static final String ARTIFACT_PATH = "a/b/artifact-1.2.jar";

  @Mock
  private PhantomJSArchive phantomJSArchive;

  @Mock
  private RepositorySystem repositorySystem;

  @Mock
  private ArtifactRepository artifactRepository;

  @Mock
  private Artifact artifact;

  @Mock
  private File file;

  private CachedArtifact cachedArtifact;

  @Before
  public void before() {
    cachedArtifact = new CachedArtifact(phantomJSArchive,repositorySystem,artifactRepository);
  }

  @Test
  public void testGetFile() throws Exception {
    when(phantomJSArchive.getVersion()).thenReturn(VERSION);
    when(phantomJSArchive.getExtension()).thenReturn(EXTENSION);
    when(phantomJSArchive.getClassifier()).thenReturn(CLASSIFIER);

    when(repositorySystem.createArtifactWithClassifier(
        CachedArtifact.GROUP_ID,
        CachedArtifact.ARTIFACT_ID,
        VERSION,
        EXTENSION,
        CLASSIFIER
    )).thenReturn(artifact);

    when(artifactRepository.getBasedir()).thenReturn(REPOSITORY_PATH);
    when(artifactRepository.pathOf(artifact)).thenReturn(ARTIFACT_PATH);

    when(artifact.getFile()).thenReturn(file);

    assertEquals(REPOSITORY_PATH + "/" + ARTIFACT_PATH, cachedArtifact.getFile().getAbsolutePath());
  }
}
