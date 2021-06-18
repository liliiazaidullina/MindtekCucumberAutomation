package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.WebOrdersHomePage;
import pages.WebOrdersLoginPage;
import utilities.BrowserUtils;
import utilities.Configuration;
import utilities.Driver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class WebOrdersSteps {

    WebDriver driver = Driver.getDriver();
    WebOrdersLoginPage webOrdersLoginPage = new WebOrdersLoginPage();
    WebOrdersHomePage webOrdersHomePage = new WebOrdersHomePage();

    List<Map<String, Object>> data = new ArrayList<>();
    List<WebElement> row;

    @Given("User navigates to application")
    public void user_navigates_to_application() {
        driver.get(Configuration.getProperty("WebOrdersURL"));
    }

    @When("User logs in with username {string} and password {string}")
    public void user_logs_in_with_username_and_password(String username, String password) {
        webOrdersLoginPage.username.sendKeys(username);
        webOrdersLoginPage.password.sendKeys(password);
        webOrdersLoginPage.loginButton.click();
    }

    @Then("User validates that application is on homepage")
    public void user_validates_that_application_is_on_homepage() {
        String expectedTitle = "Web Orders";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Then("User validates error login message {string}")
    public void user_validates_error_login_message(String expectedMessage) {
        String actualMessage = webOrdersLoginPage.errorMessage.getText();
        Assert.assertEquals(actualMessage, expectedMessage);
    }


    @When("User clicks on Order module")
    public void user_clicks_on_Order_module() {
        webOrdersHomePage.orderButton.click();
    }

    @When("User provides product {string} and quantity {int}")
    public void user_provides_product_and_quantity(String product, Integer quantity) {
        BrowserUtils.selectDropdownByValue(webOrdersHomePage.chooseProduct, product);
        webOrdersHomePage.chooseQuantity.sendKeys(Keys.BACK_SPACE);
        webOrdersHomePage.chooseQuantity.sendKeys(quantity.toString() + Keys.ENTER);
    }

    @Then("User validates total is calculated properly for quantity {int}")
    public void user_validates_total_is_calculated_properly_for_quantity(Integer quantity) {
        int pricePerUnit = Integer.parseInt(webOrdersHomePage.pricePerUnit.getAttribute("value"));
        int discount = Integer.parseInt(webOrdersHomePage.discount.getAttribute("value"));
        int actualTotal = Integer.parseInt(webOrdersHomePage.getTotal.getAttribute("value"));
        int expectedTotal = 0;
        if (discount != 0) {
            expectedTotal = quantity * pricePerUnit * (100 - discount) / 100;
        } else {
            expectedTotal = quantity * pricePerUnit;
        }
        Assert.assertEquals(actualTotal, expectedTotal);
    }


    @When("User creates and order with data")
    public void user_creates_and_order_with_data(DataTable dataTable) {
        // Converting dataTable to List of Maps -> List<Map<String, Object>>
        data = dataTable.asMaps(String.class, Object.class);

        for (int i = 0; i < data.size(); i++) {
            BrowserUtils.selectDropdownByValue(webOrdersHomePage.chooseProduct, data.get(i).get("Product").toString());
            webOrdersHomePage.chooseQuantity.sendKeys(Keys.BACK_SPACE);
            webOrdersHomePage.chooseQuantity.sendKeys(data.get(i).get("Quantity").toString());
            webOrdersHomePage.inputName.sendKeys(data.get(i).get("Customer name").toString());
            webOrdersHomePage.inputStreet.sendKeys(data.get(i).get("Street").toString());
            webOrdersHomePage.inputCity.sendKeys(data.get(i).get("City").toString());
            webOrdersHomePage.inputState.sendKeys(data.get(i).get("State").toString());
            webOrdersHomePage.inputZip.sendKeys(data.get(i).get("Zip").toString());
            webOrdersHomePage.visaCardBtn.click();
            webOrdersHomePage.inputCCNumber.sendKeys(data.get(i).get("Card Nr").toString());
            webOrdersHomePage.inputExpDate.sendKeys(data.get(i).get("Exp Date").toString());
            webOrdersHomePage.processBtn.click();
            user_validates_success_message("New order has been successfully added.");
        }
    }

    @Then("User validates success message {string}")
    public void user_validates_success_message(String expectedResult) {
        String actualResult = webOrdersHomePage.successMessage.getText();
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Then("User validates that created orders are in the list of all orders")
    public void user_validates_that_created_orders_are_in_the_list_of_all_orders() {
        webOrdersHomePage.allOrdersPart.click();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        for (int i = 0, r = data.size() + 1; i < data.size(); i++, r--) {

            row = driver.findElements(By.xpath("//table[@id='ctl00_MainContent_orderGrid']//tr[" + r + "]/td"));
            Assert.assertEquals(row.get(1).getText(), data.get(i).get("Customer name"));
            Assert.assertEquals(row.get(2).getText(), data.get(i).get("Product"));
            Assert.assertEquals(row.get(3).getText(), data.get(i).get("Quantity"));
            Assert.assertEquals(row.get(4).getText(), dateFormat.format(date));
            Assert.assertEquals(row.get(5).getText(), data.get(i).get("Street"));
            Assert.assertEquals(row.get(6).getText(), data.get(i).get("City"));
            Assert.assertEquals(row.get(7).getText(), data.get(i).get("State"));
            Assert.assertEquals(row.get(8).getText(), data.get(i).get("Zip"));
            Assert.assertEquals(row.get(9).getText(), data.get(i).get("Card"));
            Assert.assertEquals(row.get(10).getText(), data.get(i).get("Card Nr"));
            Assert.assertEquals(row.get(11).getText(), data.get(i).get("Exp Date"));
        }
    }

    @When("User selects to delete order {int} and clicks delete selected button")
    public void user_selects_to_delete_order_and_clicks_delete_selected_button(Integer int1) throws InterruptedException {
        webOrdersHomePage.checkboxMarkSmith.click();
        webOrdersHomePage.deleteSelected.click();
    }

    @Then("User validates that order is deleted")
    public void user_validates_that_order_is_deleted() {
       List<WebElement> names = webOrdersHomePage.listOfName;
       for(WebElement name: names){
           System.out.println(name.getText()+"  ");
           Assert.assertTrue( !name.getText().equals("Mark Smith"));
       }
    }



}
