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

  private final ArchiveResolverFactory archiveResolverFactory;
  private final PathResolverFactory pathResolverFactory;

  @Inject
  public PhantomJsResolver(ArchiveResolverFactory archiveResolverFactory,
                           PathResolverFactory pathResolverFactory) {
    this.archiveResolverFactory = archiveResolverFactory;
    this.pathResolverFactory = pathResolverFactory;
  }

  public RepositorySystemStep options(PhantomJsResolverOptions options) {
    return new Builder(archiveResolverFactory, pathResolverFactory).options(options);
  }

  public interface RepositorySystemStep {
    RemoteRepositoryStep repositorySystem(RepositorySystem repositorySystem);
  }

  public interface RemoteRepositoryStep {
    RepositorySystemSessionStep remoteRepositories(List<RemoteRepository> remoteRepositories);
  }

  public interface RepositorySystemSessionStep {
    Resolver repositorySystemSession(RepositorySystemSession repositorySystemSession);
  }

  public static class Builder implements RepositorySystemStep, RepositorySystemSessionStep, RemoteRepositoryStep {

    private final ArchiveResolverFactory archiveResolverFactory;
    private final PathResolverFactory pathResolverFactory;
    private final List<RemoteRepository> remoteRepositories;

    private PhantomJsResolverOptions options;
    private RepositorySystem repositorySystem;
    private RepositorySystemSession repositorySystemSession;

    private Builder(ArchiveResolverFactory archiveResolverFactory,
                    PathResolverFactory pathResolverFactory) {
      this.archiveResolverFactory = archiveResolverFactory;
      this.pathResolverFactory = pathResolverFactory;
      this.remoteRepositories = new ArrayList<>();
    }

    public Builder options(PhantomJsResolverOptions options) {
      assertNotNull(options, "options");
      this.options = options;
      return this;
    }

    public Builder repositorySystem(RepositorySystem repositorySystem) {
      assertNotNull(repositorySystem, "repositorySystem");
      this.repositorySystem = repositorySystem;
      return this;
    }

    public Builder remoteRepositories(List<RemoteRepository> remoteRepositories) {
      assertNotEmpty(remoteRepositories, "remoteRepositories");
      this.remoteRepositories.addAll(remoteRepositories);
      return this;
    }

    public Resolver repositorySystemSession(RepositorySystemSession repositorySystemSession) {
      assertNotNull(repositorySystemSession, "repositorySystemSession");
      this.repositorySystemSession = repositorySystemSession;
      return createResolver();
    }

    private Resolver createResolver() {
      List<Resolver> resolvers = new ArrayList<>();

      if (options.isCheckSystemPath()) {
        resolvers.add(this.pathResolverFactory.create(options));
      }

      resolvers.add(this.archiveResolverFactory.create(options, createRepositoryDetails()));

      return new CompositeResolver(resolvers);
    }

    private RepositoryDetails createRepositoryDetails() {
      return new RepositoryDetails(
        this.repositorySystem,
        this.repositorySystemSession,
        this.remoteRepositories
      );
    }

    private static void assertNotNull(Object object, String name) {
      if (object == null) {
        throw new IllegalArgumentException(name + " must not be null");
      }
    }

    private static void assertNotEmpty(List<?> list, String name) {
      if (list == null || list.isEmpty()) {
        throw new IllegalArgumentException(name + " must not be empty");
      }
    }
  }
}
