package eu.wiegandt.openworkshoporganizer;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@ApplicationScoped
public class CustomObjectMapperConfig {

  @Singleton
  @Produces
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
    return objectMapper;
  }
}
