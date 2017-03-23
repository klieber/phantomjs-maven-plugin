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
package com.github.klieber.phantomjs.locate;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;

import java.util.List;

public class RepositoryDetails {

  private RepositorySystem repositorySystem;
  private RepositorySystemSession repositorySystemSession;
  private List<RemoteRepository> remoteRepositories;

  public RepositorySystem getRepositorySystem() {
    return repositorySystem;
  }

  public void setRepositorySystem(RepositorySystem repositorySystem) {
    this.repositorySystem = repositorySystem;
  }

  public RepositorySystemSession getRepositorySystemSession() {
    return repositorySystemSession;
  }

  public void setRepositorySystemSession(RepositorySystemSession repositorySystemSession) {
    this.repositorySystemSession = repositorySystemSession;
  }

  public List<RemoteRepository> getRemoteRepositories() {
    return remoteRepositories;
  }

  public void setRemoteRepositories(List<RemoteRepository> remoteRepositories) {
    this.remoteRepositories = remoteRepositories;
  }
}
