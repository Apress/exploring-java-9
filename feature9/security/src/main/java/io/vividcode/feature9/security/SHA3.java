package io.vividcode.feature9.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

public class SHA3 {

  public static void main(final String[] args) throws NoSuchAlgorithmException {
    final MessageDigest instance = MessageDigest.getInstance("SHA3-224");
    final byte[] digest = instance.digest("".getBytes());
    System.out.println(Hex.encodeHexString(digest));
  }
}
