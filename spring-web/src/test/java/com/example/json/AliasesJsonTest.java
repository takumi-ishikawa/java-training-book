package com.example.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import java.util.List;
import org.junit.jupiter.api.Test;

class AliasesJsonTest {
  @Test
  void test() throws JsonProcessingException {
    AliasesJson aliasesJson =
        new AliasesJson(
            1L, 2L, 2L, List.of(new AliasJson(1L, "test", "valueTest", "https://example.com")));
    ObjectMapper objectMapper =
        new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    String json = objectMapper.writeValueAsString(aliasesJson);
    System.out.println(json);
  }
}
