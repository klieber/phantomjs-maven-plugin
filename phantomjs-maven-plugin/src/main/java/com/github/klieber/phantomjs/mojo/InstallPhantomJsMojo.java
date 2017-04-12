/*-
 * #%L
 * PhantomJS Maven Plugin
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
package com.github.klieber.phantomjs.mojo;

import com.github.klieber.phantomjs.resolve.PhantomJsResolver;
import com.github.klieber.phantomjs.resolve.PhantomJsResolverOptions;
import com.google.common.annotations.VisibleForTesting;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;

import javax.inject.Inject;
import java.io.File;

/**
 * Maven plugin for downloading and installing phantomjs binaries.
 *
 * @since 0.1
 */
@Mojo(name = "install", defaultPhase = LifecyclePhase.PROCESS_TEST_SOURCES)
public class InstallPhantomJsMojo extends AbstractPhantomJsMojo implements PhantomJsResolverOptions {

  private static final String UNABLE_TO_INSTALL = "Failed to install phantomjs.";

  /**
   * The version of phantomjs to install.
   *
   * @since 0.1
   */
  @Parameter(
    property = "phantomjs.version",
    required = true
  )
  private String version;

  /**
   * The base url the phantomjs binary can be downloaded from.
   *
   * @since 0.1
   */
  @Parameter(
    property = "phantomjs.baseUrl"
  )
  private String baseUrl;

  /**
   * The directory the phantomjs binary should be installed.
   *
   * @since 0.1
   */
  @Parameter(
    defaultValue = "${project.build.directory}/phantomjs-maven-plugin",
    property = "phantomjs.outputDir",
    required = true
  )
  private File outputDirectory;

  /**
   * Check the system path for an existing phantomjs installation.
   *
   * @since 0.2
   */
  @Parameter(
    defaultValue = "true",
    property = "phantomjs.checkSystemPath",
    required = true
  )
  private boolean checkSystemPath;

  /**
   * Require that the correct version of phantomjs is on the system path. You may either specify a boolean value
   * or starting with version 0.7 of the plugin you can also specify a version range following the same syntax
   * as the <a href="http://maven.apache.org/enforcer/enforcer-rules/versionRanges.html">Maven Enforcer Plugin</a>.
   *
   * @since 0.2
   */
  @Parameter(
    defaultValue = "true",
    property = "phantomjs.enforceVersion",
    required = true
  )
  private String enforceVersion;

  /**
   * <p>The download source for the phantomjs binary.</p>
   * <p>Valid values:</p>
   * <ul>
   * <li>REPOSITORY : download a copy from the maven central repository.</li>
   * <li>URL : download directly from a url</li>
   * </ul>
   *
   * @since 0.3
   */
  @Parameter(
    defaultValue = "REPOSITORY",
    property = "phantomjs.source",
    required = true
  )
  private PhantomJsResolverOptions.Source source;

  @Parameter(
    defaultValue = "${repositorySystemSession}",
    readonly = true
  )
  private RepositorySystemSession repositorySystemSession;

  private final RepositorySystem repositorySystem;
  private final PhantomJsResolver phantomJsResolver;

  @Inject
  public InstallPhantomJsMojo(MavenProject mavenProject,
                              RepositorySystem repositorySystem,
                              PhantomJsResolver phantomJsResolver) {
    super(mavenProject);
    this.repositorySystem = repositorySystem;
    this.phantomJsResolver = phantomJsResolver;
  }

  public PhantomJsResolverOptions.Source getSource() {
    return this.source;
  }

  public String getVersion() {
    return this.version;
  }

  public boolean isCheckSystemPath() {
    return this.checkSystemPath;
  }

  public String getEnforceVersion() {
    return this.enforceVersion;
  }

  public String getBaseUrl() {
    return this.baseUrl;
  }

  public File getOutputDirectory() {
    return this.outputDirectory;
  }

  public void run() throws MojoFailureException {

    String location = phantomJsResolver
      .options(this)
      .repositorySystem(repositorySystem)
      .remoteRepositories(getMavenProject().getRemoteProjectRepositories())
      .repositorySystemSession(repositorySystemSession)
      .resolve();

    if (location == null) {
      throw new MojoFailureException(UNABLE_TO_INSTALL);
    }

    this.setPhantomJsBinaryProperty(location);
  }

  @VisibleForTesting
  void setVersion(String version) {
    this.version = version;
  }

  @VisibleForTesting
  void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @VisibleForTesting
  void setOutputDirectory(File outputDirectory) {
    this.outputDirectory = outputDirectory;
  }

  @VisibleForTesting
  void setCheckSystemPath(boolean checkSystemPath) {
    this.checkSystemPath = checkSystemPath;
  }

  @VisibleForTesting
  void setEnforceVersion(String enforceVersion) {
    this.enforceVersion = enforceVersion;
  }

  @VisibleForTesting
  void setSource(PhantomJsResolverOptions.Source source) {
    this.source = source;
  }

  @VisibleForTesting
  void setRepositorySystemSession(RepositorySystemSession repositorySystemSession) {
    this.repositorySystemSession = repositorySystemSession;
  }
}
