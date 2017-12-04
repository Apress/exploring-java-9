module feature.logging {
  requires transitive slf4j.api;
  provides java.lang.System.LoggerFinder with io.vividcode.feature9.logging.SLF4JLoggerFinder;
}