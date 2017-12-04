package io.vividcode.feature9.module;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.lang.module.ModuleDescriptor;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeriveAutomaticModuleName {

  static final Pattern DASH_VERSION = Pattern.compile("-(\\d+(\\.|$))");
  static final Pattern NON_ALPHANUM = Pattern.compile("[^A-Za-z0-9]");
  static final Pattern REPEATING_DOTS = Pattern.compile("(\\.)(\\1)+");
  static final Pattern LEADING_DOTS = Pattern.compile("^\\.");
  static final Pattern TRAILING_DOTS = Pattern.compile("\\.$");

  public Tuple2<String, String> deriveModuleName(final String fileName) {
    Objects.requireNonNull(fileName);
    String name = fileName;
    String version = null;
    if (fileName.endsWith(".jar")) {
      name = fileName.substring(0, fileName.length() - 4);
    }

    final Matcher matcher = DASH_VERSION.matcher(name);
    if (matcher.find()) {
      final int start = matcher.start();

      try {
        final String tail = name.substring(start + 1);
        ModuleDescriptor.Version.parse(tail);
        version = tail;
      } catch (final IllegalArgumentException ignore) {
      }

      name = name.substring(0, start);
    }
    return Tuple.of(cleanModuleName(name), version);
  }

  public void displayModuleName(final String fileName) {
    final Tuple2<String, String> result = deriveModuleName(fileName);
    System.out.printf("%s => %s [%s]%n", fileName, result._1, result._2);
  }

  private String cleanModuleName(String mn) {
    // replace non-alphanumeric
    mn = NON_ALPHANUM.matcher(mn).replaceAll(".");

    // collapse repeating dots
    mn = REPEATING_DOTS.matcher(mn).replaceAll(".");

    // drop leading dots
    if (mn.length() > 0 && mn.charAt(0) == '.') {
      mn = LEADING_DOTS.matcher(mn).replaceAll("");
    }

    // drop trailing dots
    final int len = mn.length();
    if (len > 0 && mn.charAt(len - 1) == '.') {
      mn = TRAILING_DOTS.matcher(mn).replaceAll("");
    }

    return mn;
  }

  public static void main(final String[] args) {
    final DeriveAutomaticModuleName moduleName = new DeriveAutomaticModuleName();
    moduleName.displayModuleName("mylib.jar");
    moduleName.displayModuleName("slf4j-api-1.7.25.jar");
    moduleName.displayModuleName("hibernate-jpa-2.1-api-1.0.0.Final.jar");
    moduleName.displayModuleName("spring-context-support-4.3.6.RELEASE.jar");
  }
}
