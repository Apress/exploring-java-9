package io.vividcode.feature9.rx;

import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DelayedSubscribers {

  public static void main(final String[] args) {
    final DelayedSubscribers delayedSubscribers = new DelayedSubscribers();
    delayedSubscribers.publishWithSubmit();
    System.out.println("===========");
    delayedSubscribers.publishWithOffer();
    System.out.println("===========");
    delayedSubscribers.publishWithOfferTimeout();
  }

  public void publishWithSubmit() {
    final SequenceGenerator sequenceGenerator = new SequenceGenerator();
    this.publish(publisher -> publisher.submit(sequenceGenerator.get()));
  }

  public void publishWithOffer() {
    final SequenceGenerator sequenceGenerator = new SequenceGenerator();
    this.publish(publisher -> publisher.offer(sequenceGenerator.get(),
        ((subscriber, value) -> {
          System.out.printf("%s dropped %s%n", subscriber, value);
          return true;
        })));
  }

  public void publishWithOfferTimeout() {
    final SequenceGenerator sequenceGenerator = new SequenceGenerator();
    this.publish(publisher ->
        publisher.offer(
            sequenceGenerator.get(),
            1000,
            TimeUnit.MILLISECONDS,
            ((subscriber, value) -> {
              System.out.printf("%s dropped %s%n", subscriber, value);
              return true;
            })
        ));
  }

  private void publish(final Consumer<PeriodicPublisher<Integer>> action) {
    final PeriodicPublisher<Integer> publisher =
        new PeriodicPublisher<>(
            action,
            16,
            50,
            50,
            TimeUnit.MILLISECONDS);
    publisher.subscribe(new DelayedSubscriber<>("1"));
    publisher.subscribe(new DelayedSubscriber<>("2"));
    publisher.subscribe(new DelayedSubscriber<>("3"));
    publisher.waitForCompletion();
    System.out.println("Publish completed");
    try {
      Thread.sleep(5000);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static class SequenceGenerator implements Supplier<Integer> {

    private int count = 1;

    @Override
    public Integer get() {
      return this.count++;
    }
  }

  public static class DelayedSubscriber<T> implements Flow.Subscriber<T> {

    private final String id;
    private Flow.Subscription subscription;

    public DelayedSubscriber(final String id) {
      this.id = id;
    }

    @Override
    public void onSubscribe(final Flow.Subscription subscription) {
      this.subscription = subscription;
      System.out.printf("%s subscribed!%n", this.id);
      subscription.request(1);
    }

    @Override
    public void onNext(final T item) {
      this.subscription.request(1);
      try {
        Thread.sleep(100);
      } catch (final InterruptedException e) {
        e.printStackTrace();
      }
      System.out.printf("%s processed: %s%n", this.id, item);
    }

    @Override
    public void onError(final Throwable throwable) {
      throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
      System.out.printf("%s completed!%n", this.id);
    }

    @Override
    public String toString() {
      return String.format("Subscriber %s", this.id);
    }
  }
}
