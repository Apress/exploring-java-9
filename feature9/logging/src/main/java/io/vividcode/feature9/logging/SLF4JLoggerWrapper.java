package io.vividcode.feature9.logging;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.slf4j.Logger;

public class SLF4JLoggerWrapper implements System.Logger {

  private final Logger logger;

  public SLF4JLoggerWrapper(final Logger logger) {
    this.logger = logger;
  }

  @Override
  public String getName() {
    return this.logger.getName();
  }

  @Override
  public boolean isLoggable(final Level level) {
    switch (level) {
      case ALL:
      case TRACE:
        return this.logger.isTraceEnabled();
      case DEBUG:
        return this.logger.isDebugEnabled();
      case INFO:
        return this.logger.isInfoEnabled();
      case WARNING:
        return this.logger.isWarnEnabled();
      case ERROR:
        return this.logger.isErrorEnabled();
      default:
        return false;
    }
  }

  @Override
  public void log(final Level level, final ResourceBundle bundle,
      final String msg, final Throwable thrown) {
    this.doLog(level, localizedMessage(bundle, msg), thrown);
  }

  @Override
  public void log(final Level level, final ResourceBundle bundle,
      final String format, final Object... params) {
    this.doLog(level,
        String.format(localizedMessage(bundle, format), params),
        null);
  }

  private void doLog(final Level level,
      final String msg,
      final Throwable thrown) {
    switch (level) {
      case ALL:
      case TRACE:
        this.logger.trace(msg, thrown);
        break;
      case DEBUG:
        this.logger.debug(msg, thrown);
        break;
      case INFO:
        this.logger.info(msg, thrown);
        break;
      case WARNING:
        this.logger.warn(msg, thrown);
        break;
      case ERROR:
        this.logger.error(msg, thrown);
        break;
    }
  }

  private String localizedMessage(final ResourceBundle resourceBundle,
      final String msg) {
    if (resourceBundle != null) {
      try {
        return resourceBundle.getString(msg);
      } catch (final MissingResourceException e) {
        return msg;
      }
    }
    return msg;
  }
}
