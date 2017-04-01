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
