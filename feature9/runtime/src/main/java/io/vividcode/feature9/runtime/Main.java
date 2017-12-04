package io.vividcode.feature9.runtime;

import java.io.IOException;
import java.lang.System.Logger.Level;
import java.net.URL;

public class Main {

  private static final System.Logger LOGGER = System.getLogger("Main");

  public static void main(final String[] args) {
    LOGGER.log(Level.INFO, "Run!");
    try {
      new URL("https://google.com").openConnection().connect();
    } catch (final IOException e) {
      LOGGER.log(Level.WARNING, "Failed to connect", e);
    }
  }
}
