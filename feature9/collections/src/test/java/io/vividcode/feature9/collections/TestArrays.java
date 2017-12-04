package io.vividcode.feature9.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import org.junit.Test;

public class TestArrays {

  @Test
  public void testMismatch() throws Exception {
    assertEquals(0, Arrays.mismatch(new int[]{1}, new int[]{2}));
    assertEquals(1, Arrays.mismatch(new int[]{1}, new int[]{1, 2}));
    assertEquals(1, Arrays.mismatch(new int[]{1, 3}, new int[]{1, 2}));
    assertEquals(-1, Arrays.mismatch(
        new int[]{1, 3}, 0, 1,
        new int[]{1, 2}, 0, 1));
  }

  @Test
  public void testCompare() throws Exception {
    assertEquals(0, Arrays.compare(new int[]{1}, new int[]{1}));
    assertTrue(Arrays.compare(new int[]{0}, new int[]{1}) < 0);
    assertTrue(Arrays.compare(new int[]{1}, new int[]{0}) > 0);
    assertEquals(0, Arrays.compare(
        new int[]{1, 3}, 0, 1,
        new int[]{1, 2}, 0, 1));
  }

  @Test
  public void testEquals() throws Exception {
    assertTrue(Arrays.equals(new int[]{1}, new int[]{1}));
    assertTrue(Arrays.equals(
        new int[]{1, 2}, 0, 1,
        new int[]{1, 3}, 0, 1));
  }
}
