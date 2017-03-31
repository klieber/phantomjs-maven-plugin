package com.github.klieber.phantomjs.archive.mapping;

import com.github.klieber.phantomjs.sys.os.OperatingSystem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArchiveSpecTest {

  private static final String VERSION_SPEC = "[2.0,2.5]";

  @Mock
  private OperatingSystemSpec operatingSystemSpec;

  @Mock
  private OperatingSystem operatingSystem;

  private ArchiveSpec archiveSpec;

  @Before
  public void before() {
    archiveSpec = new ArchiveSpec(VERSION_SPEC, operatingSystemSpec);
  }

  @Test
  public void testGetVersionSpec() {
    assertThat(archiveSpec.getVersionSpec()).isEqualTo(VERSION_SPEC);
  }

  @Test
  public void testGetOperatingSystemSpec() {
    assertThat(archiveSpec.getOperatingSystemSpec()).isEqualTo(operatingSystemSpec);
  }

  @Test
  public void testMatches_OsAndVersionMatches() {
    when(operatingSystemSpec.matches(operatingSystem)).thenReturn(true);
    assertThat(archiveSpec.matches("2.0", operatingSystem)).isTrue();
  }

  @Test
  public void testMatches_OnlyVersionMatches() {
    assertThat(archiveSpec.matches("2.0", operatingSystem)).isFalse();
  }

  @Test
  public void testMatches_NoMatches() {
    assertThat(archiveSpec.matches("1.5", operatingSystem)).isFalse();
  }
}
