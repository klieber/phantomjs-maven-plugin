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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhantomJsOptions {

  private File configFile;
  private String workingDirectory;
  private String commandLineOptions;
  private String script;
  private List<String> arguments;

  public PhantomJsOptions() {
    this.arguments = new ArrayList<String>();
  }

  public File getConfigFile() {
    return this.configFile;
  }

  public void setConfigFile(File configFile) {
    this.configFile = configFile;
  }

  public String getWorkingDirectory() {
    return workingDirectory;
  }

  public void setWorkingDirectory(String workingDirectory) {
    this.workingDirectory = workingDirectory;
  }

  public String getCommandLineOptions() {
    return this.commandLineOptions;
  }

  public void setCommandLineOptions(String commandLineOptions) {
    this.commandLineOptions = commandLineOptions;
  }

  public String getScript() {
    return this.script;
  }

  public void setScript(String script) {
    this.script = script;
  }

  public List<String> getArguments() {
    return Collections.unmodifiableList(this.arguments);
  }

  public void addArguments(List<String> arguments) {
    this.arguments.addAll(arguments);
  }

  public void addArgument(String argument) {
    this.arguments.add(argument);
  }
}
