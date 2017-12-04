package io.vividcode.feature9.concurrency;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class AtomicTest {

  @Test
  public void testGetAcquire() {
    final AtomicBoolean value = new AtomicBoolean();
    value.setRelease(false);
    assertEquals(false, value.getAcquire());
  }

  @Test
  public void testCompareAndExchange() {
    final AtomicInteger value = new AtomicInteger(10);
    final int returned = value.compareAndExchange(10, 5);
    assertEquals(10, returned);
    assertEquals(5, value.getPlain());
  }
}
