package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class HrAppEmployeePage {
    public HrAppEmployeePage(){
        WebDriver driver = Driver.getDriver();
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//a[contains(text(),'Create new Employee')]")
    public WebElement createNewEmployee;

    @FindBy(xpath  ="//input[@placeholder='Employee ID..']")
    public WebElement searchBox;

    @FindBy(id = "searchButton")
    public WebElement searchButton;

    @FindBy(xpath = "//td[2]")
    public WebElement firstname;

    @FindBy(xpath ="//td[3]")
    public WebElement lastname;

    @FindBy(xpath ="//td[4]")
    public WebElement departmentname;



}
