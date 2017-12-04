package io.vividcode.feature9.methodhandle;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.junit.Test;

public class ForLoopTest {

  static int sum = 0;

  static int init() {
    return 1;
  }

  static int step(final int i) {
    sum += i;
    return i + 1;
  }

  static boolean pred(final int i, final int k) {
    return i < k;
  }

  static int fini(final int i, final int k) {
    return sum;
  }

  @Test
  public void testLoop() throws Throwable {
    final MethodHandle init = getMethodHandle("init",
        MethodType.methodType(int.class));
    final MethodHandle step = getMethodHandle("step",
        MethodType.methodType(int.class, int.class));
    final MethodHandle pred = getMethodHandle("pred",
        MethodType.methodType(boolean.class, int.class, int.class));
    final MethodHandle fini = getMethodHandle("fini",
        MethodType.methodType(int.class, int.class, int.class));
    final MethodHandle[] sumClause =
        new MethodHandle[]{init, step, pred, fini};
    final MethodHandle loop = MethodHandles.loop(sumClause);
    assertEquals(55, loop.invoke(11));
  }

  private MethodHandle getMethodHandle(final String name,
      final MethodType methodType)
      throws NoSuchMethodException, IllegalAccessException {
    return MethodHandles
        .lookup()
        .findStatic(ForLoopTest.class, name, methodType);
  }
}
