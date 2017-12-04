package io.vividcode.feature9.methodhandle;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import org.junit.Test;

public class MethodHandlesTest {

  @Test
  public void testArrayConstructor() throws Throwable {
    final MethodHandle handle = MethodHandles.arrayConstructor(int[].class);
    final int[] array = (int[]) handle.invoke(3);
    assertEquals(3, array.length);
  }

  @Test
  public void testArrayLength() throws Throwable {
    final MethodHandle handle = MethodHandles.arrayLength(int[].class);
    final int[] array = new int[]{1, 2, 3, 4, 5};
    final int length = (int) handle.invoke(array);
    assertEquals(5, length);
  }

  @Test
  public void testVarHandleInvoker() throws Throwable {
    final VarHandle varHandle = MethodHandles
        .lookup()
        .findVarHandle(HandleTarget.class, "count", int.class);
    final VarHandle.AccessMode accessMode = VarHandle.AccessMode.GET;
    final MethodHandle methodHandle = MethodHandles.varHandleInvoker(
        accessMode,
        varHandle.accessModeType(accessMode)
    );
    final HandleTarget handleTarget = new HandleTarget();
    final int result = (int) methodHandle.invoke(varHandle, handleTarget);
    assertEquals(result, 1);
  }

  @Test
  public void testZero() throws Throwable {
    assertEquals(0, MethodHandles.zero(int.class).invoke());
    assertEquals(0L, MethodHandles.zero(long.class).invoke());
    assertEquals(0F, MethodHandles.zero(float.class).invoke());
    assertEquals(0D, MethodHandles.zero(double.class).invoke());
    assertEquals(null, MethodHandles.zero(String.class).invoke());
  }

  @Test
  public void testEmpty() throws Throwable {
    assertEquals(0, MethodHandles.
        empty(MethodType.methodType(int.class)).invoke());
    assertEquals(null, MethodHandles.
        empty(MethodType.methodType(String.class)).invoke());
  }
}
