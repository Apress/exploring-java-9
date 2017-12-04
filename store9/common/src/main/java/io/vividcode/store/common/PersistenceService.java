package io.vividcode.store.common;

public interface PersistenceService {

  void save(final Persistable persistable) throws PersistenceException;
}
