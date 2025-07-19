package br.com.erudio.integracao.container;


import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;



import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntregrationTest.Initializer.class)
public class AbstractIntregrationTest {
  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{
      static PostgreSQLContainer<?> postgre = new PostgreSQLContainer<>("docker pull postgres:14.18");

      private static void startContainer() {
          Startables.deepStart(Stream.of(postgre)).join();
      }
      @Override
      public void initialize(ConfigurableApplicationContext applicationContext) {
          startContainer();
          ConfigurableEnvironment context = applicationContext.getEnvironment();
          MapPropertySource properties = new MapPropertySource("testcontainers",
                  (Map)createConnectionConfiguration());
          context.getPropertySources().addFirst(properties);

      }

      private static Map<String, Object> createConnectionConfiguration() {
             return Map.of(
                     "spring.datasource.url",postgre.getJdbcUrl(),
                     "spring.datasource.username",postgre.getUsername(),
                     "spring.datasource.password",postgre.getPassword());
      }


  }
}
