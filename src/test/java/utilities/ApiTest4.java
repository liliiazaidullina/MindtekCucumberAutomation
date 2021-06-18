package utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.head;

public class ApiTest4 {

    public static void main(String[] args) {
        /**
         * Authentication post api call Request
         * endpoint + headers + body
         */

        Response response = given().baseUri("https://mindtek-restapi.herokuapp.com/").accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body("{\n" +
                        "    \"password\": \"MindtekStudent\",\n" +
                        "    \"username\": \"Mindtek\"\n" +
                        "}")
                .when().post("authenticate");
        String token = response.jsonPath().getString("jwt");
        System.out.println(token);

        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer "+token);

        Response response1 = given().baseUri("https://mindtek-restapi.herokuapp.com/")
                .and().headers(headers)
                .when().get("api/customers");
        System.out.println(response1.body().asString());

    }
}
