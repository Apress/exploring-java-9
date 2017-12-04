package io.vividcode.feature9.rx;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import reactor.adapter.JdkFlowAdapter;
import reactor.core.publisher.Flux;

public class Reactor {

  public void toFlow() {
    JdkFlowAdapter.publisherToFlowPublisher(
        Flux.interval(Duration.ZERO, Duration.ofMillis(50))
            .take(50)
    ).subscribe(new DelayedSubscribers.DelayedSubscriber<>("1"));
  }

  public void fromFlow() {
    final DelayedSubscribers.SequenceGenerator sequenceGenerator =
        new DelayedSubscribers.SequenceGenerator();
    final PeriodicPublisher<Integer> publisher =
        new PeriodicPublisher<>(
            pub -> pub.submit(sequenceGenerator.get()),
            16,
            50,
            50,
            TimeUnit.MILLISECONDS);
    JdkFlowAdapter.flowPublisherToFlux(publisher)
        .map(v -> v * 10)
        .subscribe(System.out::println);
  }

  public static void main(final String[] args) {
    final Reactor reactor = new Reactor();
    reactor.toFlow();
    reactor.fromFlow();
    try {
      Thread.sleep(10000);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }
}
