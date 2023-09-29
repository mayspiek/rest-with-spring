package br.com.mayara.integrationtests.swagger;

import br.com.mayara.config.TestConfig;
import br.com.mayara.integrationtests.testcontainer.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void shouldDisplaySwaggerUiPage(){
        var content =
            given()
                    .basePath("/swagger-ui/index.html")
                    .port(TestConfig.SERVER_PORT)
                    .when()
                        .get()
                    .then()
                        .statusCode(200)
                    .extract()
                    .body().asString();
        Assertions.assertTrue(content.contains("Swagger UI"));
    }
}
