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
package com.github.klieber.phantomjs.locate;

import com.github.klieber.phantomjs.resolve.BinaryResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PathLocatorTest {

  private static final String PATH_A = "/opt/phantomjs/bin";
  private static final String PATH_B = "/usr/bin";

  private static final String LOCATION_A = PATH_A + "/phantomjs";
  private static final String LOCATION_B = PATH_B + "/phantomjs";

  @Mock
  private BinaryResolver binaryResolver;

  private PathLocator pathLocator;

  @Before
  public void before() {
    this.pathLocator = new PathLocator(binaryResolver, Arrays.asList(PATH_A, PATH_B));
  }

  @Test
  public void shouldLocateA() {
    when(binaryResolver.resolve(PATH_A)).thenReturn(LOCATION_A);
    assertEquals(LOCATION_A, pathLocator.locate());
    verify(binaryResolver, never()).resolve(PATH_B);
  }

  @Test
  public void shouldLocateB() {
    when(binaryResolver.resolve(PATH_B)).thenReturn(LOCATION_B);
    assertEquals(LOCATION_B, pathLocator.locate());
    verify(binaryResolver).resolve(PATH_A);
  }

  @Test
  public void shouldNotLocateAnything() {
    assertNull(pathLocator.locate());
    verify(binaryResolver).resolve(PATH_A);
    verify(binaryResolver).resolve(PATH_B);
  }
}
