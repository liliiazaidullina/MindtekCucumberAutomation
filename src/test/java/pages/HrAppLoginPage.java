package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class HrAppLoginPage {

    public HrAppLoginPage(){
        WebDriver driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "username")
    public WebElement username;

    @FindBy(name="password")
    public WebElement password;

    @FindBy(xpath = "//button[@class='btn btn-success']")
    public WebElement loginButton;

}
