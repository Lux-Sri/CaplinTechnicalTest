package org.luxman.Pages.LondonStockExchange;

import org.luxman.Pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ftse100Page extends BasePage {

    @FindBy (xpath = ".//a[@class='logo']")
    private WebElement logo;

    public Ftse100Page(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public void openTab(String tab) {
        clickWebElement(driver.findElement(By.xpath(".//div[@class='tab-nav-container']//*[contains(text(),'"+tab+"')]")));
        wait.until(ExpectedConditions.elementToBeClickable(logo));
    }
}
