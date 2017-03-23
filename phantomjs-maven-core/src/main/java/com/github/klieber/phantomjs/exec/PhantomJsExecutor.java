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

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class PhantomJsExecutor {

  private static final String UNABLE_TO_EXECUTE = "Unable to execute phantomjs process";

  public int execute(PhantomJsProcessBuilder runner) throws ExecutionException {
    try {
      Process process = runner.start();
      inheritIO(process.getInputStream(), System.out);
      inheritIO(process.getErrorStream(), System.err);
      return process.waitFor();
    } catch (InterruptedException e) {
      throw new ExecutionException(UNABLE_TO_EXECUTE, e);
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
