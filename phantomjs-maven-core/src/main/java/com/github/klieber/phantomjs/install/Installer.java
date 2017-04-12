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
package com.github.klieber.phantomjs.install;

import com.github.klieber.phantomjs.archive.Archive;
import com.github.klieber.phantomjs.download.DownloadException;
import com.github.klieber.phantomjs.download.Downloader;
import com.github.klieber.phantomjs.extract.ArchiveExtractor;
import com.github.klieber.phantomjs.extract.ExtractionException;

import java.io.File;

public class Installer {

  private static final String UNABLE_TO_INSTALL = "Unable to install phantomjs.";

  private final Downloader downloader;
  private final ArchiveExtractor extractor;
  private final File outputDirectory;

  public Installer(Downloader downloader, ArchiveExtractor extractor, File outputDirectory) {
    this.downloader = downloader;
    this.extractor = extractor;
    this.outputDirectory = outputDirectory;
  }

  public String install(Archive archive) throws InstallationException {
    String executable = archive.getPathToExecutable();

    File extractTo = new File(outputDirectory, executable);

    if (!extractTo.exists()) {
      downloadAndExtract(archive, executable, extractTo);
    }
    return extractTo.getAbsolutePath();
  }

  private void downloadAndExtract(Archive executableArchive,
                                  String executable,
                                  File extractTo) throws InstallationException {
    try {
      File archive = downloader.download(executableArchive);
      extractor.extract(archive, executable, extractTo);
      extractTo.setExecutable(true);
    } catch(DownloadException | ExtractionException e) {
      throw new InstallationException(UNABLE_TO_INSTALL, e);
    }
  }
}
