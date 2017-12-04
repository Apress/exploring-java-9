package io.vividcode.feature9.rx;

import hu.akarnokd.rxjava2.interop.FlowInterop;
import io.reactivex.Flowable;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;
import reactor.adapter.JdkFlowAdapter;

public class RxInterop {

  public static void main(final String[] args) {
    final Flow.Publisher<Long> publisher =
        Flowable.interval(0, 50, TimeUnit.MILLISECONDS)
            .take(50)
            .to(FlowInterop.toFlow());
    JdkFlowAdapter.flowPublisherToFlux(publisher)
        .map(v -> v * 10)
        .toStream()
        .forEach(System.out::println);
  }
}
