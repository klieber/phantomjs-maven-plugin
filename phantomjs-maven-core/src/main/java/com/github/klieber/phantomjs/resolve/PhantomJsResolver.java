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

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
public class PhantomJsResolver {

  private final ResolverFactory resolverFactory;

  @Inject
  public PhantomJsResolver(ResolverFactory resolverFactory) {
    this.resolverFactory = resolverFactory;
  }

  public Builder options(PhantomJsResolverOptions options) {
    return new Builder(resolverFactory, options);
  }

  public static class Builder {

    private final ResolverFactory resolverFactory;
    private final PhantomJsResolverOptions options;
    private final List<RemoteRepository> remoteRepositories;

    private RepositorySystem repositorySystem;
    private RepositorySystemSession repositorySystemSession;

    private Builder(ResolverFactory resolverFactory,
                    PhantomJsResolverOptions options) {
      this.resolverFactory = resolverFactory;
      this.options = options;
      this.remoteRepositories = new ArrayList<>();
    }

    public Builder repositorySystem(RepositorySystem repositorySystem) {
      this.repositorySystem = repositorySystem;
      return this;
    }

    public Builder repositorySystemSession(RepositorySystemSession repositorySystemSession) {
      this.repositorySystemSession = repositorySystemSession;
      return this;
    }

    public Builder remoteRepositories(List<RemoteRepository> remoteRepositories) {
      this.remoteRepositories.addAll(remoteRepositories);
      return this;
    }

    public String resolve() {
      return resolverFactory.create(options, createRepositoryDetails()).resolve();
    }

    private RepositoryDetails createRepositoryDetails() {
      return new RepositoryDetails(
        this.repositorySystem,
        this.repositorySystemSession,
        this.remoteRepositories
      );
    }
  }
}
