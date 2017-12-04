package io.vividcode.feature9.methodhandle;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.junit.Test;

public class TryFinallyTest {

  static int success() {
    return 1;
  }

  static int failure() {
    throw new IllegalArgumentException("");
  }

  static int cleanup(final Throwable throwable, final int result) {
    if (throwable != null) {
      throwable.printStackTrace();
    } else {
      System.out.println("Success: " + result);
    }
    return result;
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTryFinally() throws Throwable {
    final MethodHandle targetSuccess = getMethodHandle("success",
        MethodType.methodType(int.class));
    final MethodHandle targetFailure = getMethodHandle("failure",
        MethodType.methodType(int.class));
    final MethodHandle cleanup = getMethodHandle("cleanup",
        MethodType.methodType(int.class, Throwable.class, int.class));
    final MethodHandle tryFinallySuccess = MethodHandles
        .tryFinally(targetSuccess, cleanup);
    assertEquals(1, tryFinallySuccess.invoke());
    final MethodHandle tryFinallyFailure = MethodHandles
        .tryFinally(targetFailure, cleanup);
    tryFinallyFailure.invoke();
  }

  private MethodHandle getMethodHandle(final String name,
      final MethodType methodType)
      throws NoSuchMethodException, IllegalAccessException {
    return MethodHandles
        .lookup()
        .findStatic(TryFinallyTest.class, name, methodType);
  }
}