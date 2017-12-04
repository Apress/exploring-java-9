package io.vividcode.feature9.misc;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.stream.Collectors;

public class NetworkInterfaceDemo {

  public static void main(final String[] args) throws SocketException {
    final String allInterfaces = NetworkInterface.networkInterfaces().map(
        networkInterface ->
            String.format("Name:%s%nHost addresses:%s%nSub-interfaces:%s",
                networkInterface.getDisplayName(),
                networkInterface.inetAddresses().map(InetAddress::getHostAddress)
                    .collect(Collectors.joining(", ")),
                networkInterface.subInterfaces().map(NetworkInterface::getName)
                    .collect(Collectors.joining(", ")))
    ).collect(Collectors.joining("\n\n"));
    System.out.println(allInterfaces);
  }
}
