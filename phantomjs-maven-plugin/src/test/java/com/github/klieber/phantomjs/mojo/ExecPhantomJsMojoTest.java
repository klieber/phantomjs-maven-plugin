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
import static com.googlecode.catchexception.apis.CatchExceptionHamcrestMatchers.hasMessage;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExecPhantomJsMojoTest extends TestCase {

  @Mock
  private MavenProject mavenProject;

  @Mock
  private PhantomJsExecutor executor;

  @Mock
  private PhantomJsProcessBuilder processBuilder;

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
    assertThat(caughtException(), allOf(
        is(instanceOf(MojoFailureException.class)),
        hasMessage("PhantomJS execution did not exit normally (code = 1)")
    ));
  }
}