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
import pages.EtsyHomePage;
import utilities.Configuration;
import utilities.Driver;

import java.util.List;

public class EtsyAppSteps {

    WebDriver driver = Driver.getDriver();
    EtsyHomePage etsyHomePage = new EtsyHomePage();

    @Given("User navigates to Etsy application")
    public void user_navigates_to_Etsy_application() {
        driver.get(Configuration.getProperty("EtsyURL"));
    }

    @When("User searches for {string}")
    public void user_searches_for(String item) {
        etsyHomePage.searchBox.sendKeys(item, Keys.ENTER);
     /*   List<WebElement> word = driver.findElements(By.xpath("//ul[@class='wt-grid wt-grid--block wt-pl-xs-0 tab-reorder-container']/li"));
        System.out.println(word.get(1).getText());*/

    }

    @Then("User validates search results contain")
    public void user_validates_search_results_contain(DataTable dataTable) {
        List<String> keywords = dataTable.asList();
        System.out.println(keywords.get(0));
        System.out.println(keywords.get(2));

        for(int i=0; i<etsyHomePage.resultItems.size();i++ ){
        String itemDescription = etsyHomePage.resultItems.get(i).getText();
            System.out.println(itemDescription);
            boolean isFound=false;
            for(int a=0; a<keywords.size();a++){
                if(itemDescription.toLowerCase().contains(keywords.get(a))){
                    isFound=true;
                }

            }
            Assert.assertTrue(itemDescription, isFound);

        }

    }
}
