package io.vividcode.feature9.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;

public class TestInputStream {

  private InputStream inputStream;
  private static final String CONTENT = "Hello World";

  @Before
  public void setUp() throws Exception {
    this.inputStream =
        TestInputStream.class.getResourceAsStream("/input.txt");
  }

  @Test
  public void testReadAllBytes() throws Exception {
    final String content = new String(this.inputStream.readAllBytes());
    assertEquals(CONTENT, content);
  }

  @Test
  public void testReadNBytes() throws Exception {
    final byte[] data = new byte[5];
    this.inputStream.readNBytes(data, 0, 5);
    assertEquals("Hello", new String(data));
  }

  @Test
  public void testTransferTo() throws Exception {
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    this.inputStream.transferTo(outputStream);
    assertEquals(CONTENT, outputStream.toString());
  }
}
