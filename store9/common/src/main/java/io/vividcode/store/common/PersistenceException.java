package io.vividcode.store.common;

public class PersistenceException extends Exception {

  public PersistenceException() {
    super();
  }

  public PersistenceException(final String message) {
    super(message);
  }

  public PersistenceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public PersistenceException(final Throwable cause) {
    super(cause);
  }
}
