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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PhantomJsOptionsTest {

  private static final String EXAMPLE_STRING = "example-string";
  @Mock
  private File file;

  private PhantomJsOptions options;

  @Before
  public void before() {
    options = new PhantomJsOptions();
  }

  @Test
  public void testConfigFileField() {
    assertNull(options.getConfigFile());
    options.setConfigFile(file);
    assertSame(file, options.getConfigFile());
  }

  @Test
  public void testCommandLineOptionsField() {
    assertNull(options.getCommandLineOptions());
    options.setCommandLineOptions(EXAMPLE_STRING);
    assertSame(EXAMPLE_STRING, options.getCommandLineOptions());
  }

  @Test
  public void testScriptField() {
    assertNull(options.getScript());
    options.setScript(EXAMPLE_STRING);
    assertSame(EXAMPLE_STRING, options.getScript());
  }

  @Test
  public void testAddArguments() {
    List<String> arguments = Arrays.asList("a", "b", "c");
    assertTrue(options.getArguments().isEmpty());
    options.addArguments(arguments);
    assertEquals(arguments, options.getArguments());
  }

  @Test
  public void testAddArgument() {
    assertTrue(options.getArguments().isEmpty());
    options.addArgument("a");
    assertEquals(Arrays.asList("a"), options.getArguments());
    options.addArgument("b");
    assertEquals(Arrays.asList("a","b"), options.getArguments());
  }
}
