/*
 * Copyright (c) 2014 Kyle Lieber
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.klieber.phantomjs.exec;

import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
public class PhantomJsProcessBuilderTest {

  private static final String BINARY = "phantomjs";

  private static final String COMMAND_LINE = "-a -b -c";

  @Mock
  private PhantomJsOptions options;

  @Mock
  private File file;

  private PhantomJsProcessBuilder builder;

  @Before
  public void before() {
    builder = new PhantomJsProcessBuilder(BINARY);
  }

  @Test
  public void testStart() throws Exception {
    Process process = builder.start(options);
    assertNotNull(process);
  }

  @Test
  public void testStartWithConfigFile() throws Exception {
    when(options.getConfigFile()).thenReturn(file);
    when(file.exists()).thenReturn(true);
    Process process = builder.start(options);
    assertNotNull(process);
  }

  @Test
  public void testStartWithMissingConfigFile() throws Exception {
    when(options.getConfigFile()).thenReturn(file);
    when(file.exists()).thenReturn(false);
    Process process = builder.start(options);
    assertNotNull(process);
  }

  @Test
  @PrepareForTest(CommandLineUtils.class)
  public void testStartCanHandleException() throws Exception {
    mockStatic(CommandLineUtils.class);
    when(options.getCommandLineOptions()).thenReturn(COMMAND_LINE);
    when(CommandLineUtils.translateCommandline(COMMAND_LINE)).thenThrow(new RuntimeException());

    catchException(builder).start(options);
    assertThat(caughtException(), is(instanceOf(ExecutionException.class)));
  }
}
