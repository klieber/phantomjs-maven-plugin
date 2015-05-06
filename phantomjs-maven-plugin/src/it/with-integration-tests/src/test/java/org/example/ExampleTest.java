package org.example;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ExampleTest {

  @Test
  public void shouldHavePhantomJsBinary() {
    String binary = System.getProperty("phantomjs.binary");
    assertNotNull(binary);
    assertTrue(new File(binary).exists());
  }

}