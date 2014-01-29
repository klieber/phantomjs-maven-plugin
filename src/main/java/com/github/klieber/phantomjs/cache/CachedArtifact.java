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

import java.io.File;

public class CachedArtifact implements CachedFile {

  public static final String GROUP_ID = "org.phantomjs";
  public static final String ARTIFACT_ID = "phantomjs";

  private final PhantomJSArchive phantomJSArchive;
  private final RepositorySystem repositorySystem;
  private final ArtifactRepository repository;

  public CachedArtifact(PhantomJSArchive phantomJSArchive,
                        RepositorySystem repositorySystem,
                        ArtifactRepository repository) {
    this.phantomJSArchive = phantomJSArchive;
    this.repositorySystem = repositorySystem;
    this.repository = repository;
  }

  @Override
  public File getFile() {
    Artifact artifact = repositorySystem.createArtifactWithClassifier(
        GROUP_ID,
        ARTIFACT_ID,
        phantomJSArchive.getVersion(),
        phantomJSArchive.getExtension(),
        phantomJSArchive.getClassifier());
    return new File(repository.getBasedir(), repository.pathOf(artifact));
  }
}
