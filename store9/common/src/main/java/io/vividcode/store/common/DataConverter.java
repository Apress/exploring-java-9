package io.vividcode.store.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static final String serialize(final Object input) {
    try {
      return objectMapper.writeValueAsString(input);
    } catch (final JsonProcessingException e) {
      return input.toString();
    }
  }
}
