package io.vividcode.feature9.rx;

import hu.akarnokd.rxjava2.interop.FlowInterop;
import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;

public class RxJava2 {

  public void toFlow() {
    Flowable.interval(0, 50, TimeUnit.MILLISECONDS)
        .take(50)
        .to(FlowInterop.toFlow())
        .subscribe(new DelayedSubscribers.DelayedSubscriber<>("1"));
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
    FlowInterop.fromFlowPublisher(publisher)
        .map(v -> v * 10)
        .forEach(System.out::println);
  }

  public static void main(final String[] args) {
    final RxJava2 rxJava2 = new RxJava2();
    rxJava2.toFlow();
    rxJava2.fromFlow();
    try {
      Thread.sleep(10000);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }
}
