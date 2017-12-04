package io.vividcode.feature9.process;

import java.util.Arrays;

public class CurrentProcess {

  public static void main(final String[] args) {
    new CurrentProcess().printProcessInfo(ProcessHandle.current());
  }

  private void printProcessInfo(final ProcessHandle processHandle) {
    final ProcessHandle.Info info = processHandle.info();
    System.out.println("Process info =>");
    System.out.format("PID: %s%n", processHandle.pid());
    info.arguments().ifPresent(args -> System.out.format("Arguments: %s%n", Arrays.toString(args)));
    info.command().ifPresent(command -> System.out.format("Command: %s%n", command));
    info.commandLine()
        .ifPresent(commandLine -> System.out.format("Command line: %s%n", commandLine));
    info.startInstant()
        .ifPresent(startInstant -> System.out.format("Start time: %s%n", startInstant));
    info.totalCpuDuration()
        .ifPresent(cpuDuration -> System.out.format("CPU time: %s%n", cpuDuration));
    info.user().ifPresent(user -> System.out.format("User: %s%n", user));
  }
}
