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
package com.github.klieber.phantomjs.resolve;

import com.github.klieber.phantomjs.exec.ExecutionException;
import com.github.klieber.phantomjs.exec.PhantomJsProcessBuilder;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class PhantomJsBinaryResolver implements BinaryResolver {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhantomJsBinaryResolver.class);

  private static final String FOUND_PHANTOMJS = "Found phantomjs {} at {}";
  private static final String SYSTEM_CHECK_FAILURE = "Failed to check system path";

  private static final List<String> BINARY_NAMES = Arrays.asList(
    "phantomjs",
    "phantomjs.exe"
  );

  private final String version;
  private final boolean enforceVersion;

  public PhantomJsBinaryResolver(String version, boolean enforceVersion) {
    this.version = version;
    this.enforceVersion = enforceVersion;
  }

  @Override
  public String resolve(String path) {
    String binaryPath = null;
    for (String binaryName : BINARY_NAMES) {
      binaryPath = getBinaryPath(path,binaryName);
      if (binaryPath != null) {
        break;
      }
    }
    return binaryPath;
  }

  private String getBinaryPath(String path, String binary) {
    File file = new File(path, binary);
    if (file.exists()) {
      String versionString = getVersion(file.getAbsolutePath());
      if (versionString != null && (!this.enforceVersion || this.version.equals(versionString))) {
        LOGGER.info(FOUND_PHANTOMJS,versionString,binary);
        return file.getAbsolutePath();
      }
    }
    return null;
  }

  private String getVersion(String binary) {
    try {
      Process process = new PhantomJsProcessBuilder(binary).commandLineOptions("-v").start();
      BufferedReader standardOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String versionString = StringUtils.trim(standardOut.readLine());
      return process.waitFor() == 0 ? versionString : null;

      // TODO Should these exceptions just be wrapped and rethrown for the mojo to handle?
    } catch (ExecutionException e) {
      LOGGER.warn(SYSTEM_CHECK_FAILURE,e);
    } catch (IOException e) {
      LOGGER.warn(SYSTEM_CHECK_FAILURE,e);
    } catch (InterruptedException e) {
      LOGGER.warn(SYSTEM_CHECK_FAILURE,e);
    }
    return null;
  }

}
