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

public abstract class PhantomJSArchive {

	private final String basename;
	private final String version;

	public PhantomJSArchive(String version) {
		this.basename = "phantomjs";
		this.version  = version;
	}

	protected abstract String getExtension();
	protected abstract String getPlatform();
	protected abstract String getExecutable();

	protected String getArch() {
		return null;
	}

	public final String getArchiveName() {
		return this.getArchiveNameSB().toString();
	}

	public final String getPathToExecutable() {
		return this.getArchiveNameSB()
		.append("/")
		.append(this.getNameWithoutExtension())
		.append("/")
		.append(this.getExecutable())
		.toString();
	}

	public final String getExtractToPath() {
		return this.getNameWithoutExtension().append("/").append(this.getExecutable()).toString();
	}

	private final StringBuilder getArchiveNameSB() {
		return this.getNameWithoutExtension()
			.append(".")
			.append(this.getExtension());
	}

	public final StringBuilder getNameWithoutExtension() {
		StringBuilder sb = new StringBuilder()
		.append(this.basename)
		.append("-")
		.append(this.version)
		.append("-")
		.append(this.getPlatform());
		if (this.getArch() != null) {
			sb.append("-").append(this.getArch());
		}
		return sb;
	}
}
