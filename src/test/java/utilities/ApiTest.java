package utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiTest {

    public static void main(String[] args) {
        /**
        Do get call.
        get employee 100
        /api/employees/100
        URL+Headers
        URL=BaseURL+Endpoint
         */
        /*
        Given BaseURL
        And Headers (Accept -> application/json)
        When Endpoint(make a get call with endpoint)
         */
        Response response =
                given().baseUri("https://devenv-apihr-arslan.herokuapp.com") //Base URL
                .and().accept(ContentType.JSON) //Header(Accept)
                .when().get("/api/employees/100"); //call type and endpoint

        response.then().log().all();
        System.out.println(response.getStatusCode());
        System.out.println(response.body().asString());
        System.out.println(response.body().as(HashMap.class));
        Map<String,Object> responseData = response.body().as(HashMap.class);
        System.out.println(responseData.get("employeeId"));

        /*
        POST call
        Request: BaseURL + Endpoint + Headers + Json body
        Given BaseURL
        And Accept ->application json
        And Content Type -> application json
        When Json body
        And send post call

        Response:
        statusCode -> 201
         */

        Response postResponse = given().baseUri("https://devenv-apihr-arslan.herokuapp.com")
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .when().body("{\n" +
                        "    \"department\": {\n" +
                        "        \"departmentId\":60,\n" +
                        "        \"departmentName\": \"IT\",\n" +
                        "        \"location\": {\n" +
                        "            \"locationCity\": \"Chicago\",\n" +
                        "            \"locationCountry\": \"US\",\n" +
                        "            \"locationId\": 45579,\n" +
                        "            \"locationState\": \"cillum nostrud in\"\n" +
                        "        }\n" +
                        "    },\n" +
                        "    \"employeeId\": 298,\n" +
                        "    \"firstName\": \"FiN\",\n" +
                        "    \"job\": {\n" +
                        "        \"jobId\": \"IT_PROG\",\n" +
                        "        \"salary\": 1090903.6678370833,\n" +
                        "        \"title\": \"consequat eu\"\n" +
                        "    },\n" +
                        "    \"lastName\": \"LtN\"\n" +
                        "}")
                .and().post("/api/employees");
        System.out.println(postResponse.statusCode());

        /*
        Delete call:
        REQUEST:
        Given BaseURL
        When delete call

        RESPONSE
        Status code
         */
        Response deleteResponse =  given().baseUri("https://devenv-apihr-arslan.herokuapp.com")
                .when().delete("/api/employees/102");
        System.out.println(deleteResponse.statusCode());







    }

}
