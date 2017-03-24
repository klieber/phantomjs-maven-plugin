package com.github.klieber.phantomjs.test;

import org.junit.rules.ExternalResource;

import java.io.File;
import java.io.PrintWriter;

public class MockPhantomJsBinary extends ExternalResource {

  private static final String DEFAULT_TARGET_FOLDER = System.getProperty("user.dir")+"/target/";
  private static final String PHANTOMJS_FILE_NAME = "phantomjs";

  private final File phantomJsBinary;
  private final String version;

  public MockPhantomJsBinary(String version) {
    this(DEFAULT_TARGET_FOLDER, version);
  }

  public MockPhantomJsBinary(String targetFolder, String version) {
    this(new File(targetFolder, PHANTOMJS_FILE_NAME), version);
  }

  public MockPhantomJsBinary(File phantomJsBinary, String version) {
    this.phantomJsBinary = phantomJsBinary;
    this.version = version;
  }

  @Override
  protected void before() throws Throwable {
    PrintWriter writer = new PrintWriter(this.phantomJsBinary);
    writer.println("#!/bin/sh");
    writer.println("echo "+this.version);
    writer.close();
    this.phantomJsBinary.setExecutable(true);
  }

  @Override
  protected void after() {
    if (this.phantomJsBinary.exists()) {
      this.phantomJsBinary.delete();
    }
  }

  public File get() {
    return this.phantomJsBinary;
  }

}
