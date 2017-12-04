package io.vividcode.feature9.methodhandle;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

public class IteratedLoopTest {

  static int body(final int sum, final String value) {
    return sum + value.length();
  }

  @Test
  public void testIteratedLoop() throws Throwable {
    final MethodHandle iterator = MethodHandles.constant(
        Iterator.class,
        List.of("a", "bc", "def").iterator());
    final MethodHandle init = MethodHandles.zero(int.class);
    final MethodHandle body = MethodHandles
        .lookup()
        .findStatic(
            IteratedLoopTest.class,
            "body",
            MethodType.methodType(
                int.class,
                int.class,
                String.class));
    final MethodHandle iteratedLoop = MethodHandles
        .iteratedLoop(iterator, init, body);
    assertEquals(6, iteratedLoop.invoke());
  }
}
