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
package com.github.klieber.phantomjs.archive.mapping;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ArchiveFormat {

  private final String baseUrlTemplate;
  private final String fileTemplate;
  private final String executableTemplate;
  private final String classifier;
  private final String extension;

  @JsonCreator
  public ArchiveFormat(@JsonProperty("baseUrlTemplate") String baseUrlTemplate,
                       @JsonProperty("fileTemplate") String fileTemplate,
                       @JsonProperty("executableTemplate") String executableTemplate,
                       @JsonProperty("classifier") String classifier,
                       @JsonProperty("extension") String extension) {
    this.baseUrlTemplate = baseUrlTemplate;
    this.fileTemplate = fileTemplate;
    this.executableTemplate = executableTemplate;
    this.classifier = classifier;
    this.extension = extension;
  }

  public String getBaseUrlTemplate() {
    return baseUrlTemplate;
  }

  public String getFileTemplate() {
    return fileTemplate;
  }

  public String getExecutableTemplate() {
    return executableTemplate;
  }

  public String getClassifier() {
    return classifier;
  }

  public String getExtension() {
    return extension;
  }
}
