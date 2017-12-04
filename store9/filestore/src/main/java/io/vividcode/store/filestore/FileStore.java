package io.vividcode.store.filestore;

import io.vividcode.store.common.DataConverter;
import io.vividcode.store.common.Persistable;
import io.vividcode.store.common.PersistenceException;
import io.vividcode.store.common.PersistenceService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileStore implements PersistenceService {

  private static final Logger LOGGER = LoggerFactory.getLogger("filestore");
  private final Path storePath = Paths.get("store_data").toAbsolutePath();

  public FileStore() {
    try {
      Files.createDirectories(this.storePath);
      LOGGER.info("Data store directory: {}", this.storePath);
    } catch (final IOException e) {
      LOGGER.warn("Failed to create store directory: {}", this.storePath, e);
    }
  }

  @Override
  public void save(final Persistable persistable) throws PersistenceException {
    final byte[] data = DataConverter.serialize(persistable).getBytes(StandardCharsets.UTF_8);
    try {
      Files.write(this.storePath.resolve(persistable.getId()), data,
          StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    } catch (final IOException e) {
      throw new PersistenceException("Failed to save object " + persistable.getId(), e);
    }
  }
}
