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

import com.github.klieber.phantomjs.exec.PhantomJsExecutor;
import com.github.klieber.phantomjs.exec.PhantomJsProcessBuilder;
import junit.framework.TestCase;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import java.util.Properties;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;
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

  private ExecPhantomJsMojo mojo;

  @Before
  public void before() {
    this.mojo = new ExecPhantomJsMojo(executor);
    Whitebox.setInternalState(this.mojo, mavenProject);
  }

  @Test
  public void testRun() throws Exception {
    Whitebox.setInternalState(this.mojo, "failOnNonZeroExitCode", true);
    when(mavenProject.getProperties()).thenReturn(properties);
    when(executor.execute(isA(PhantomJsProcessBuilder.class))).thenReturn(0);
    mojo.run();
  }

  @Test
  public void testRunNoFailureOnNonZero() throws Exception {
    Whitebox.setInternalState(this.mojo, "failOnNonZeroExitCode", false);
    when(mavenProject.getProperties()).thenReturn(properties);
    when(executor.execute(isA(PhantomJsProcessBuilder.class))).thenReturn(1);
    mojo.run();
  }

  @Test
  public void testRunFailureOnNonZero() throws Exception {
    Whitebox.setInternalState(this.mojo, "failOnNonZeroExitCode", true);
    when(mavenProject.getProperties()).thenReturn(properties);
    when(executor.execute(isA(PhantomJsProcessBuilder.class))).thenReturn(1);
    catchException(mojo).run();
    assertThat(caughtException())
      .isInstanceOf(MojoFailureException.class)
      .hasMessage("PhantomJS execution did not exit normally (code = 1)");
  }
}
