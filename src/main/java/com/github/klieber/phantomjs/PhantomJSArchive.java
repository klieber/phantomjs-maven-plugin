package com.github.klieber.phantomjs;

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
	
	private final StringBuilder getNameWithoutExtension() {
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
