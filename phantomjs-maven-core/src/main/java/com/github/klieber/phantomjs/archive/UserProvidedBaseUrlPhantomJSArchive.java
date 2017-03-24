package com.github.klieber.phantomjs.archive;

public class UserProvidedBaseUrlPhantomJSArchive extends AbstractPhantomJSArchive {

  private final PhantomJSArchive delegate;
  private final String baseUrl;

  public UserProvidedBaseUrlPhantomJSArchive(PhantomJSArchive delegate, String baseUrl) {
    this.delegate = delegate;
    this.baseUrl = baseUrl;
  }

  @Override
  protected String getBaseUrl() {
    return this.baseUrl;
  }

  @Override
  public String getExtension() {
    return delegate.getExtension();
  }

  @Override
  public String getArchiveName() {
    return delegate.getArchiveName();
  }

  @Override
  public String getPathToExecutable() {
    return delegate.getPathToExecutable();
  }

  @Override
  public String getVersion() {
    return delegate.getVersion();
  }

  @Override
  public String getClassifier() {
    return delegate.getClassifier();
  }
}
