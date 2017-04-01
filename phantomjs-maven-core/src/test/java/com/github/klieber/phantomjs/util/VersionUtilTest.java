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
package com.github.klieber.phantomjs.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VersionUtilTest {

  @Test
  public void testGetVersionSpec_EnforceVersionTrue() {
    assertThat(VersionUtil.getVersionSpec("2.0.0", "true")).isEqualTo("[2.0.0]");
  }

  @Test
  public void testGetVersionSpec_EnforceVersionFalse() {
    assertThat(VersionUtil.getVersionSpec("2.0.0", "false")).isEqualTo("[0,]");
  }

  @Test
  public void testGetVersionSpec_EnforceVersionRange() {
    assertThat(VersionUtil.getVersionSpec("2.0.0", "[1.2,2.5]")).isEqualTo("[1.2,2.5]");
  }

  @Test
  public void testGetVersionSpec_EnforceVersionRangeNotWithin() {
    assertThat(VersionUtil.getVersionSpec("1.0", "[1.2,2.5]")).isEqualTo("[1.2,2.5]");
  }

  @Test
  public void testIsWithin_MinimumVersion() {
    assertThat(VersionUtil.isWithin("2.0.0", "2")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.0", "2.0")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.0", "2.0.0")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.1", "2")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.1", "2.0")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.1", "2.0.0")).isTrue();
    assertThat(VersionUtil.isWithin("2.1", "2")).isTrue();
    assertThat(VersionUtil.isWithin("3.0", "2")).isTrue();
    assertThat(VersionUtil.isWithin("1.9", "2")).isFalse();
  }

  @Test
  public void testIsWithin_ExactMatch() {
    assertThat(VersionUtil.isWithin("2.0.0", "[2]")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.0", "[2.0]")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.0", "[2.0.0]")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.1", "[2]")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.1", "[2.0]")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.1", "[2.0.0]")).isFalse();
    assertThat(VersionUtil.isWithin("2.1", "[2]")).isFalse();
    assertThat(VersionUtil.isWithin("3.0", "[2]")).isFalse();
    assertThat(VersionUtil.isWithin("1.9", "[2]")).isFalse();
  }

  @Test
  public void testIsWithin_MinimumVersionInclusive() {
    assertThat(VersionUtil.isWithin("2.0.0", "[2,)")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.0", "[2.0,)")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.0", "[2.0.0,)")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.1", "[2,)")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.1", "[2.0,)")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.1", "[2.0.0,)")).isTrue();
    assertThat(VersionUtil.isWithin("2.1", "[2,)")).isTrue();
    assertThat(VersionUtil.isWithin("3.0", "[2,)")).isTrue();
    assertThat(VersionUtil.isWithin("1.9", "[2,)")).isFalse();
  }

  @Test
  public void testIsWithin_MinimumVersionExclusive() {
    assertThat(VersionUtil.isWithin("2.0.0", "(2,)")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.0", "(2.0,)")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.0", "(2.0.0,)")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.1", "(2,)")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.1", "(2.0,)")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.1", "(2.0.0,)")).isTrue();
    assertThat(VersionUtil.isWithin("2.1", "(2,)")).isTrue();
    assertThat(VersionUtil.isWithin("3.0", "(2,)")).isTrue();
    assertThat(VersionUtil.isWithin("1.9", "(2,)")).isFalse();
  }

  @Test
  public void testIsWithin_MaximumVersionInclusive() {
    assertThat(VersionUtil.isWithin("2.0.0", "(,2]")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.0", "(,2.0]")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.0", "(,2.0.0]")).isTrue();
    assertThat(VersionUtil.isWithin("2.0.1", "(,2]")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.1", "(,2.0]")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.1", "(,2.0.0]")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.0", "(,2.0.1]")).isTrue();
    assertThat(VersionUtil.isWithin("2.1", "(,2]")).isFalse();
    assertThat(VersionUtil.isWithin("3.0", "(,2]")).isFalse();
    assertThat(VersionUtil.isWithin("1.9", "(,2]")).isTrue();
  }

  @Test
  public void testIsWithin_MaximumVersionExclusive() {
    assertThat(VersionUtil.isWithin("2.0.0", "(,2)")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.0", "(,2.0)")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.0", "(,2.0.0)")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.1", "(,2)")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.1", "(,2.0)")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.1", "(,2.0.0)")).isFalse();
    assertThat(VersionUtil.isWithin("2.0.0", "(,2.0.1)")).isTrue();
    assertThat(VersionUtil.isWithin("2.1", "(,2)")).isFalse();
    assertThat(VersionUtil.isWithin("3.0", "(,2)")).isFalse();
    assertThat(VersionUtil.isWithin("1.9", "(,2)")).isTrue();
  }

  @Test
  public void testIsWithin_MinimumInclusiveMaximumInclusive() {
    assertThat(VersionUtil.isWithin("1.0", "[1.5,2.5]")).isFalse();
    assertThat(VersionUtil.isWithin("1.5", "[1.5,2.5]")).isTrue();
    assertThat(VersionUtil.isWithin("2.0", "[1.5,2.5]")).isTrue();
    assertThat(VersionUtil.isWithin("2.5", "[1.5,2.5]")).isTrue();
    assertThat(VersionUtil.isWithin("3.0", "[1.5,2.5]")).isFalse();
  }

  @Test
  public void testIsWithin_MinimumInclusiveMaximumExclusive() {
    assertThat(VersionUtil.isWithin("1.0", "[1.5,2.5)")).isFalse();
    assertThat(VersionUtil.isWithin("1.5", "[1.5,2.5)")).isTrue();
    assertThat(VersionUtil.isWithin("2.0", "[1.5,2.5)")).isTrue();
    assertThat(VersionUtil.isWithin("2.5", "[1.5,2.5)")).isFalse();
    assertThat(VersionUtil.isWithin("3.0", "[1.5,2.5)")).isFalse();
  }

  @Test
  public void testIsWithin_MinimumExclusiveMaximumInclusive() {
    assertThat(VersionUtil.isWithin("1.0", "(1.5,2.5]")).isFalse();
    assertThat(VersionUtil.isWithin("1.5", "(1.5,2.5]")).isFalse();
    assertThat(VersionUtil.isWithin("2.0", "(1.5,2.5]")).isTrue();
    assertThat(VersionUtil.isWithin("2.5", "(1.5,2.5]")).isTrue();
    assertThat(VersionUtil.isWithin("3.0", "(1.5,2.5]")).isFalse();
  }

  @Test
  public void testIsWithin_MinimumExclusiveMaximumExclusive() {
    assertThat(VersionUtil.isWithin("1.0", "(1.5,2.5)")).isFalse();
    assertThat(VersionUtil.isWithin("1.5", "(1.5,2.5)")).isFalse();
    assertThat(VersionUtil.isWithin("2.0", "(1.5,2.5)")).isTrue();
    assertThat(VersionUtil.isWithin("2.5", "(1.5,2.5)")).isFalse();
    assertThat(VersionUtil.isWithin("3.0", "(1.5,2.5)")).isFalse();
  }

  @Test
  public void testIsWithin_InvalidSpec() {
    assertThat(VersionUtil.isWithin("2.0", "[,a)")).isFalse();
    assertThat(VersionUtil.isWithin("2.0", "")).isFalse();
    assertThat(VersionUtil.isWithin("2.0", "[")).isFalse();
    assertThat(VersionUtil.isWithin("2.0", null)).isFalse();
  }

}
