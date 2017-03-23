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
package com.github.klieber.phantomjs.locate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompositeLocatorTest {

  private static final String LOCATION_A = "/opt/phantomjs/bin/phantomjs";
  private static final String LOCATION_B = "/usr/bin/phantomjs";

  @Mock
  private Locator locatorA;

  @Mock
  private Locator locatorB;

  private CompositeLocator compositeLocator;

  @Before
  public void before() {
    compositeLocator = new CompositeLocator(Arrays.asList(locatorA,locatorB));
  }

  @Test
  public void shouldLocateLocationA() throws Exception  {
    when(locatorA.locate()).thenReturn(LOCATION_A);
    assertEquals(LOCATION_A, compositeLocator.locate());
    verifyNoMoreInteractions(locatorB);
  }

  @Test
  public void shouldLocateLocationB() throws Exception  {
    when(locatorB.locate()).thenReturn(LOCATION_B);
    assertEquals(LOCATION_B, compositeLocator.locate());
    verify(locatorA).locate();
  }

  @Test
  public void shouldNotLocateAnything() throws Exception  {
    assertNull(compositeLocator.locate());
    verify(locatorA).locate();
    verify(locatorB).locate();
  }
}
