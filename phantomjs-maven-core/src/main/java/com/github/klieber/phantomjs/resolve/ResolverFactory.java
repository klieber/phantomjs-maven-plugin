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

import com.github.klieber.phantomjs.archive.Archive;
import com.github.klieber.phantomjs.archive.ArchiveFactory;
import com.github.klieber.phantomjs.install.Installer;
import com.github.klieber.phantomjs.install.InstallerFactory;
import com.github.klieber.phantomjs.sys.SystemProperties;
import com.github.klieber.phantomjs.util.VersionUtil;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
public class ResolverFactory {

  private final InstallerFactory installerFactory;
  private final ArchiveFactory archiveFactory;
  private final SystemProperties systemProperties;

  @Inject
  public ResolverFactory(InstallerFactory installerFactory,
                         ArchiveFactory archiveFactory,
                         SystemProperties systemProperties) {
    this.installerFactory = installerFactory;
    this.archiveFactory = archiveFactory;
    this.systemProperties = systemProperties;
  }

  public Resolver create(PhantomJsResolverOptions options,
                         RepositoryDetails repositoryDetails) {
    List<Resolver> resolvers = new ArrayList<Resolver>();
    if (options.isCheckSystemPath()) {
      resolvers.add(this.createPathResolver(options));
    }
    if (repositoryDetails != null) {
      resolvers.add(this.createArchiveResolver(options, repositoryDetails));
    }
    return new CompositeResolver(resolvers);
  }

  private Resolver createPathResolver(PhantomJsResolverOptions options) {
    return createPathResolver(getVersionSpec(options));
  }

  private Resolver createArchiveResolver(PhantomJsResolverOptions options,
                                         RepositoryDetails repositoryDetails) {
    Archive archive = archiveFactory.create(options);
    Installer installer = installerFactory.create(options, repositoryDetails);
    return new ArchiveResolver(installer, archive);
  }

  private Resolver createPathResolver(String versionSpec) {
    return new PathResolver(systemProperties, versionSpec);
  }

  private String getVersionSpec(PhantomJsResolverOptions options) {
    return VersionUtil.getVersionSpec(options.getVersion(), options.getEnforceVersion());
  }
}
