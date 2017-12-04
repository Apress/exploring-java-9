package io.vividcode.feature9.security;

import java.security.DrbgParameters;
import java.security.DrbgParameters.Capability;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Hex;

public class DRBGRandom {

  public static void main(final String[] args) throws NoSuchAlgorithmException {
    final DrbgParameters.Instantiation instantiation =
        DrbgParameters.instantiation(
            256,
            Capability.PR_AND_RESEED,
            "HelloWorld".getBytes()
        );
    final SecureRandom secureRandom =
        SecureRandom.getInstance("DRBG", instantiation);
    final DrbgParameters.NextBytes nextBytes =
        DrbgParameters.nextBytes(-1, true, "Hello".getBytes());
    final byte[] result = new byte[32];
    secureRandom.nextBytes(result, nextBytes);
    System.out.println(Hex.encodeHexString(result));
    final DrbgParameters.Reseed reseed =
        DrbgParameters.reseed(true, null);
    secureRandom.reseed(reseed);
    secureRandom.nextBytes(result, nextBytes);
    System.out.println(Hex.encodeHexString(result));
  }
}
