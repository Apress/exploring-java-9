package io.vividcode.feature9.smallchanges;

public interface SayHi {

  private String buildMessage() {
    return "Hello";
  }

  void sayHi(final String message);

  default void sayHi() {
    sayHi(buildMessage());
  }
}
