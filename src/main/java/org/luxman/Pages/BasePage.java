package org.luxman.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Actions action;
    protected ArrayList<String> tabs;
    protected List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December");

    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        action = new Actions(driver);
    }

    protected void clickWebElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        action.moveToElement(element).perform();
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public Boolean isElementDisplayed(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }

    public void rejectCookies() {
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("onetrust-reject-all-handler"))).click();

        pause(500);
    }

    public void pause(Integer milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void switchToTab(int tab) {
        tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tab-1));
        pause(2000);
    }

    protected int getCurrentYear() {
        return Year.now().getValue();
    }
}
