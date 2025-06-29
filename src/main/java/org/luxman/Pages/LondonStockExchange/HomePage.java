package org.luxman.Pages.LondonStockExchange;

import org.luxman.Pages.BasePage;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    @FindBy (xpath = ".//a[text()='View FTSE 100']")
    private WebElement viewFtse100;

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public Boolean isViewFtse100LinkDisplayed() {
        return isElementDisplayed(viewFtse100);
    }

    public void openFTSE100List() {
        clickWebElement(viewFtse100);
        switchToTab(2);
    }



}
