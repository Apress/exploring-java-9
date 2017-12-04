package io.vividcode.feature9.stream;

import static org.junit.Assert.assertEquals;

import java.util.stream.Stream;
import org.junit.Test;

public class StreamTest {

  @Test
  public void testOfNullable() throws Exception {
    assertEquals(1, Stream.ofNullable("").count());
    assertEquals(0, Stream.ofNullable(null).count());
  }

  @Test
  public void testDropWhile() throws Exception {
    final long count = Stream.of(1, 2, 3, 4, 5)
        .dropWhile(i -> i % 2 != 0)
        .count();
    assertEquals(4, count);
  }

  @Test
  public void testTakeWhile() throws Exception {
    final long count = Stream.of(1, 2, 3, 4, 5)
        .takeWhile(i -> i % 2 != 0)
        .count();
    assertEquals(1, count);
  }

  @Test
  public void testIterate() throws Exception {
    final Stream<Integer> stream =
        Stream.iterate(2, i -> i <= 10, i -> i + 2);
    assertEquals(5, stream.count());
  }
}
