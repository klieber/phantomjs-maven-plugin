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

import com.github.klieber.phantomjs.exec.ExecutionException;
import com.github.klieber.phantomjs.exec.PhantomJsExecutor;
import com.github.klieber.phantomjs.exec.PhantomJsOptions;
import com.github.klieber.phantomjs.exec.PhantomJsProcessBuilder;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Maven plugin for downloading and installing phantomjs binaries.
 *
 * @since 0.2
 */
@Mojo(name = "exec", defaultPhase = LifecyclePhase.TEST)
public class ExecPhantomJsMojo extends AbstractPhantomJsMojo {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExecPhantomJsMojo.class);

  private static final String EXECUTION_FAILURE = "Failed to execute PhantomJS command";
  private static final String ABNORMAL_EXIT_CODE = "PhantomJS execution did not exit normally (code = %d)";

  /**
   * Working Directory
   *
   * @since 0.5
   */
  @Parameter(
      property = "phantomjs.workingDirectory",
      defaultValue = "${project.basedir}"
  )
  private String workingDirectory;

  /**
   * Command line options for phantomjs
   *
   * @since 0.2
   */
  @Parameter(
      property = "phantomjs.commandLineOptions"
  )
  private String commandLineOptions;

  /**
   * Script to execute
   *
   * @since 0.2
   */
  @Parameter(
      property = "phantomjs.script"
  )
  private String script;

  /**
   * Arguments for the script being executed
   *
   * @since 0.2
   */
  @Parameter(
      property = "phantomjs.args"
  )
  private List<String> arguments;

  /**
   * Configuration file for phantomjs
   *
   * @since 0.2
   */
  @Parameter(
      property = "phantomjs.configFile"
  )
  private File configFile;

  /**
   * Fail on a non-zero exit code
   *
   * @since 0.4
   */
  @Parameter(
      property = "phantomjs.failOnNonZeroExitCode",
      defaultValue = "true"
  )
  private boolean failOnNonZeroExitCode;

  public void run() throws MojoFailureException {
    LOGGER.info("Executing phantomjs command");

    String binary = this.getPhantomJsBinary();

    PhantomJsProcessBuilder builder = new PhantomJsProcessBuilder(binary);

    PhantomJsExecutor executor = new PhantomJsExecutor(builder);

    PhantomJsOptions options = new PhantomJsOptions();
    options.setConfigFile(this.configFile);
    options.setWorkingDirectory(this.workingDirectory);
    options.setCommandLineOptions(this.commandLineOptions);
    options.addArguments(this.arguments);
    options.setScript(this.script);

    try {
      int code = executor.execute(options);
      if (failOnNonZeroExitCode && code != 0) {
        throw new MojoFailureException(String.format(ABNORMAL_EXIT_CODE, code));
      }
    } catch (ExecutionException e) {
      throw new MojoFailureException(EXECUTION_FAILURE, e);
    }
  }
}
