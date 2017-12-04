/**
 * File-based storage
 */

import io.vividcode.store.filestore.FileStore;

module io.vividcode.store.filestore {
  requires slf4j.api;
  requires io.vividcode.store.common.persistence;
  provides io.vividcode.store.common.PersistenceService with FileStore;
}