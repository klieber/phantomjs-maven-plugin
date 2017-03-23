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
package com.github.klieber.phantomjs.util;

import com.github.klieber.phantomjs.archive.PhantomJSArchive;
import org.eclipse.aether.artifact.Artifact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PhantomJSArchive.class)
public class ArtifactBuilderTest {

  @Mock
  private PhantomJSArchive phantomJSArchive;

  private ArtifactBuilder builder = new ArtifactBuilder();

  private static final String CUSTOM_GROUP_ID = "org.example";
  private static final String CUSTOM_ARTIFACT_ID = "example";

  private static final String CLASSIFIER = "linux";
  private static final String EXTENSION = "zip";
  private static final String VERSION = "1.0.0";

  @Before
  public void before() {
    when(phantomJSArchive.getClassifier()).thenReturn(CLASSIFIER);
    when(phantomJSArchive.getExtension()).thenReturn(EXTENSION);
    when(phantomJSArchive.getVersion()).thenReturn(VERSION);
  }

  @Test
  public void shouldCreateArtifactWithDefaults() {
    verifyArtifact(
        builder.createArtifact(phantomJSArchive),
        ArtifactBuilder.GROUP_ID,
        ArtifactBuilder.ARTIFACT_ID
    );
  }

  @Test
  public void shouldCreateArtifactWithCustom() {
    verifyArtifact(
        builder.createArtifact(CUSTOM_GROUP_ID,CUSTOM_ARTIFACT_ID,phantomJSArchive),
        CUSTOM_GROUP_ID,
        CUSTOM_ARTIFACT_ID
    );
  }

  private void verifyArtifact(Artifact artifact, String groupId, String artifactId) {
    assertThat(artifact.getGroupId()).isEqualTo(groupId);
    assertThat(artifact.getArtifactId()).isEqualTo(artifactId);
    assertThat(artifact.getClassifier()).isEqualTo(CLASSIFIER);
    assertThat(artifact.getExtension()).isEqualTo(EXTENSION);
    assertThat(artifact.getVersion()).isEqualTo(VERSION);
  }
}
