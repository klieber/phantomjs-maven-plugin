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

import com.github.klieber.phantomjs.exec.ExecutionException;
import com.github.klieber.phantomjs.exec.PhantomJsExecutor;
import com.github.klieber.phantomjs.exec.PhantomJsProcessBuilder;
import junit.framework.TestCase;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExecPhantomJsMojoTest extends TestCase {

  @Mock
  private MavenProject mavenProject;

  @Mock
  private PhantomJsExecutor executor;

  @Mock
  private Properties properties;

  @InjectMocks
  private ExecPhantomJsMojo mojo;

  @Test
  public void testRun() throws Exception {
    when(mavenProject.getProperties()).thenReturn(properties);
    when(executor.execute(isA(PhantomJsProcessBuilder.class))).thenReturn(0);
    mojo.setFailOnNonZeroExitCode(true);
    mojo.run();
  }

  @Test
  public void testRunNoFailureOnNonZero() throws Exception {
    when(mavenProject.getProperties()).thenReturn(properties);
    when(executor.execute(isA(PhantomJsProcessBuilder.class))).thenReturn(1);
    mojo.setFailOnNonZeroExitCode(false);
    mojo.run();
  }

  @Test
  public void testRunFailureOnNonZero() throws Exception {
    when(mavenProject.getProperties()).thenReturn(properties);
    when(executor.execute(isA(PhantomJsProcessBuilder.class))).thenReturn(1);
    mojo.setFailOnNonZeroExitCode(true);
    assertThatThrownBy(() -> mojo.run())
      .isInstanceOf(MojoFailureException.class)
      .hasMessage("PhantomJS execution did not exit normally (code = 1)");
  }

  @Test
  public void testRunFailureOnExecutionException() throws Exception {
    when(mavenProject.getProperties()).thenReturn(properties);
    when(executor.execute(isA(PhantomJsProcessBuilder.class))).thenThrow(ExecutionException.class);
    mojo.setFailOnNonZeroExitCode(true);
    assertThatThrownBy(() -> mojo.run())
      .isInstanceOf(MojoFailureException.class)
      .hasMessage("Failed to execute PhantomJS command")
      .hasCauseInstanceOf(ExecutionException.class);
  }
}
