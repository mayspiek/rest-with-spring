package br.com.mayara.integrationtests.controller.withjson;

import br.com.mayara.config.TestConfig;
import br.com.mayara.integrationtests.testcontainer.AbstractIntegrationTest;
import br.com.mayara.integrationtests.vo.PersonVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonVO person;
    //coloca em primeiro na ordem de testes
    @BeforeAll
    public static void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        person = new PersonVO();
    }
    @Test
    @Order(1)
    public void createTest() throws JsonProcessingException {
        mockPerson();
        // especificação está aqui porque nesse momento o spring esta em execução
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_MAYARINHA)
                .setBasePath("/api/person/v1")
                .setPort(TestConfig.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .build();

        var content =
            given().spec(specification)
                    .contentType(TestConfig.CONTENT_TYPE_JSON)
                        .body(person)
                        .when()
                        .post()
                    .then()
                        .statusCode(200)
                            .extract()
                            .body()
                                .asString();

        PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
        person = createdPerson;
        assertNotNull(createdPerson);

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);

        assertEquals("Richard", createdPerson.getFirstName());
        assertEquals("Stallman", createdPerson.getLastName());
        assertEquals("New York City, New York, Us", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }
    @Test
    @Order(2)
    public void testCreateWithWrongOrigin() {
        mockPerson();
        // especificação está aqui porque nesse momento o spring esta em execução
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_MAYARA)
                .setBasePath("/api/person/v1")
                .setPort(TestConfig.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .build();

        var content =
            given().spec(specification)
                    .contentType(TestConfig.CONTENT_TYPE_JSON)
                        .body(person)
                        .when()
                        .post()
                    .then()
                        .statusCode(403)
                            .extract()
                            .body()
                                .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);

    }

    @Test
    @Order(3)
    public void testFindById() throws JsonProcessingException {
        mockPerson();
        // especificação está aqui porque nesse momento o spring esta em execução
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_MAYARINHA)
                .setBasePath("/api/person/v1")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given().spec(specification)
                        .contentType(TestConfig.CONTENT_TYPE_JSON)
                            .pathParams("id", person.getId())
                            .when()
                            .get("{id}")
                        .then()
                            .statusCode(200)
                                .extract()
                                .body()
                                    .asString();

        PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
        person = persistedPerson;
        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());

        assertTrue(persistedPerson.getId() > 0);

        assertEquals("Richard", persistedPerson.getFirstName());
        assertEquals("Stallman", persistedPerson.getLastName());
        assertEquals("New York City, New York, Us", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }    @Test
    @Order(4)
    public void testFindByIdWithWrongOrigin() {
        mockPerson();
        // especificação está aqui porque nesse momento o spring esta em execução
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_MAYARA)
                .setBasePath("/api/person/v1")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .build();

        var content =
                given().spec(specification)
                        .contentType(TestConfig.CONTENT_TYPE_JSON)
                            .pathParams("id", person.getId())
                            .when()
                            .get("{id}")
                        .then()
                            .statusCode(403)
                                .extract()
                                .body()
                                    .asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    private void mockPerson() {
        person.setFirstName("Richard");
        person.setLastName("Stallman");
        person.setAddress("New York City, New York, Us");
        person.setGender("Male");
    }
}
