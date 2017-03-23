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
import org.eclipse.aether.repository.LocalRepositoryManager;

import java.io.File;

public class CachedArtifact implements CachedFile {

  private final PhantomJSArchive phantomJSArchive;
  private final ArtifactBuilder artifactBuilder;
  private final RepositorySystemSession repositorySystemSession;

  public CachedArtifact(PhantomJSArchive phantomJSArchive,
                        ArtifactBuilder artifactBuilder,
                        RepositorySystemSession repositorySystemSession) {
    this.phantomJSArchive = phantomJSArchive;
    this.artifactBuilder = artifactBuilder;
    this.repositorySystemSession = repositorySystemSession;
  }

  @Override
  public File getFile() {
    Artifact artifact = artifactBuilder.createArtifact(phantomJSArchive);
    LocalRepositoryManager manager = repositorySystemSession.getLocalRepositoryManager();
    return new File(manager.getRepository().getBasedir(), manager.getPathForLocalArtifact(artifact));
  }
}
