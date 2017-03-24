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
package com.github.klieber.phantomjs.resolve;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompositeResolverTest {

  private static final String LOCATION_A = "/opt/phantomjs/bin/phantomjs";
  private static final String LOCATION_B = "/usr/bin/phantomjs";

  @Mock
  private Resolver resolverA;

  @Mock
  private Resolver resolverB;

  private CompositeResolver compositeLocator;

  @Before
  public void before() {
    compositeLocator = new CompositeResolver(Arrays.asList(resolverA, resolverB));
  }

  @Test
  public void shouldLocateLocationA() throws Exception {
    when(resolverA.resolve()).thenReturn(LOCATION_A);
    assertThat(compositeLocator.resolve()).isEqualTo(LOCATION_A);
    verifyNoMoreInteractions(resolverB);
  }

  @Test
  public void shouldLocateLocationB() throws Exception {
    when(resolverB.resolve()).thenReturn(LOCATION_B);
    assertThat(compositeLocator.resolve()).isEqualTo(LOCATION_B);
    verify(resolverA).resolve();
  }

  @Test
  public void shouldNotLocateAnything() throws Exception {
    assertThat(compositeLocator.resolve()).isNull();
    verify(resolverA).resolve();
    verify(resolverB).resolve();
  }
}
