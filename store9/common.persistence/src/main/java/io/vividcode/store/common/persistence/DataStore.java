package io.vividcode.store.common.persistence;

import io.vividcode.store.common.Persistable;
import io.vividcode.store.common.PersistenceException;
import io.vividcode.store.common.PersistenceService;
import java.util.Optional;
import java.util.ServiceLoader;

public class DataStore<T extends Persistable> {

  private final Optional<PersistenceService> persistenceServices;

  public DataStore() {
    this.persistenceServices = ServiceLoader.load(PersistenceService.class).findFirst();
  }

  public void save(final T object) throws PersistenceException {
    if (this.persistenceServices.isPresent()) {
      this.persistenceServices.get().save(object);
    }
  }
}
