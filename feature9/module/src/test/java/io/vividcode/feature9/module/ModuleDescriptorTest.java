package io.vividcode.feature9.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Requires;
import java.lang.module.ModuleReference;
import java.util.function.Predicate;
import org.junit.Test;

public class ModuleDescriptorTest {

  @Test
  public void testModuleDescriptor() throws Exception {
    final ModuleReference reference = ModuleTestSupport.getModuleReference();
    assertNotNull(reference);
    final ModuleDescriptor descriptor = reference.descriptor();
    assertEquals(ModuleTestSupport.MODULE_NAME, descriptor.name());
    assertFalse(descriptor.isAutomatic());
    assertFalse(descriptor.isOpen());
    assertEquals(1, descriptor.exports().size());
    assertEquals(2, descriptor.packages().size());
    assertTrue(descriptor.requires().stream().map(Requires::name)
        .anyMatch(Predicate.isEqual("jackson.core")));
    assertTrue(descriptor.uses().isEmpty());
    assertTrue(descriptor.provides().isEmpty());
  }

  @Test
  public void testModuleDescriptorBuilder() {
    final ModuleDescriptor descriptor = ModuleDescriptor.newModule("demo")
        .exports("demo.api")
        .exports("demo.common")
        .mainClass("demo.Main")
        .version("1.0.0")
        .build();
    assertEquals(2, descriptor.exports().size());
    assertTrue(descriptor.mainClass().isPresent());
    assertEquals("demo.Main", descriptor.mainClass().get());
    assertTrue(descriptor.version().isPresent());
    assertEquals("1.0.0", descriptor.version().get().toString());
  }
}
