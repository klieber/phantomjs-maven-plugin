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
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InstallPhantomJsMojoTest {

  private static final String VERSION = "2.0.0";

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private PhantomJsResolver phantomJsResolver;

  @Mock
  private RepositorySystem repositorySystem;

  @Mock
  private RepositorySystemSession repositorySystemSession;

  @Mock
  private MavenProject mavenProject;

  @Mock
  private File file;

  @InjectMocks
  private InstallPhantomJsMojo mojo;

  @Test
  public void testGetSource() {
    mojo.setSource(PhantomJsResolverOptions.Source.REPOSITORY);
    assertThat(mojo.getSource()).isEqualTo(PhantomJsResolverOptions.Source.REPOSITORY);
  }

  @Test
  public void testGetVersion() {
    mojo.setVersion(VERSION);
    assertThat(mojo.getVersion()).isEqualTo(VERSION);
  }

  @Test
  public void testIsCheckSystemPath() {
    mojo.setCheckSystemPath(true);
    assertThat(mojo.isCheckSystemPath()).isTrue();
  }

  @Test
  public void testGetEnforceVersion() {
    mojo.setEnforceVersion("[1.9.7,)");
    assertThat(mojo.getEnforceVersion()).isEqualTo("[1.9.7,)");
  }

  @Test
  public void testGetBaseUrl() {
    mojo.setBaseUrl("http://example.org/files");
    assertThat(mojo.getBaseUrl()).isEqualTo("http://example.org/files");
  }

  @Test
  public void testGetOutputDirectory() {
    mojo.setOutputDirectory(file);
    assertThat(mojo.getOutputDirectory()).isEqualTo(file);
  }

  @Test
  public void testRun() throws MojoFailureException {

    List<RemoteRepository> remoteRepositories = new ArrayList<RemoteRepository>();

    when(mavenProject.getRemoteProjectRepositories()).thenReturn(remoteRepositories);
    when(mavenProject.getProperties()).thenReturn(new Properties());

    when(
      phantomJsResolver
        .options(mojo)
        .repositorySystem(repositorySystem)
        .remoteRepositories(remoteRepositories)
        .repositorySystemSession(repositorySystemSession)
        .resolve()
    ).thenReturn("/usr/bin/phantomjs");

    mojo.setPropertyName("phantomjs.binary");
    mojo.setRepositorySystemSession(repositorySystemSession);
    mojo.run();

    assertThat(mojo.getPhantomJsBinary()).isEqualTo("/usr/bin/phantomjs");
  }

  @Test
  public void testRun_UnableToLocate() throws MojoFailureException {

    List<RemoteRepository> remoteRepositories = new ArrayList<RemoteRepository>();

    when(mavenProject.getRemoteProjectRepositories()).thenReturn(remoteRepositories);

    when(
      phantomJsResolver
        .options(mojo)
        .repositorySystem(repositorySystem)
        .remoteRepositories(remoteRepositories)
        .repositorySystemSession(repositorySystemSession)
        .resolve()
    ).thenReturn(null);

    mojo.setRepositorySystemSession(repositorySystemSession);

    assertThatThrownBy(() -> mojo.run())
      .isInstanceOf(MojoFailureException.class)
      .hasMessage("Failed to install phantomjs.");
  }
}
