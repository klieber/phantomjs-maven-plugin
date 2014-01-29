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
package com.github.klieber.phantomjs.extract;

import com.github.klieber.phantomjs.archive.PhantomJSArchive;
import de.schlichtherle.truezip.file.TFile;
import de.schlichtherle.truezip.file.TVFS;
import de.schlichtherle.truezip.fs.FsSyncException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class PhantomJsExtractor implements Extractor {

  private static final Logger LOGGER = LoggerFactory.getLogger(PhantomJsExtractor.class);

  private static final String UNABLE_TO_EXTRACT = "Unable to extract phantomjs binary from %s";
  private static final String UNABLE_TO_UNMOUNT = "Unable to unmount file system after extracting";
  private static final String EXTRACTING = "Extracting {} to {}";


  private final PhantomJSArchive phantomJSArchive;

  public PhantomJsExtractor(PhantomJSArchive phantomJSArchive) {
    this.phantomJSArchive = phantomJSArchive;
  }

  @Override
  public void extract(File archive,File extractTo) throws ExtractionException {
    try {
      TFile tfile = new TFile(archive, phantomJSArchive.getPathToExecutable());
      LOGGER.info(EXTRACTING, tfile.getAbsolutePath(), extractTo.getAbsolutePath());
      if (extractTo.getParentFile().exists() || extractTo.getParentFile().mkdirs()) {
        tfile.cp(extractTo);
        extractTo.setExecutable(true);
      }
    } catch (IOException e) {
      throw new ExtractionException(String.format(UNABLE_TO_EXTRACT, archive), e);
    } finally {
      unmountArchive();
    }
  }

  private void unmountArchive() {
    try {
      TVFS.umount();
    }
    catch (FsSyncException e) {
      LOGGER.error(UNABLE_TO_UNMOUNT, e);
    }
  }
}
