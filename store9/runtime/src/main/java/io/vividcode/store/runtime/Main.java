package io.vividcode.store.runtime;

import io.vividcode.store.common.PersistenceException;
import io.vividcode.store.product.Product;
import io.vividcode.store.product.persistence.ProductStore;
import java.math.BigDecimal;

public class Main {

  public static void main(final String[] args) throws PersistenceException {
    final ProductStore productStore = new ProductStore();
    final Product product = new Product("001", "Test product", new BigDecimal("100.0"));
    productStore.save(product);
  }
}
