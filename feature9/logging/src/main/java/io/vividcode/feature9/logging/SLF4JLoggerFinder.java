package io.vividcode.feature9.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JLoggerFinder extends System.LoggerFinder {

  private static final RuntimePermission LOGGERFINDER_PERMISSION =
      new RuntimePermission("loggerFinder");

  public SLF4JLoggerFinder() {
    this.checkPermission();
  }

  private void checkPermission() {
    final SecurityManager sm = System.getSecurityManager();
    if (sm != null) {
      sm.checkPermission(LOGGERFINDER_PERMISSION);
    }
  }

  @Override
  public System.Logger getLogger(final String name, final Module module) {
    checkPermission();
    final Logger logger = LoggerFactory
        .getLogger(String.format("%s/%s", module.getName(), name));
    return new SLF4JLoggerWrapper(logger);
  }
}
