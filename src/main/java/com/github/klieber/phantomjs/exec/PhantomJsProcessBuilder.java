/*
 * Copyright (c) 2014 Kyle Lieber
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
package com.github.klieber.phantomjs.exec;

import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class PhantomJsProcessBuilder {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhantomJsProcessBuilder.class);

  private static final String PHANTOMJS_COMMAND = "phantomjs command: {}";
  private static final String UNABLE_TO_BUILD_CMD_LINE_OPTIONS = "Unable to build command line options.";
  private static final String UNABLE_TO_START = "Unable to start phantomjs process.";

  private final String phantomJsBinary;

  public PhantomJsProcessBuilder(String phantomJsBinary) {
    this.phantomJsBinary = phantomJsBinary;
  }

  public Process start(PhantomJsOptions options) throws ExecutionException {
    Commandline commandline = getCommandLine(options);

    String[] shellCommandline = commandline.getShellCommandline();

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(PHANTOMJS_COMMAND, Arrays.asList(shellCommandline));
    }
    ProcessBuilder processBuilder = new ProcessBuilder(shellCommandline);
    try {
      return processBuilder.start();
    } catch(IOException e) {
      throw new ExecutionException(UNABLE_TO_START,e);
    }
  }

  private Commandline getCommandLine(PhantomJsOptions options) throws ExecutionException {
    Commandline commandline = new Commandline(this.phantomJsBinary);
    File configFile = options.getConfigFile();

    if (configFile != null && configFile.exists()) {
      commandline.createArg().setValue("--config=" + configFile.getAbsolutePath());
    } else {
      commandline.addArguments(this.getCommandLineOptions(options.getCommandLineOptions()));
    }
    commandline.createArg().setValue(options.getScript());
    commandline.addArguments(options.getArguments().toArray(new String[options.getArguments().size()]));

    return commandline;
  }

  private String[] getCommandLineOptions(String commandLineOptions) throws ExecutionException {
    try {
      return CommandLineUtils.translateCommandline(commandLineOptions);
    } catch (Exception e) {
      throw new ExecutionException(UNABLE_TO_BUILD_CMD_LINE_OPTIONS, e);
    }
  }
}
