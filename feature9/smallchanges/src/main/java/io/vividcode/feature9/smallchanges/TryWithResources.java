package io.vividcode.feature9.smallchanges;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class TryWithResources {

  public void process() throws IOException {
    final InputStream inputStream = Files.newInputStream(Paths.get("test.txt"));
    try (inputStream) {
      inputStream.readAllBytes();
    }
  }

  @SafeVarargs
  private void output(final String... args) {
    System.out.println(Arrays.toString(args));
  }
}
