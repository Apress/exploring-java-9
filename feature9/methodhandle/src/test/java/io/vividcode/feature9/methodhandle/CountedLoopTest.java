package io.vividcode.feature9.methodhandle;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.junit.Test;

public class CountedLoopTest {

  static int body(final int sum, final int i) {
    return sum + i + 1;
  }

  @Test
  public void testCountedLoop() throws Throwable {
    final MethodHandle iterations = MethodHandles.constant(int.class, 10);
    final MethodHandle init = MethodHandles.zero(int.class);
    final MethodHandle body = MethodHandles
        .lookup()
        .findStatic(CountedLoopTest.class, "body",
            MethodType.methodType(int.class, int.class, int.class));
    final MethodHandle countedLoop = MethodHandles
        .countedLoop(iterations, init, body);
    assertEquals(55, countedLoop.invoke());
  }

  @Test
  public void testCountedLoopStartEnd() throws Throwable {
    final MethodHandle start = MethodHandles.zero(int.class);
    final MethodHandle end = MethodHandles.constant(int.class, 10);
    final MethodHandle init = MethodHandles.zero(int.class);
    final MethodHandle body = MethodHandles
        .lookup()
        .findStatic(CountedLoopTest.class, "body",
            MethodType.methodType(int.class, int.class, int.class));
    final MethodHandle countedLoop = MethodHandles
        .countedLoop(start, end, init, body);
    assertEquals(55, countedLoop.invoke());
  }
}
