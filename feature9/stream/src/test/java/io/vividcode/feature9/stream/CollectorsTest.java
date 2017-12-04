package io.vividcode.feature9.stream;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

public class CollectorsTest {

  @Test
  public void testSimpleFiltering() throws Exception {
    final Set<String> result = Stream.of("a", "bc", "def")
        .collect(Collectors.filtering(
            v -> v.length() > 1,
            Collectors.toSet()));
    assertEquals(2, result.size());
  }

  @Test
  public void testComplicatedFiltering() throws Exception {
    final Map<String, Integer> users = Map.of(
        "Alex", 30,
        "Bob", 16,
        "David", 50
    );
    final Map<String, Set<String>> result = users
        .entrySet()
        .stream()
        .collect(Collectors.groupingBy(entry -> entry.getKey().substring(0, 1),
            Collectors.filtering(entry -> entry.getValue() > 18,
                Collectors.mapping(
                    Map.Entry::getKey,
                    Collectors.toSet()))));
    assertEquals(1, result.get("A").size());
    assertEquals(0, result.get("B").size());
    assertEquals(1, result.get("D").size());
  }

  @Test
  public void testFlatMapping() throws Exception {
    final Set<Integer> result = Stream.of("a", "ab", "abc")
        .collect(Collectors.flatMapping(v -> v.chars().boxed(),
            Collectors.toSet()));
    assertEquals(3, result.size());
  }
}
