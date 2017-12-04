package io.vividcode.feature9.rx;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class PeriodicPublisher<T> extends SubmissionPublisher<T> {

  private final ScheduledExecutorService scheduler;
  private final ScheduledFuture<?> periodicTask;
  private final AtomicInteger count = new AtomicInteger(0);
  private final CountDownLatch closeLatch = new CountDownLatch(1);

  public PeriodicPublisher(final Consumer<PeriodicPublisher<T>> action,
      final int maxBufferCapacity,
      final int total,
      final long period,
      final TimeUnit timeUnit) {
    super(ForkJoinPool.commonPool(),
        maxBufferCapacity,
        ((subscriber, throwable) ->
            System.out.printf("Publish error for %s: %s",
                subscriber, throwable)));
    this.scheduler = new ScheduledThreadPoolExecutor(1);
    this.periodicTask = this.scheduler.scheduleAtFixedRate(
        () -> {
          action.accept(this);
          final int value = this.count.incrementAndGet();
          if (value >= total) {
            this.doClose();
          }
        }, 0, period, timeUnit);
  }

  @Override
  public void close() {
    this.periodicTask.cancel(false);
    this.scheduler.shutdown();
    super.close();
  }

  public void waitForCompletion() {
    try {
      this.closeLatch.await();
    } catch (final InterruptedException e) {
      this.close();
    }
  }

  private void doClose() {
    try {
      this.close();
    } finally {
      this.closeLatch.countDown();
    }
  }
}
