package io.vividcode.feature9.concurrency;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.Test;

public class QueueTest {

  @Test
  public void testForEach() {
    final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
    queue.offer(1);
    queue.offer(2);
    queue.offer(3);
    queue.forEach(System.out::println);
  }

  @Test
  public void testRemoveAll() {
    final LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(3);
    queue.offer(1);
    queue.offer(2);
    queue.removeAll(List.of(1));
    assertEquals(1, queue.size());
  }

  @Test
  public void testRemoveIf() {
    final ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
    queue.offer(1);
    queue.offer(2);
    queue.offer(3);
    queue.removeIf(i -> i % 2 == 0);
    assertEquals(2, queue.size());
  }

  @Test
  public void testRetainAll() {
    final LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque(3);
    deque.offer(1);
    deque.offer(2);
    deque.offer(3);
    deque.retainAll(List.of(1));
    assertEquals(1, deque.size());
  }
}
