package io.vividcode.feature9.varhandle;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.junit.Before;
import org.junit.Test;

public class VarHandleTest {

  private HandleTarget handleTarget = new HandleTarget();
  private VarHandle varHandle;

  @Before
  public void setUp() throws Exception {
    this.handleTarget = new HandleTarget();
    this.varHandle = MethodHandles
        .lookup()
        .findVarHandle(HandleTarget.class, "count", int.class);
  }

  @Test
  public void testGet() throws Exception {
    assertEquals(1, this.varHandle.get(this.handleTarget));
    assertEquals(1, this.varHandle.getVolatile(this.handleTarget));
    assertEquals(1, this.varHandle.getOpaque(this.handleTarget));
    assertEquals(1, this.varHandle.getAcquire(this.handleTarget));
  }

  @Test
  public void testSet() throws Exception {
    final int newValue = 2;
    this.varHandle.set(this.handleTarget, newValue);
    assertEquals(newValue, this.varHandle.get(this.handleTarget));
    this.varHandle.setVolatile(this.handleTarget, newValue + 1);
    assertEquals(newValue + 1, this.varHandle.get(this.handleTarget));
    this.varHandle.setOpaque(this.handleTarget, newValue + 2);
    assertEquals(newValue + 2, this.varHandle.get(this.handleTarget));
    this.varHandle.setRelease(this.handleTarget, newValue + 3);
    assertEquals(newValue + 3, this.varHandle.get(this.handleTarget));
  }

  @Test
  public void testAtomicUpdate() throws Exception {
    final int expectedValue = 1;
    final int newValue = 2;
    assertEquals(true,
        this.varHandle.compareAndSet(this.handleTarget, expectedValue, newValue));
    assertEquals(newValue,
        this.varHandle.compareAndExchange(
            this.handleTarget, newValue, newValue + 1));
    assertEquals(newValue + 1,
        this.varHandle.getAndSet(this.handleTarget, newValue + 2));
  }

  @Test
  public void testNumericAtomicUpdate() throws Exception {
    final int expectedValue = 1;
    assertEquals(expectedValue,
        this.varHandle.getAndAdd(this.handleTarget, 1));
    assertEquals(expectedValue + 1,
        this.varHandle.getAndAddAcquire(this.handleTarget, 1));
    assertEquals(expectedValue + 2,
        this.varHandle.getAndAddRelease(this.handleTarget, 1));
  }

  @Test
  public void testBitwiseAtomicUpdate() throws Exception {
    final int mask = 1;
    assertEquals(1, this.varHandle.getAndBitwiseAnd(this.handleTarget, mask));
    assertEquals(1, this.varHandle.get(this.handleTarget));
    assertEquals(1, this.varHandle.getAndBitwiseOr(this.handleTarget, mask));
    assertEquals(1, this.varHandle.get(this.handleTarget));
    assertEquals(1, this.varHandle.getAndBitwiseXor(this.handleTarget, mask));
    assertEquals(0, this.varHandle.get(this.handleTarget));
  }

  @Test
  public void testArray() throws Exception {
    final VarHandle arrayElementHandle = MethodHandles
        .arrayElementVarHandle(String[].class);
    assertEquals(true,
        arrayElementHandle.compareAndSet(
            this.handleTarget.names, 0, "Alex", "Alex_new"));
    assertEquals("Alex_new", this.handleTarget.names[0]);
  }

  @Test
  public void testByteArrayView() throws Exception {
    final VarHandle varHandle = MethodHandles
        .byteArrayViewVarHandle(int[].class, ByteOrder.BIG_ENDIAN);
    final byte[] data = this.handleTarget.data;
    assertEquals(16777216, varHandle.get(data, 0));
    assertEquals(1, varHandle.get(data, 1));
    assertEquals(256, varHandle.get(data, 2));
    assertEquals(65536, varHandle.get(data, 3));
    assertEquals(16777216, varHandle.get(data, 4));
  }

  @Test
  public void testByteBufferView() throws Exception {
    final VarHandle varHandle = MethodHandles
        .byteBufferViewVarHandle(int[].class, ByteOrder.BIG_ENDIAN);
    final ByteBuffer dataBuffer = this.handleTarget.dataBuffer;
    assertEquals(16777216, varHandle.get(dataBuffer, 0));
    assertEquals(1, varHandle.get(dataBuffer, 1));
    assertEquals(256, varHandle.get(dataBuffer, 2));
    assertEquals(65536, varHandle.get(dataBuffer, 3));
    assertEquals(16777216, varHandle.get(dataBuffer, 4));
  }
}
