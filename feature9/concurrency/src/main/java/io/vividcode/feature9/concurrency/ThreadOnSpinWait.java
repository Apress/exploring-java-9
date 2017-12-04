package io.vividcode.feature9.concurrency;

public class ThreadOnSpinWait {

  public static void main(final String[] args) throws InterruptedException {
    final NormalTask task1 = new NormalTask();
    final SpinWaitTask task2 = new SpinWaitTask();
    final Thread thread1 = new Thread(task1);
    thread1.start();
    final Thread thread2 = new Thread(task2);
    thread2.start();
    new Thread(() -> {
      try {
        Thread.sleep(3000);
      } catch (final InterruptedException e) {
        e.printStackTrace();
      } finally {
        task1.start();
        task2.start();
      }
    }).start();
    thread1.join();
    thread2.join();
  }

  private abstract static class Task implements Runnable {

    volatile boolean canStart;

    void start() {
      this.canStart = true;
    }
  }

  private static class NormalTask extends Task {

    @Override
    public void run() {
      while (!this.canStart) {

      }
      System.out.println("Done!");
    }
  }

  private static class SpinWaitTask extends Task {

    @Override
    public void run() {
      while (!this.canStart) {
        Thread.onSpinWait();
      }
      System.out.println("Done!");
    }
  }
}
