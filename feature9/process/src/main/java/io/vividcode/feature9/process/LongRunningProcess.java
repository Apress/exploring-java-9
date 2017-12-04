package io.vividcode.feature9.process;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class LongRunningProcess {

  public ProcessHandle start() throws IOException {
    final ProcessBuilder processBuilder = new ProcessBuilder("top")
        .inheritIO();
    return processBuilder.start().toHandle();
  }

  public void waitFor(final ProcessHandle processHandle) {
    final CountDownLatch latch = new CountDownLatch(1);
    processHandle.onExit().whenCompleteAsync((handle, throwable) -> {
      if (throwable == null) {
        System.out.println(handle.pid());
      } else {
        throwable.printStackTrace();
      }
      latch.countDown();
    });
    final Thread shutdownThread = new Thread(() -> {
      try {
        Thread.sleep(1000);
      } catch (final InterruptedException e) {
        e.printStackTrace();
      }
      if (processHandle.supportsNormalTermination()) {
        processHandle.destroy();
      } else {
        processHandle.destroyForcibly();
      }
    });
    shutdownThread.start();
    try {
      shutdownThread.join();
      latch.await();
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(final String[] args) {
    final LongRunningProcess longRunningProcess = new LongRunningProcess();
    try {
      longRunningProcess.waitFor(longRunningProcess.start());
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
