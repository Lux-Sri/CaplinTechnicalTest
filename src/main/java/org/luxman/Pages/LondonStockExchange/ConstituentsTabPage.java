package org.luxman.Pages.LondonStockExchange;

import org.luxman.Pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;

public class ConstituentsTabPage extends BasePage {

    @FindBy(xpath = ".//table[contains(@class,'ftse-index-table')]")
    private WebElement constituentsTable;

    @FindBy (xpath = ".//th[contains(@class,'percentualchange')]")
    private WebElement changePercentageDropdown;

    @FindBy (xpath = ".//th[contains(@class,'percentualchange')]//span[contains(@class,'clickable') and not(contains(@class,'reverse'))]")
    private WebElement changePercentageSortedHighToLow;

    @FindBy (xpath = ".//th[contains(@class,'percentualchange')]//span[contains(@class,'clickable') and contains(@class,'reverse')]")
    private WebElement changePercentageSortedLowToHigh;

    @FindBy (xpath = ".//th[contains(@class,'marketcap')]")
    private WebElement marketCapDropdown;

    @FindBy (xpath = ".//th[contains(@class,'marketcap')]//span[contains(@class,'clickable') and not(contains(@class,'reverse'))]")
    private WebElement marketCapSortedHighToLow;

    @FindBy (xpath = ".//th[contains(@class,'marketcap')]//span[contains(@class,'clickable') and contains(@class,'reverse')]")
    private WebElement marketCapSortedLowToHigh;

    @FindBy (xpath = ".//a[contains(@href,'page=')]")
    private List<WebElement> tablePages;

    @FindBy (xpath = ".//section[@id='ftse-index-table']//table//tbody//tr//td[contains(@class,'name')]")
    private List<WebElement> constituentsNameEntries;

    @FindBy (xpath = ".//section[@id='ftse-index-table']//table//tbody//tr//td[contains(@class,'percentualchange')]")
    private List<WebElement> constituentsPercentageChangeEntries;

    @FindBy (xpath = ".//section[@id='ftse-index-table']//table//tbody//tr//td[contains(@class,'marketcapitalization')]")
    private List<WebElement> constituentsMarketCapEntries;

    Wait<WebDriver> fluentWait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(10))
            .pollingEvery(Duration.ofMillis(500))
            .ignoring(StaleElementReferenceException.class);

    public ConstituentsTabPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public Boolean isConstituentsTableDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(constituentsTable)).isDisplayed();
    }

    public Boolean sortColumn(String column, String sort) {
        switch (column) {
            case "Change %" -> {
                clickWebElement(changePercentageDropdown);
                clickWebElement(driver.findElement(By.xpath(".//span[@class='dropmenu dropdown expanded']//div[@title='" + sort + "']")));
                if (sort.equalsIgnoreCase("Highest – lowest")) {
                    return wait.until(ExpectedConditions.visibilityOf(changePercentageSortedHighToLow)).isDisplayed();
                } else {
                    return wait.until(ExpectedConditions.visibilityOf(changePercentageSortedLowToHigh)).isDisplayed();
                }
            }
            case "Market Cap" -> {
                clickWebElement(marketCapDropdown);
                clickWebElement(driver.findElement(By.xpath(".//span[@class='dropmenu dropdown expanded']//div[@title='" + sort + "']")));
                if (sort.equalsIgnoreCase("Highest – lowest")) {
                    return wait.until(ExpectedConditions.visibilityOf(marketCapSortedHighToLow)).isDisplayed();
                } else {
                    return wait.until(ExpectedConditions.visibilityOf(marketCapSortedLowToHigh)).isDisplayed();
                }
            }
        }

        return null;
    }

    public void displayResultsOnConsole(LinkedHashMap<String, String> results, String column) {
        for (String name : results.keySet()) {
            System.out.println("Constituent Name: " + name + "\n" + column + ": " + results.get(name) + "\n");
        }
    }

    public LinkedHashMap<String, String> getTopTenNamesAndPercentageChange() {
        LinkedHashMap<String, String> topTenConstituents = new LinkedHashMap<>();

        pause(2000);

        for (int i = 1; i < 11; i++) {
            fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(.//section[@id='ftse-index-table']//td[contains(@class,'clickable instrument-name')]//a)["+i+"]")));
            topTenConstituents.put(constituentsNameEntries.get(i-1).getText().trim(), constituentsPercentageChangeEntries.get(i-1).getText().trim());
        }

        return topTenConstituents;
    }

    public LinkedHashMap<String, String> getAllNamesAndMarketCapExceeding(String exceedingValue) {
        LinkedHashMap<String, String> allConstituentsMarketCap = new LinkedHashMap<>();

        for (WebElement tablePage : tablePages) {
            Boolean isMarketCapGreater = false;
            for (int i = 1; i < constituentsNameEntries.size() + 1; i++) {

                pause(1000);

                fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(.//section[@id='ftse-index-table']//td[contains(@class,'clickable instrument-name')]//a)[" + i + "]")));

                String constituentMarketCap = constituentsMarketCapEntries.get(i-1).getText().trim();
                isMarketCapGreater = isMarketCapGreaterThan(constituentMarketCap, exceedingValue);

                if (isMarketCapGreater) {
                    allConstituentsMarketCap.put(constituentsNameEntries.get(i-1).getText().trim(), constituentMarketCap);
                } else {
                    break;
                }
            }

            if (isMarketCapGreater) {
                clickWebElement(tablePage);
            } else {
                break;
            }
        }

        return allConstituentsMarketCap;
    }

    public Boolean isMarketCapGreaterThan(String marketCap, String exceedingValue) {
        Boolean isGreaterThan = false;
        String marketCapNew = marketCap.replaceAll(",", "");
        String exceedingValueNew = exceedingValue.replaceAll(",", "");

        if(Double.parseDouble(marketCapNew) > Double.parseDouble(exceedingValueNew))
            isGreaterThan = true;

        return isGreaterThan;
    }
}
