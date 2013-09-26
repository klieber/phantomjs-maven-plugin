package com.github.klieber.phantomjs;

public class MacOSXPhantomJSArchive extends PhantomJSArchive {

	public MacOSXPhantomJSArchive(String version) {
		super(version);
	}

	@Override
	protected String getExtension() {
		return "zip";
	}

	@Override
	protected String getPlatform() {
		return "macosx";
	}

	@Override
	protected String getExecutable() {
		return "bin/phantomjs";
	}
}
