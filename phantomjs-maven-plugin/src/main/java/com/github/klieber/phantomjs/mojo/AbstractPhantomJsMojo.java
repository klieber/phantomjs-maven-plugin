/*-
 * #%L
 * PhantomJS Maven Plugin
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
package com.github.klieber.phantomjs.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;

/**
 * Abstract base class for phantomjs-maven-plugin mojos.
 */
public abstract class AbstractPhantomJsMojo extends AbstractMojo {

  /**
   * The name of the property that will contains the path to the binary.
   *
   * @since 0.1
   */
  @Parameter(
      defaultValue = "phantomjs.binary",
      property = "phantomjs.propertyName",
      required = true
  )
  private String propertyName;

  /**
   * The path to the phantomjs binary
   *
   * @since 0.2
   */
  @Parameter(
      property = "phantomjs.binary"
  )
  private String phantomJsBinary;

  /**
   * Skip the phantomjs-maven-plugin execution.
   *
   * @since 0.2
   */
  @Parameter(
      defaultValue = "false",
      property = "phantomjs.skip",
      required = true
  )
  private boolean skip;

  @Parameter(defaultValue = "${project}", readonly = true)
  private MavenProject mavenProject;

  public final void execute() throws MojoFailureException {
    if (!skip) {
      this.run();
    }
  }

  protected abstract void run() throws MojoFailureException;

  protected String getPhantomJsBinary() {
    if (StringUtils.isBlank(this.phantomJsBinary)) {
      this.phantomJsBinary = mavenProject.getProperties().getProperty(this.propertyName);
    }
    return this.phantomJsBinary;
  }

  protected void setPhantomJsBinary(String binary) {
    mavenProject.getProperties().setProperty(this.propertyName, binary);
  }
}
