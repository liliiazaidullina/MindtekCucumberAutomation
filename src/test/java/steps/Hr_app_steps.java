package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pages.HrAppEmployeePage;
import pages.HrAppLoginPage;
import utilities.Configuration;
import utilities.Driver;
import utilities.JDBCUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Hr_app_steps {
    WebDriver driver = Driver.getDriver();
    HrAppLoginPage hrAppLoginPage = new HrAppLoginPage();
    HrAppEmployeePage hrAppEmployeePage=new HrAppEmployeePage();

    @Given("user navigates to login page")
    public void user_navigates_to_login_page() {
        driver.get(Configuration.getProperty("HrAppURL"));
        String actualTitle = driver.getTitle();
        String expectedTitle = "HrApp";
        Assert.assertEquals(expectedTitle, actualTitle);
    }

    @When("user logs in to HRapp")
    public void user_logs_in_to_HRapp() {
        hrAppLoginPage.username.sendKeys(Configuration.getProperty("HrAppUsername"));
        hrAppLoginPage.password.sendKeys(Configuration.getProperty("HrAppPassword"));
        hrAppLoginPage.loginButton.click();

    }

    @When("create new employee")
    public void create_new_employee() {
        System.out.println("New employee id created");
    }

    @Then("user validates new employee is created in database")
    public void user_validates_new_employee_is_created_in_database() throws SQLException {
    hrAppEmployeePage.searchBox.sendKeys("101"+ Keys.ENTER);
    String expectedFN = hrAppEmployeePage.firstname.getText(); //from UI
    String expectedLN = hrAppEmployeePage.lastname.getText();


        System.out.println(expectedFN);
        System.out.println(expectedLN);


        JDBCUtils.establishConnection();
        List<Map<String, Object>> actual= JDBCUtils.runQuery("select * from employees where employee_id=101"); //from database
        Assert.assertEquals(actual.get(0).get("FIRST_NAME"), expectedFN);
        Assert.assertEquals(expectedLN, actual.get(0).get("LAST_NAME").toString());

        System.out.println(actual.get(0).get("FIRST_NAME").toString());
        System.out.println(actual.get(0).get("LAST_NAME").toString());
        //System.out.println(actual.get(1).get("FIRST_NAME")); //IndexOutOfBoundsException, because we have a condition WHERE employee_id=101, it returns only 1 row

        String expectedDN =hrAppEmployeePage.departmentname.getText();
        System.out.println(expectedDN);
        List<Map<String,Object>> departmentNameActual = JDBCUtils.runQuery("SELECT department_name FROM departments d INNER JOIN " +
                "employees e ON d.department_id=e.department_id WHERE e.employee_id=101");
        System.out.println(departmentNameActual.get(0).get("DEPARTMENT_NAME").toString());
        Assert.assertEquals(expectedDN, departmentNameActual.get(0).get("DEPARTMENT_NAME").toString());
        JDBCUtils.closeConnection();

    }
}
