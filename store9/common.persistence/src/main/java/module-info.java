/**
 * Common persistence API
 */

module io.vividcode.store.common.persistence {
  requires slf4j.api;
  requires transitive io.vividcode.store.common;
  exports io.vividcode.store.common.persistence;
  uses io.vividcode.store.common.PersistenceService;
}
