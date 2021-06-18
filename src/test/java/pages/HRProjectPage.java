package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class HRProjectPage {

    public HRProjectPage(){
        WebDriver driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy (xpath = "//table[@id='europe-employees']//tbody[2]//td[1]")
    public List<WebElement> firstNameEU;

    @FindBy (xpath = "//table[@id='europe-employees']//tbody[2]//td[2]")
    public List<WebElement> lastNameEU;

    @FindBy (xpath = "//table[@id='number-of-employees-by-department-name']//tbody[2]/tr")
    public List<WebElement> empActive;
}
