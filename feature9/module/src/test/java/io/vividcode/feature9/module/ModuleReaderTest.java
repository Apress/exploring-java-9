package io.vividcode.feature9.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.nio.ByteBuffer;
import java.util.Optional;
import org.junit.Test;

public class ModuleReaderTest {

  @Test
  public void testModuleReader() throws Exception {
    final ModuleReference reference = ModuleTestSupport.getModuleReference();
    assertNotNull(reference);
    final ModuleReader reader = reference.open();
    final Optional<ByteBuffer> byteBuffer = reader.read("module-info.class");
    assertTrue(byteBuffer.isPresent());
    final ModuleDescriptor descriptor = ModuleDescriptor.read(byteBuffer.get());
    assertEquals(ModuleTestSupport.MODULE_NAME, descriptor.name());
  }
}
