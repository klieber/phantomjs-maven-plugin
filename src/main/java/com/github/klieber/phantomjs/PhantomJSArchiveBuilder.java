package com.github.klieber.phantomjs;


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
