package com.github.klieber.phantomjs.archive;

public abstract class AbstractPhantomJSArchive implements PhantomJSArchive {

  protected abstract String getBaseUrl();

  @Override
  public final String getUrl() {
    String baseUrl = this.getBaseUrl();
    StringBuilder url = new StringBuilder();
    url.append(baseUrl);
    if (!baseUrl.endsWith("/")) {
      url.append('/');
    }
    url.append(this.getArchiveName());
    return url.toString();
  }

}
