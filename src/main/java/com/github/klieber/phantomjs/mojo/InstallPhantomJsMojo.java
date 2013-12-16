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

import com.github.klieber.phantomjs.archive.PhantomJSArchive;
import com.github.klieber.phantomjs.archive.PhantomJSArchiveBuilder;
import de.schlichtherle.truezip.file.TFile;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Maven plugin for downloading and installing phantomjs binaries.
 *
 * @since 0.1
 */
@Mojo(name = "install", defaultPhase = LifecyclePhase.PROCESS_TEST_SOURCES)
public class InstallPhantomJsMojo extends AbstractPhantomJsMojo {

  /**
   * The version of phantomjs to install.
   *
   * @since 0.1
   */
  @Parameter(
      property = "phantomjs.version",
      required = true
  )
  private String version;

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
  private String baseUrl;

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
  private File outputDirectory;

  public void run() throws MojoExecutionException {

    if (!outputDirectory.exists() && !outputDirectory.mkdirs()) {
      throw new MojoExecutionException("unable to create directory: " + outputDirectory);
    }

    PhantomJSArchive phantomJSFile = new PhantomJSArchiveBuilder(version).build();

    File extractTo = new File(outputDirectory, phantomJSFile.getExtractToPath());
    if (!extractTo.exists()) {
      StringBuilder url = new StringBuilder();
      url.append(baseUrl);
      url.append(phantomJSFile.getArchiveName());

      try {
        URL downloadLocation = new URL(url.toString());

        getLog().info("Downloading phantomjs binaries from " + url);
        ReadableByteChannel rbc = Channels.newChannel(downloadLocation.openStream());
        File outputFile = new File(outputDirectory, phantomJSFile.getArchiveName());
        FileOutputStream fos = new FileOutputStream(outputFile);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        TFile archive = new TFile(outputDirectory, phantomJSFile.getPathToExecutable());

        getLog().info("Extracting " + archive.getAbsolutePath() + " to " + extractTo.getAbsolutePath());
        if (extractTo.getParentFile().mkdirs()) {
          archive.cp(extractTo);
          extractTo.setExecutable(true);
        }
      } catch (MalformedURLException e) {
        throw new MojoExecutionException("Unable to download phantomjs binary from " + url, e);
      } catch (IOException e) {
        throw new MojoExecutionException("Unable to download phantomjs binary from " + url, e);
      }
    }
    this.setPhantomJsBinary(extractTo.getAbsolutePath());
  }
}
