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
package com.github.klieber.phantomjs.archive;

import com.github.klieber.phantomjs.util.VersionUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacOSXPhantomJSArchive extends PhantomJSArchive {

	public MacOSXPhantomJSArchive(String version) {
		super(version);
	}

	@Override
	public String getExtension() {
		return "zip";
	}

	@Override
	protected String getPlatform(String version) {
		if (version != null) {
			Pattern pattern = Pattern.compile(VERSION_MATCHER);
			Matcher matcher = pattern.matcher(version);
			if (matcher.find()) {
				Integer major = Integer.valueOf(matcher.group(1));
				Integer minor = Integer.valueOf(matcher.group(2));
				if (major > 2 || ( major == 2 && minor >= 5)) {
					return "macos";
				}
			}
		}
		return "macosx";
	}

	@Override
	protected String getExecutable() {
		return "bin/phantomjs";
	}

  @Override
  public String getPatch() {
    return VersionUtil.isEqualTo("2.0.0", this.getVersion()) ? "fix01" : super.getPatch();
  }
}
