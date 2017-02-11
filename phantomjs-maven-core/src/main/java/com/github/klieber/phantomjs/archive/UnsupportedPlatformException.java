package com.github.klieber.phantomjs.archive;

import com.github.klieber.phantomjs.os.OperatingSystem;

public class UnsupportedPlatformException extends RuntimeException {

  private static final String MESSAGE = "Unsupported platform: %s";

  public UnsupportedPlatformException(OperatingSystem operatingSystem) {
    super(String.format(MESSAGE, operatingSystem));
  }
}
