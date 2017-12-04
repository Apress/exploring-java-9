/**
 * Common API
 *
 */

module io.vividcode.store.common {
  requires jackson.core;
  requires jackson.databind;
  requires jackson.annotations;
  exports io.vividcode.store.common;
  opens config;
}
