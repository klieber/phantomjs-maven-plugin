package com.github.klieber.phantomjs.locate;

import java.io.File;

public interface PhantomJsLocatorOptions {

  enum Source {
    URL,
    REPOSITORY
  }

  Source getSource();
  String getVersion();
  boolean isCheckSystemPath();
  boolean isEnforceVersion();
  String getBaseUrl();
  File getOutputDirectory();
}
