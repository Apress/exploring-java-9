package io.vividcode.feature9.concurrency;

import static org.junit.Assert.assertEquals;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class TimeUnitTest {

  @Test
  public void testChronoUnit() throws Exception {
    assertEquals(TimeUnit.MINUTES, TimeUnit.of(ChronoUnit.MINUTES));
    assertEquals(ChronoUnit.SECONDS, TimeUnit.SECONDS.toChronoUnit());
  }
}
