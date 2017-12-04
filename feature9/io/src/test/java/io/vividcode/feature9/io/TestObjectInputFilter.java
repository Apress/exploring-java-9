package io.vividcode.feature9.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestObjectInputFilter {

  static class A implements Serializable {

    public int a = 1;
    public B b = new B();
  }

  static class B implements Serializable {

    public String b = "hello";
    public C c = new C();
  }

  static class C implements Serializable {

    public int c = 2;
    public D[] ds = new D[]{new D(), new D()};
  }

  static class D implements Serializable {

    public String d = "world";
  }

  private ByteArrayInputStream objectInput;

  @Before
  public void setUp() throws Exception {
    final A a = new A();
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final ObjectOutputStream outputStream = new ObjectOutputStream(baos);
    outputStream.writeObject(a);
    this.objectInput = new ByteArrayInputStream(baos.toByteArray());
  }

  @Test(expected = InvalidClassException.class)
  public void testFilterByClass() throws Exception {
    final ObjectInputStream inputStream =
        new ObjectInputStream(this.objectInput);
    inputStream.setObjectInputFilter(filterInfo -> {
      if (B.class.isAssignableFrom(filterInfo.serialClass())) {
        return ObjectInputFilter.Status.REJECTED;
      }
      return ObjectInputFilter.Status.UNDECIDED;
    });
    final A a = (A) inputStream.readObject();
    assertEquals(1, a.a);
  }

  @Test
  public void testFilterByDepth() throws Exception {
    final ObjectInputStream inputStream = new
        ObjectInputStream(this.objectInput);
    inputStream.setObjectInputFilter(filterInfo -> {
      if (filterInfo.depth() > 6) {
        return ObjectInputFilter.Status.REJECTED;
      }
      return ObjectInputFilter.Status.UNDECIDED;
    });
    final A a = (A) inputStream.readObject();
    assertEquals(1, a.a);
  }

  @Test(expected = InvalidClassException.class)
  public void testProcessWideFilter() throws Exception {
    ObjectInputFilter.Config.setSerialFilter(filterInfo -> {
      if (C.class.isAssignableFrom(filterInfo.serialClass())) {
        return ObjectInputFilter.Status.REJECTED;
      }
      return ObjectInputFilter.Status.UNDECIDED;
    });
    final ObjectInputStream inputStream =
        new ObjectInputStream(this.objectInput);
    final A a = (A) inputStream.readObject();
    assertEquals(1, a.a);
  }

  @Test(expected = InvalidClassException.class)
  @Ignore
  public void testFilterPattern() throws Exception {
    ObjectInputFilter.Config.setSerialFilter(
        ObjectInputFilter.Config.
            createFilter("!io.vividcode.feature9.**"));
    final ObjectInputStream inputStream =
        new ObjectInputStream(this.objectInput);
    final A a = (A) inputStream.readObject();
    assertEquals(1, a.a);
  }
}
