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
