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
package com.github.klieber.phantomjs.download;

import com.github.klieber.phantomjs.archive.Archive;
import com.github.klieber.phantomjs.resolve.RepositoryDetails;
import com.github.klieber.phantomjs.util.ArtifactBuilder;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class AetherDownloader implements Downloader {

  private static final Logger LOGGER = LoggerFactory.getLogger(AetherDownloader.class);

  private final static String UNABLE_TO_RESOLVE = "Unable to resolve artifact.";

  private final static String RESOLVED_ARTIFACT = "Resolved artifact {} from {}";

  private final ArtifactBuilder artifactBuilder;
  private final RepositoryDetails repositoryDetails;

  public AetherDownloader(ArtifactBuilder artifactBuilder,
                          RepositoryDetails repositoryDetails) {
    this.artifactBuilder = artifactBuilder;
    this.repositoryDetails = repositoryDetails;
  }

  @Override
  public File download(Archive archive) throws DownloadException {
    ArtifactResult result = resolveArtifact(createRequest(archive));
    File artifact = result.getArtifact().getFile();
    LOGGER.info(RESOLVED_ARTIFACT, artifact, result.getRepository());
    return artifact;
  }

  private ArtifactRequest createRequest(Archive archive) {
    ArtifactRequest request = new ArtifactRequest();
    request.setArtifact(artifactBuilder.createArtifact(archive));
    request.setRepositories(repositoryDetails.getRemoteRepositories());
    return request;
  }

  private ArtifactResult resolveArtifact(ArtifactRequest artifactRequest) throws DownloadException {
    try {
      return repositoryDetails.getRepositorySystem().resolveArtifact(
        repositoryDetails.getRepositorySystemSession(),
        artifactRequest
      );
    } catch (ArtifactResolutionException e) {
      throw new DownloadException(UNABLE_TO_RESOLVE, e);
    }
  }
}
