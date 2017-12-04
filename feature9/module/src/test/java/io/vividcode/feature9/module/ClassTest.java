package io.vividcode.feature9.module;

import static org.junit.Assert.assertEquals;

import java.sql.Driver;
import org.junit.Test;

public class ClassTest {

  @Test
  public void testGetModule() {
    assertEquals("java.sql", Driver.class.getModule().getName());
    assertEquals("java.base", String[].class.getModule().getName());
    assertEquals("java.base", int.class.getModule().getName());
    assertEquals("java.base", void.class.getModule().getName());
  }

  @Test
  public void testGetPackageName() {
    assertEquals("java.sql", Driver.class.getPackageName());
    assertEquals("java.lang", String[].class.getPackageName());
    assertEquals("java.lang", int.class.getPackageName());
    assertEquals("java.lang", void.class.getPackageName());
  }
}
