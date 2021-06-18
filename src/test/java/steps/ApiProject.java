package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pages.HrAppLoginPage;
import pojos.Department;
import pojos.Location;
import utilities.ApiUtils;
import utilities.Configuration;
import utilities.Driver;
import java.util.List;


public class ApiProject {
    String departmentId;
    Response response;
    Response response2;
    WebDriver driver =Driver.getDriver();
    HrAppLoginPage hrAppLoginPage = new HrAppLoginPage();
    String departmentName ="StudentDepartment1";

    @Given("User sends Create Department API POST call")
    public void user_sends_Create_Department_API_POST_call() {
        String endpoint = "/api/departments";
        Department department = new Department();
        department.setDepartmentId(5);
        department.setDepartmentName(departmentName);
        Location location = new Location();
        location.setLocationCity("Chicago");
        department.setLocation(location);
        response = ApiUtils.postCall(endpoint, department);
        departmentId = department.getDepartmentId().toString();
        System.out.println(departmentId);
        Assert.assertEquals(201, response.statusCode() );

    }

    @Then("User validates department is created in UI in department dropdown")
    public void user_validates_department_is_created_in_UI_in_department_dropdown() {
        driver.get(Configuration.getProperty("HrAppURL"));
        hrAppLoginPage.username.sendKeys(Configuration.getProperty("HrAppUsername"));
        hrAppLoginPage.password.sendKeys(Configuration.getProperty("HrAppPassword"));
        hrAppLoginPage.loginButton.click();
        Select select = new Select(driver.findElement(By.id("department")));
        List<WebElement> elements = select.getOptions();
        boolean ifDepartmentPresent = false;
        for(int i=0; i<elements.size();i++){
            String temp = elements.get(i).getText();
            if(temp.equals(departmentName)){
                ifDepartmentPresent=true;
                break;
            }
        }
        Assert.assertTrue(ifDepartmentPresent);

    }

    @Then("User validates with GET department api call")
    public void user_validates_with_GET_department_api_call() {
        String endpoint = "/api/departments";
        Response response = ApiUtils.getCall(endpoint);
        Assert.assertTrue(response.body().asString().contains(departmentName));
    }

    @Then("User validates with GET department api call using id")
    public void user_validates_with_GET_department_api_call_using_id() {
       String endpoint = "/api/departments/"+departmentId;
        Response response = ApiUtils.getCall(endpoint);
        Department responseBody = response.body().as(Department.class);
        String actualResult = responseBody.getDepartmentName();
        Assert.assertEquals(departmentName,actualResult);
    }

    @When("User sends Delete department api call")
    public void user_sends_Delete_department_api_call() {
         response2 = ApiUtils.deleteCall("/api/departments/"+departmentId);
         Assert.assertEquals(response2.statusCode(), 204);
    }

    @Then("User validates department is not shown in UI dropdown")
    public void user_validates_department_is_not_shown_in_UI_dropdown() {
    /*    driver.get(Configuration.getProperty("HrAppURL"));
        hrAppLoginPage.username.sendKeys(Configuration.getProperty("HrAppUsername"));
        hrAppLoginPage.password.sendKeys(Configuration.getProperty("HrAppPassword"));
        hrAppLoginPage.loginButton.click();*/
        driver.navigate().refresh();
        Select select = new Select(driver.findElement(By.id("department")));
        List<WebElement> elements = select.getOptions();
        boolean ifDepartmentNotPresent = false;
        for(int i=0; i<elements.size();i++){
            String temp = elements.get(i).getText();
            if(temp.equals(departmentName)){
                ifDepartmentNotPresent=true;
                break;
            }
        }
        Assert.assertFalse(ifDepartmentNotPresent); //assertFalse(false)
    }

    @Then("User validates department  is not  found in response of get department call")
    public void user_validates_department_is_not_found_in_response_of_get_department_call() {
        response2 = ApiUtils.getCall("/api/departments/"+departmentId);
        Assert.assertEquals(404, response2.statusCode());
        Assert.assertFalse(response2.body().asString().contains(departmentName));
    }





}
