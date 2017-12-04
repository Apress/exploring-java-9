package io.vividcode.feature9.collections;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Collections {

  public static void main(final String[] args) {
    List.of();
    List.of("Hello", "World");
    List.of(1, 2, 3);

    Set.of(1, 2, 3);

    Map.of("Hello", 1, "World", 2);

    Map.ofEntries
        (new AbstractMap.SimpleEntry<>("Hello", 1),
            new AbstractMap.SimpleEntry<>("World", 2)
        );
  }
}
