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


public class PhantomJSArchiveBuilder {
	
	private final String platform;
	private final String arch;
	private final String version;
	
	public PhantomJSArchiveBuilder(String platform, String arch, String version) {
		this.platform = platform;
		this.arch     = arch;
		this.version  = version;
	}
	
	public PhantomJSArchiveBuilder(String version) {
		this(
				System.getProperty("os.name").toLowerCase(),
				System.getProperty("os.arch").toLowerCase(),
				version
		);
	}
	
	public PhantomJSArchive build() {
		PhantomJSArchive archive = null;
  	if (platform.contains("win")) {
  		archive = new WindowsPhantomJSArchive(version);
  	} else if (platform.contains("mac")) {
  		archive = new MacOSXPhantomJSArchive(version);
  	} else if (platform.contains("nux")) {
  		String modifier = arch.contains("64") ? "x86_64" : "i686"; 
 			archive = new LinuxPhantomJSArchive(version, modifier);
  	}
  	if (archive == null) {
  		throw new IllegalArgumentException("unknown platform: " + platform);
  	}
  	return archive;
	}
}
