package org.luxman.Pages.LondonStockExchange;

import org.luxman.Pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class OverviewTabPage extends BasePage {

    @FindBy(xpath = "(.//input[@placeholder='YYYY'])[1]")
    private WebElement fromYearField;

    @FindBy(xpath = ".//span[contains(text(),'Chart Type')]/..")
    private WebElement chartTypeDropdown;

    @FindBy(xpath = ".//div[contains(text(),'Bar')]/..")
    private WebElement barChartType;

    @FindBy(xpath = ".//*[contains(@class,'highcharts-point-down')]")
    private List<WebElement> barsDown;

    public OverviewTabPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public Boolean isDateFieldDisplayed() {
        return isElementDisplayed(fromYearField);
    }

    public Boolean isChartTypeDropDownDisplayed() {
        return isElementDisplayed(chartTypeDropdown);
    }

    public Boolean isChartDisplayed() {
        return isElementDisplayed(barsDown.getFirst());
    }

    public void setTimePeriod() {
        String year = String.valueOf(getCurrentYear() - 3);
        clickWebElement(fromYearField);
        fromYearField.sendKeys(Keys.CONTROL + "a");
        fromYearField.sendKeys(Keys.DELETE);
        fromYearField.sendKeys(year);
        pause(1000);
        fromYearField.sendKeys(Keys.ENTER);
        pause(3000);
    }

    public void setChartTypeToBar() {
        clickWebElement(chartTypeDropdown);
        clickWebElement(barChartType);
        pause(2000);
    }

    public void getLowestAverageValueMonthYear() {
        List<Double> barValue = new ArrayList<>();
        HashMap<Double, String> barData = new HashMap<>();

        for (WebElement bar : barsDown) {
            String ariaLabelValue = bar.getAttribute("aria-label");

            String ariaLabelValueSubString = ariaLabelValue.replace("Price of base ric is ", "");
            ariaLabelValueSubString = ariaLabelValueSubString.substring(0,ariaLabelValueSubString.indexOf(".")+3);

            Double monthValue = Double.parseDouble(ariaLabelValueSubString);
            barValue.add(monthValue);
            barData.put(monthValue, ariaLabelValue);
        }

        Double lowestValue = Collections.min(barValue);
        String lowestValueBarData = barData.get(lowestValue);
        String lowestValueMonth = null;

        for (String month : months) {
            if(lowestValueBarData.contains(month)) {
                String year = lowestValueBarData.substring(lowestValueBarData.length()-4);
                lowestValueMonth = month + " " + year;
                break;
            }
        }

        System.out.println("Lowest average index value over last 3 years occurred: " + lowestValueMonth + " with value: " + lowestValue);
    }
}
