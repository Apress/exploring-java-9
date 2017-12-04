package io.vividcode.store.product;

import io.vividcode.store.common.Persistable;
import java.math.BigDecimal;

public class Product implements Persistable {

  private String sku;
  private String name;
  private String description;
  private BigDecimal price;

  public Product() {
  }

  public Product(final String sku, final String name, final BigDecimal price) {
    this.sku = sku;
    this.name = name;
    this.price = price;
  }

  public Product(final String sku, final String name, final String description,
      final BigDecimal price) {
    this.sku = sku;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public String getSku() {
    return this.sku;
  }

  public void setSku(final String sku) {
    this.sku = sku;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return this.price;
  }

  public void setPrice(final BigDecimal price) {
    this.price = price;
  }

  @Override
  public String getId() {
    return this.sku;
  }
}
