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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Maven plugin for downloading and installing phantomjs binaries.
 *
 * @since 0.2
 */
@Mojo(name = "exec", defaultPhase = LifecyclePhase.TEST)
public class ExecPhantomJsMojo extends AbstractPhantomJsMojo {

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

  public void run() throws MojoExecutionException {
    getLog().info("Executing phantomjs command");

    String binary = this.getPhantomJsBinary();

    Commandline commandline = new Commandline(binary);
    if (configFile != null && configFile.exists()) {
      commandline.createArg().setValue("--config=" + configFile.getAbsolutePath());
    } else {
      commandline.addArguments(this.getCommandLineOptions());
    }
    commandline.createArg().setValue(this.script);
    commandline.addArguments(this.arguments.toArray(new String[this.arguments.size()]));

    try {
      if (getLog().isDebugEnabled()) {
        getLog().debug("phantomjs command: " + Arrays.asList(commandline.getShellCommandline()));
      }
      Process process = new ProcessBuilder(commandline.getShellCommandline()).start();
      inheritIO(process.getInputStream(), System.out);
      inheritIO(process.getErrorStream(), System.err);
      process.waitFor();
    } catch (IOException e) {
      throw new MojoExecutionException("Failed to execute phantomjs command", e);
    } catch (InterruptedException e) {
      throw new MojoExecutionException("Failed to execute phantomjs command", e);
    }
  }

  private String[] getCommandLineOptions() throws MojoExecutionException {
    try {
      return CommandLineUtils.translateCommandline(this.commandLineOptions);
    } catch (Exception e) {
      throw new MojoExecutionException("Failed to execute phantomjs command", e);
    }
  }

  private static void inheritIO(final InputStream src, final PrintStream dest) {
    new Thread(
        new Runnable() {
          public void run() {
            Scanner sc = new Scanner(src);
            while (sc.hasNextLine()) {
              dest.println(sc.nextLine());
            }
          }
        }
    ).start();
  }
}
