package eu.wiegandt.openworkshoporganizer;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class OpeningsResourceTest {
  private static final Logger LOG = LoggerFactory.getLogger(OpeningsResourceTest.class);

  @Test
  void testSaveOpening() {
    OpeningTimes opening =
        new OpeningTimes(
            "Test opening", LocalTime.MIN, LocalTime.NOON, DayOfWeek.MONDAY, DayOfWeek.THURSDAY);
    Response response =
        given()
            .when()
            .contentType(ContentType.JSON)
            .body(opening, ObjectMapperType.JACKSON_2)
            .post("/service/events/openings");
    LOG.debug(response.andReturn().body().prettyPrint());
    OpeningTimes responseObject = response.then().statusCode(200).extract().as(OpeningTimes.class);

    // Set the id because its generated on saving
    opening.setId(responseObject.getId());
    assertThat(responseObject, equalTo(opening));
  }

  @Test
  void testGetOpening() {
    OpeningTimes testdata = saveTestdata().get(0);
    Response response = given().when().get("/service/events/openings/{id}/", testdata.getId());
    OpeningTimes responseObject = response.then().statusCode(200).extract().as(OpeningTimes.class);
    assertThat(responseObject, equalTo(testdata));
  }

  @Test
  void testDeleteOpening() {
    OpeningTimes testdata = saveTestdata().get(0);
    // Delete
    given()
            .when()
            .delete("/service/events/openings/{id}/", testdata.getId())
            .then()
            .statusCode(204);
    // Check if it is deleted
    given().when().get("/service/events/openings/{id}/", testdata.getId()).then().statusCode(204);
  }

  @Test
  void testChangeOpening() {
    OpeningTimes testdata = saveTestdata().get(0);
    testdata.setDescription("Example description");
    Response response =
            given()
                    .when()
                    .contentType(ContentType.JSON)
                    .body(testdata, ObjectMapperType.JACKSON_2)
                    .put("/service/events/openings/");
    LOG.debug(response.andReturn().body().prettyPrint());
    response.then().statusCode(200).extract().as(OpeningTimes.class);

    OpeningTimes changedOpening =
            given()
                    .when()
                    .get("/service/events/openings/{id}", testdata.getId())
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(OpeningTimes.class);
    assertThat(changedOpening, is(testdata));
  }

  private List<OpeningTimes> saveTestdata() {
    OpeningTimes opening1 =
        new OpeningTimes(
            "Test opening", LocalTime.MIN, LocalTime.NOON, DayOfWeek.MONDAY, DayOfWeek.THURSDAY);
    OpeningTimes opening2 =
        new OpeningTimes("Test opening 2", LocalTime.MIN, LocalTime.NOON, DayOfWeek.FRIDAY);
    opening1 =
            given()
                    .contentType(ContentType.JSON)
                    .body(opening1, ObjectMapperType.JACKSON_2)
                    .post("/service/events/openings")
                    .then()
                    .extract()
                    .as(OpeningTimes.class);
    opening2 =
            given()
                    .contentType(ContentType.JSON)
                    .body(opening2, ObjectMapperType.JACKSON_2)
                    .post("/service/events/openings")
                    .then()
                    .extract()
                    .as(OpeningTimes.class);
    return Arrays.asList(opening1, opening2);
  }

  @Test
  void testGetAll() {
    List<OpeningTimes> testdata = saveTestdata();
    Collection<OpeningTimes> openings =
        given()
            .when()
            .get("/service/events/openings")
            .then()
            .statusCode(200)
            .extract()
            .as(new TypeRef<Collection<OpeningTimes>>() {});

    assertThat(openings, is(testdata));
  }

  @AfterEach
  @Transactional
  void cleanUpDatabase() {
    // Delete all won't work because of the element collection
    OpeningTimes.findAll().stream().forEach(PanacheEntityBase::delete);
  }
}
