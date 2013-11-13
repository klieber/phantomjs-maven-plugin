/*
 * Copyright (c) 2013 Kyle Lieber
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
package com.github.klieber.phantomjs.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.util.List;

/**
 * Abstract base class for phantomjs-maven-plugin mojos.
 */
public abstract class AbstractPhantomJsMojo extends AbstractMojo {

  /**
   * The version of phantomjs to install.
   *
   * @since 0.1
   */
  @Parameter(
      property = "phantomjs.version",
      required = true
  )
  protected String version;

  /**
   * The base url the phantomjs binary can be downloaded from.
   *
   * @since 0.1
   */
  @Parameter(
      defaultValue = "https://phantomjs.googlecode.com/files/",
      property = "phantomjs.baseUrl",
      required = true
  )
  protected String baseUrl;

  /**
   * The directory the phantomjs binary should be installed.
   *
   * @since 0.1
   */
  @Parameter(
      defaultValue = "${project.build.directory}/phantomjs-maven-plugin",
      property = "phantomjs.outputDir",
      required = true
  )
  protected File outputDirectory;

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
  protected String propertyName;

  /**
   * The path to the phantomjs binary
   *
   * @since 0.2
   */
  @Parameter(
      property = "phantomjs.binary"
  )
  protected String phantomJsBinary;

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
  protected boolean skip;

  /**
   * Command line options for phantomjs
   *
   * @since 0.2
   */
  @Parameter(
      property = "phantomjs.commandLineOptions"
  )
  protected String commandLineOptions;

  /**
   * Script to execute
   *
   * @since 0.2
   */
  @Parameter(
      property = "phantomjs.script"
  )
  protected String script;

  /**
   * Arguments for the script being executed
   *
   * @since 0.2
   */
  @Parameter(
      property = "phantomjs.args"
  )
  protected List<String> arguments;

  /**
   * Configuration file for phantomjs
   *
   * @since 0.2
   */
  @Parameter(
      property = "phantomjs.configFile"
  )
  protected File configFile;

  @Parameter(defaultValue = "${project}", readonly = true)
  protected MavenProject mavenProject;

  public final void execute() throws MojoExecutionException {
    if (!skip) {
      this.run();
    }
  }

  protected abstract void run() throws MojoExecutionException;

  protected String getPhantomJsBinary() {
    if (StringUtils.isBlank(this.phantomJsBinary)) {
      this.phantomJsBinary = mavenProject.getProperties().getProperty(this.propertyName);
    }
    return this.phantomJsBinary;
  }
}
