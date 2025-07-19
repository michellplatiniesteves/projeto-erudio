package br.com.erudio.integracao.container.swagger;

import br.com.erudio.config.TestConfigs;
import br.com.erudio.integracao.container.AbstractIntregrationTest;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerTest extends AbstractIntregrationTest {

    @Test
    public void contextLoads(){
      var contest= given()
               .basePath("/swagger-ui/index.html")
                   .port(TestConfigs.SERVER_PORT)
               .when()
                 .get()
               .then()
                 .statusCode(200)
               .extract()
                  .body()
                    .asString();
        assertTrue(contest.contains("Swagger UI"));
    }

}
