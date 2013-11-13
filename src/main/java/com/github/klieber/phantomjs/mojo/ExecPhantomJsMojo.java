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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Maven plugin for downloading and installing phantomjs binaries.
 *
 * @since 0.2
 */
@Mojo(name = "exec", defaultPhase = LifecyclePhase.TEST)
public class ExecPhantomJsMojo extends AbstractPhantomJsMojo {

  public void run() throws MojoExecutionException {
    getLog().info("Executing phantomjs");

    String binary = this.getPhantomJsBinary();

    List<String> commands = new ArrayList<String>();

    if (isWindows() && binary.toLowerCase().endsWith(".bat")) {
      commands.add("cmd");
      commands.add("/c");
    }

    commands.add(binary);
    commands.add(this.script);

    try {
      Process process = new ProcessBuilder(commands).inheritIO().start();
      inheritIO(process.getInputStream(), System.out);
      inheritIO(process.getErrorStream(), System.err);
      process.waitFor();
    } catch (IOException e) {
      throw new MojoExecutionException("Failed to execute phantomjs command", e);
    } catch (InterruptedException e) {
      throw new MojoExecutionException("Failed to execute phantomjs command", e);
    }
  }

  private boolean isWindows() {
    String platform = System.getProperty("os.name");
    return platform != null && platform.toLowerCase().contains("win");
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
