/**
 * Product persistence
 */

module io.vividcode.store.product.persistence {
  requires transitive io.vividcode.store.product;
  requires transitive io.vividcode.store.common.persistence;
  exports io.vividcode.store.product.persistence;
}