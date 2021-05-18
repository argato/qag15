import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegresinTests {

  @BeforeAll
  static void setup() {
    RestAssured.baseURI = "https://reqres.in";
  }

  @Test
  void findUserInListTest() {
    given()
        .when()
        .get("/api/users?page=2")
        .then()
        .statusCode(200)
        .body("page", is(2))
        .body("total", is(12))
        .body("data.id[0]", is(7))
        .body("data.email[0]", is("michael.lawson@reqres.in"))
        .body("data.first_name[0]", is("Michael"))
        .body("data.last_name[0]", is("Lawson"))
        .body("data.avatar[0]", is("https://reqres.in/img/faces/7-image.jpg"));
  }

  @Test
  void checkSingleUserTest() {
    given()
        .when()
        .get("/api/users/2")
        .then()
        .statusCode(200)
        .body("data.id", is(2))
        .body("data.email", is("janet.weaver@reqres.in"))
        .body("data.first_name", is("Janet"))
        .body("data.last_name", is("Weaver"))
        .body("data.avatar", is("https://reqres.in/img/faces/2-image.jpg"));
  }

  @Test
  void createUserTest() {
    Map<String, Object> data = new HashMap<>();
    data.put("name", "morpheus");
    data.put("job", "leader");

    given()
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .post("/api/users")
        .then()
        .statusCode(201)
        .body("name", is("morpheus"))
        .body("job", is("leader"))
        .body("id", is(notNullValue()))
        .body("createdAt", is(notNullValue()))
        .body("updatedAt", is(nullValue()));
  }

  @Test
  void putUserDataTest() {
    Map<String, Object> data = new HashMap<>();
    data.put("name", "morpheus");
    data.put("job", "zion resident");

    given()
        .contentType(ContentType.JSON)
        .body(data)
        .when()
        .put("/api/users/2")
        .then()
        .statusCode(200)
        .body("name", is("morpheus"))
        .body("job", is("zion resident"))
        .body("updatedAt", is(notNullValue()))
        .body("id", is(nullValue()))
        .body("createdAt", is(nullValue()));
  }

  @Test
  void deleteUserTest() {
    given()
        .when()
        .delete("/api/users/2")
        .then()
        .statusCode(204)
        .body(is(""));
  }
}
