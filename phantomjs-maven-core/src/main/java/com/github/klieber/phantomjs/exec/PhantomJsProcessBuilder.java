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
package com.github.klieber.phantomjs.exec;

import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhantomJsProcessBuilder {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhantomJsProcessBuilder.class);

  private static final String PHANTOMJS_COMMAND = "phantomjs command: {}";
  private static final String UNABLE_TO_BUILD_CMD_LINE_OPTIONS = "Unable to build command line options.";
  private static final String UNABLE_TO_START = "Unable to start phantomjs process.";

  private final String phantomJsBinary;

  private File configFile;
  private String workingDirectory;
  private String commandLineOptions;
  private String script;
  private List<String> arguments;

  public PhantomJsProcessBuilder(String phantomJsBinary) {
    this.phantomJsBinary = phantomJsBinary;
    this.arguments = new ArrayList<String>();
  }

  public PhantomJsProcessBuilder configFile(File configFile) {
    this.configFile = configFile;
    return this;
  }

  public PhantomJsProcessBuilder workingDirectory(String workingDirectory) {
    this.workingDirectory = workingDirectory;
    return this;
  }

  public PhantomJsProcessBuilder commandLineOptions(String commandLineOptions) {
    this.commandLineOptions = commandLineOptions;
    return this;
  }

  public PhantomJsProcessBuilder script(String script) {
    this.script = script;
    return this;
  }

  public PhantomJsProcessBuilder arguments(List<String> arguments) {
    if (arguments != null) {
      this.arguments.addAll(arguments);
    }
    return this;
  }

  public PhantomJsProcessBuilder arguments(String ... arguments) {
    return arguments != null ? arguments(Arrays.asList(arguments)) : this;
  }

  public Process start() throws ExecutionException {
    Commandline commandline = getCommandLine();

    String[] shellCommandline = commandline.getShellCommandline();

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(PHANTOMJS_COMMAND, Arrays.asList(shellCommandline));
    }

    ProcessBuilder processBuilder = new ProcessBuilder(shellCommandline);

    if (commandline.getWorkingDirectory() != null) {
      processBuilder.directory(commandline.getWorkingDirectory());
    }

    try {
      return processBuilder.start();
    } catch(IOException e) {
      throw new ExecutionException(UNABLE_TO_START,e);
    }
  }

  private Commandline getCommandLine() throws ExecutionException {
    Commandline commandline = new Commandline(this.phantomJsBinary);

    if (configFile != null && configFile.exists()) {
      commandline.createArg().setValue("--config=" + configFile.getAbsolutePath());
    } else {
      commandline.addArguments(this.getCommandLineOptions(commandLineOptions));
    }
    if (script != null) {
      commandline.createArg().setValue(script);
    }
    if (arguments != null) {
      commandline.addArguments(arguments.toArray(new String[arguments.size()]));
    }
    if (workingDirectory != null) {
      commandline.setWorkingDirectory(workingDirectory);
    }

    return commandline;
  }

  protected String[] getCommandLineOptions(String commandLineOptions) throws ExecutionException {
    try {
      return CommandLineUtils.translateCommandline(commandLineOptions);
    } catch (Exception e) {
      throw new ExecutionException(UNABLE_TO_BUILD_CMD_LINE_OPTIONS, e);
    }
  }
}
