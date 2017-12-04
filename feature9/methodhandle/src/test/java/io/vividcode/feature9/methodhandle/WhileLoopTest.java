package io.vividcode.feature9.methodhandle;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.junit.Test;

public class WhileLoopTest {

  static int[] init(final int k) {
    return new int[]{1, 0};
  }

  static boolean pred(final int[] local, final int k) {
    return local[0] < k;
  }

  static int[] body(final int[] local, final int k) {
    return new int[]{local[0] + 1, local[1] + local[0]};
  }

  @Test
  public void testWhileLoop() throws Throwable {
    final MethodHandle init = getMethodHandle("init",
        MethodType.methodType(int[].class, int.class));
    final MethodHandle pred = getMethodHandle("pred",
        MethodType.methodType(boolean.class, int[].class, int.class));
    final MethodHandle body = getMethodHandle("body",
        MethodType.methodType(int[].class, int[].class, int.class));
    final MethodHandle whileLoop = MethodHandles.whileLoop(init, pred, body);
    assertEquals(55, ((int[]) whileLoop.invoke(11))[1]);
  }

  @Test
  public void testDoWhileLoop() throws Throwable {
    final MethodHandle init = getMethodHandle("init",
        MethodType.methodType(int[].class, int.class));
    final MethodHandle pred = getMethodHandle("pred",
        MethodType.methodType(boolean.class, int[].class, int.class));
    final MethodHandle body = getMethodHandle("body",
        MethodType.methodType(int[].class, int[].class, int.class));
    final MethodHandle doWhileLoop = MethodHandles
        .doWhileLoop(init, body, pred);
    assertEquals(55, ((int[]) doWhileLoop.invoke(11))[1]);
  }

  private MethodHandle getMethodHandle(final String name,
      final MethodType methodType)
      throws NoSuchMethodException, IllegalAccessException {
    return MethodHandles
        .lookup()
        .findStatic(WhileLoopTest.class, name, methodType);
  }
}
