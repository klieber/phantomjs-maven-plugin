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
package com.github.klieber.phantomjs.exec;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhantomJsProcessBuilderTest {

  private static final String BINARY = "phantomjs";

  private static final String COMMAND_LINE = "-a -b -c";

  @Mock
  private File file;

  private PhantomJsProcessBuilder builder;

  @Before
  public void before() {
    builder = spy(new PhantomJsProcessBuilder(BINARY));
  }

  @Test
  public void testStart() throws Exception {
    assertThat(builder.start()).isNotNull();
  }

  @Test
  public void testStartWithConfigFile() throws Exception {
    when(file.exists()).thenReturn(true);
    assertThat(builder.configFile(file)).isSameAs(builder);
    assertThat(builder.start()).isNotNull();
  }

  @Test
  public void testStartWithMissingConfigFile() throws Exception {
    when(file.exists()).thenReturn(false);
    assertThat(builder.configFile(file)).isSameAs(builder);
    assertThat(builder.start()).isNotNull();
  }

  @Test
  public void testStartCanHandleException() throws Exception {
    //noinspection unchecked
    when(builder.getCommandLineOptions(COMMAND_LINE)).thenThrow(ExecutionException.class);

    try {
      builder.commandLineOptions(COMMAND_LINE).start();
      fail("Should have thrown an ExecutionException");
    } catch (ExecutionException e) {
      // expected
    }
  }
}
