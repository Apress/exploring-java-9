package io.vividcode.feature9.misc;

import static org.junit.Assert.assertEquals;

import java.util.ResourceBundle;
import org.junit.Test;

public class UTF8ResourceBundleTest {

  @Test
  public void testGetProperty() {
    final ResourceBundle resourceBundle = ResourceBundle.getBundle("demo");
    assertEquals("你好", resourceBundle.getString("greeting"));
  }
}
