package io.vividcode.feature9.concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutures {

  public static void main(final String[] args) {
    final Long v1 = new CompletableFuture<Long>().completeAsync(() -> 1L).join();
    final Long v2 = new CompletableFuture<Long>().completeAsync(() -> 1L,
        Executors.newSingleThreadExecutor()).join();
    System.out.printf("%s %s%n", v1, v2);

    try {
      new CompletableFuture<Long>().orTimeout(3, TimeUnit.SECONDS).get();
    } catch (final InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    try {
      final Long value = new CompletableFuture<Long>()
          .completeOnTimeout(1L, 3, TimeUnit.SECONDS).get();
      System.out.println(value);
    } catch (final InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }
}
