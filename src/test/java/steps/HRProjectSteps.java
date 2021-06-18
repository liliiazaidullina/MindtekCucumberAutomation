package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.HRProjectPage;
import utilities.Driver;
import utilities.JDBCUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class HRProjectSteps {
    WebDriver driver = Driver.getDriver();
    HRProjectPage hrProjectPage = new HRProjectPage();
    List<Map<String, Object>> actualNames;

    @Given("User navigates to HR page")
    public void user_navigates_to_HR_page() throws SQLException {
        JDBCUtils.establishConnection();
        actualNames = JDBCUtils.runQuery("SELECT first_name, last_name FROM employees WHERE department_id IN (SELECT department_id FROM departments WHERE location_id IN (SELECT location_id FROM locations WHERE country_id IN (SELECT country_id FROM countries WHERE region_id = (SELECT region_id FROM regions WHERE region_name='Europe'))))");
        System.out.println(actualNames.toString());


    }

    @Then("User validates first name and last name")
    public void user_validates_first_name_and_last_name() {
        driver.get("file:///Users/liliiazaidullina/Downloads/HR%20Application.html");
        List<WebElement> expectedFName = hrProjectPage.firstNameEU;
        List<WebElement> expectedLName = hrProjectPage.lastNameEU;
int count=0;
            for (int g = 0; g < expectedFName.size(); g++) {
                System.out.println(actualNames.toString());
               System.out.println(expectedFName.get(g).getText());
               Assert.assertTrue( actualNames.toString().contains(expectedFName.get(g).getText()));
               count++;
            }

        System.out.println(count);
            for (int g = 0; g < expectedLName.size(); g++) {
                Assert.assertTrue(actualNames.toString().contains(expectedLName.get(g).getText()));
            }


    }
}