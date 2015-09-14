package com.novachevskyi.expenseslite.data.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ObjectToJsonStringConverter {
  public static String convert(Object object) {
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    String jsonString = null;

    try {
      jsonString = ow.writeValueAsString(object);
    } catch (JsonProcessingException ignored) {

    }

    return jsonString;
  }
}
