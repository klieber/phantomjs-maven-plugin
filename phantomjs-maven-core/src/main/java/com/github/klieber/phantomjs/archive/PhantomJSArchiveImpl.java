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
package com.github.klieber.phantomjs.archive;

import com.github.klieber.phantomjs.archive.mapping.ArchiveFormat;

public class PhantomJSArchiveImpl implements PhantomJSArchive {

  private final ArchiveFormat archiveFormat;
  private final String version;

  public PhantomJSArchiveImpl(ArchiveFormat archiveFormat,
                              String version) {
    this.archiveFormat = archiveFormat;
    this.version = version;
  }

  @Override
  public String getExtension() {
    return this.archiveFormat.getExtension();
  }

  @Override
  public String getArchiveName() {
    return applyTemplate(this.archiveFormat.getFileTemplate());
  }

  @Override
  public String getPathToExecutable() {
    return applyTemplate(this.archiveFormat.getExecutableTemplate());
  }

  @Override
  public String getVersion() {
    return version;
  }

  @Override
  public String getClassifier() {
    return this.archiveFormat.getClassifier();
  }

  private String applyTemplate(String template) {
    return template
      .replaceAll("\\{version}", this.version)
      .replaceAll("\\{classifier}", this.archiveFormat.getClassifier())
      .replaceAll("\\{extension}", this.archiveFormat.getExtension());
  }
}
