package io.vividcode.feature9.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.Test;

public class ClassLoaderTest {

  @Test
  public void testClassLoaderName() {
    ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
    final List<String> names = Lists.newArrayList();
    while (classLoader != null) {
      names.add(classLoader.getName());
      classLoader = classLoader.getParent();
    }
    assertEquals(2, names.size());
    assertEquals("app", names.get(0));
    assertEquals("platform", names.get(1));
  }

  @Test
  public void testGetDefinedPackages() {
    final ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
    final Package[] packages = classLoader.getDefinedPackages();
    assertTrue(Stream.of(packages)
        .map(Package::getName)
        .noneMatch(Predicate.isEqual("java.lang")));
    assertTrue(Stream.of(packages)
        .map(Package::getName)
        .anyMatch(Predicate.isEqual("io.vividcode.feature9.module")));
    assertNull(classLoader.getDefinedPackage("java.lang"));
    assertNotNull(classLoader.getDefinedPackage("io.vividcode.feature9.module"));
  }
}
