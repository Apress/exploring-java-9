package io.vividcode.feature9.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

public class OptionalTest {

  private AtomicInteger count;

  @Before
  public void setUp() throws Exception {
    this.count = new AtomicInteger();
  }

  @Test
  public void testIfPresentOrElse() throws Exception {
    checkValue(Optional.empty());
    assertEquals(-1, this.count.get());
    checkValue(Optional.of(1));
    assertEquals(0, this.count.get());
  }

  private void checkValue(final Optional<Integer> value) {
    value.ifPresentOrElse(
        v -> this.count.incrementAndGet(),
        () -> this.count.decrementAndGet()
    );
  }

  @Test
  public void testOr() throws Exception {
    assertTrue(Optional.empty().or(() -> Optional.of(1)).isPresent());
  }

  @Test
  public void testStream() throws Exception {
    final long count = Stream.of(
        Optional.of(1),
        Optional.empty(),
        Optional.of(2)
    ).flatMap(Optional::stream)
        .count();
    assertEquals(2, count);
  }
}
