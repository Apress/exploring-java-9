package io.vividcode.feature9.varhandle;

import java.nio.ByteBuffer;

public class HandleTarget {

  public int count = 1;

  public String[] names = new String[]{"Alex", "Bob", "David"};

  public byte[] data = new byte[]{1, 0, 0, 0, 1, 0, 0, 0};

  public ByteBuffer dataBuffer = ByteBuffer.wrap(this.data);
}
