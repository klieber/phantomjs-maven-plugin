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

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractPhantomJsMojoTest {

  private static final String BINARY_PATH = "bin/phantomjs";
  private static final String PROPERTY_NAME = "phantomjs.bin.path";

  @Mock
  private MavenProject project;

  @Mock
  private Properties properties;

  @Spy
  private AbstractPhantomJsMojo mojo = new AbstractPhantomJsMojo() {
    @Override
    protected void run() throws MojoFailureException { }
  };

  @Test
  public void testExecute() throws MojoFailureException {
    mojo.execute();
    verify(mojo).run();
  }

  @Test
  public void testSkipExecute() throws MojoFailureException {
    Whitebox.setInternalState(mojo,"skip",true);
    mojo.execute();
    verifyNoMoreInteractions(mojo);
  }

  @Test
  public void testGetPhantomJsBinary() {
    Whitebox.setInternalState(mojo,"phantomJsBinary", BINARY_PATH);
    assertThat(mojo.getPhantomJsBinary()).isEqualTo(BINARY_PATH);
  }

  @Test
  public void testGetPhantomJsBinaryFromProject() {
    when(project.getProperties()).thenReturn(properties);
    when(properties.getProperty(PROPERTY_NAME)).thenReturn(BINARY_PATH);
    Whitebox.setInternalState(mojo, "mavenProject", project);
    Whitebox.setInternalState(mojo, "propertyName", PROPERTY_NAME);
    assertThat(mojo.getPhantomJsBinary()).isEqualTo(BINARY_PATH);
    verify(project, times(1)).getProperties();
    verify(properties, times(1)).getProperty(PROPERTY_NAME);
  }

  @Test
  public void testSetPhantomJsBinary() {
    when(project.getProperties()).thenReturn(properties);
    Whitebox.setInternalState(mojo,"mavenProject",project);
    Whitebox.setInternalState(mojo, "propertyName", PROPERTY_NAME);
    mojo.setPhantomJsBinary(BINARY_PATH);
    verify(properties).setProperty(PROPERTY_NAME, BINARY_PATH);
  }
}
