package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.HrAppEmployeePage;
import pages.HrAppLoginPage;
import pojos.Department;
import pojos.Employee;
import utilities.ApiUtils;
import utilities.Configuration;
import utilities.Driver;

import java.util.Map;

public class HrAppApiSteps {
    Response response;
    WebDriver driver = Driver.getDriver();
    HrAppLoginPage hrAppLoginPage = new HrAppLoginPage();
    HrAppEmployeePage hrAppEmployeePage = new HrAppEmployeePage();
    String employeeId;

    @Given("User sends create employee api post call with data")
    public void user_sends_create_employee_api_post_call_with_data(io.cucumber.datatable.DataTable dataTable) {
        //endpoint+body(pojo)

        Map <String, Object> data = dataTable.asMap(String.class, Object.class);
        String endpoint = "/api/employees";
        Employee requestBody = new Employee();
        requestBody.setFirstName(data.get("firstName").toString());
        requestBody.setLastName(data.get("lastName").toString());
        Department department = new Department();
        department.setDepartmentName(data.get("departmentName").toString());
        requestBody.setDepartment(department);

       response = ApiUtils.postCall(endpoint, requestBody);


    }

    @Then("User validates status code {int}")
    public void user_validates_status_code(int expectedStatusCode) {
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Then("User validates data populated in UI")
    public void user_validates_data_populated_in_UI(io.cucumber.datatable.DataTable dataTable) throws InterruptedException {
        Map<String, Object> data = dataTable.asMap(String.class, Object.class);

        driver.get(Configuration.getProperty("HrAppURL"));
        hrAppLoginPage.username.sendKeys(Configuration.getProperty("HrAppUsername"));
        hrAppLoginPage.password.sendKeys(Configuration.getProperty("HrAppPassword"));
        hrAppLoginPage.loginButton.click();
        //response will include JSON body
        Employee responseBody = response.body().as(Employee.class);
         employeeId = responseBody.getEmployeeId().toString();

        hrAppEmployeePage.searchBox.sendKeys(employeeId);
        Thread.sleep(2000);
        hrAppEmployeePage.searchButton.click();

        String actualFirstName = hrAppEmployeePage.firstname.getText();
        String actualLastName  = hrAppEmployeePage.lastname.getText();
        String actualDepartmentName = hrAppEmployeePage.departmentname.getText();

        String expectedFirstName = data.get("firstName").toString();
        String expectedLastName = data.get("lastName").toString();
        String expectedDepartmentNAme = data.get("departmentName").toString();

        Assert.assertEquals(expectedFirstName, actualFirstName);
        Assert.assertEquals(expectedLastName,actualLastName);
        Assert.assertEquals(expectedDepartmentNAme,actualDepartmentName);

    }

    @Then("User validates employee data is persisted into DB")
    public void user_validates_employee_data_is_persisted_into_DB() {

    }

    @Then("User validates data with get employee api call")
    public void user_validates_data_with_get_employee_api_call(io.cucumber.datatable.DataTable dataTable) {
       


    }

}
