package io.vividcode.feature9.rx;

import java.util.concurrent.Flow;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class RandomNumberGenerator extends PeriodicPublisher<Long> {

  public RandomNumberGenerator() {
    super((publisher) ->
            publisher.submit(ThreadLocalRandom.current().nextLong()),
        Flow.defaultBufferSize(),
        10,
        1,
        TimeUnit.SECONDS);
  }

  public static void main(final String[] args) {
    final RandomNumberGenerator generator = new RandomNumberGenerator();
    generator.subscribe(new Flow.Subscriber<>() {
      @Override
      public void onSubscribe(final Flow.Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
      }

      @Override
      public void onNext(final Long item) {
        System.out.printf("Received: %s%n", item);
      }

      @Override
      public void onError(final Throwable throwable) {
        throwable.printStackTrace();
      }

      @Override
      public void onComplete() {
        System.out.println("Completed!");
      }
    });
    generator.waitForCompletion();
  }
}
