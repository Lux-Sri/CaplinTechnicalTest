//import org.junit.jupiter.api.*;
import org.junit.Assert;
import org.junit.Test;
import org.luxman.Pages.LondonStockExchange.ConstituentsTabPage;
import org.luxman.Pages.LondonStockExchange.Ftse100Page;
import org.luxman.Pages.LondonStockExchange.HomePage;
import org.luxman.Pages.LondonStockExchange.OverviewTabPage;

import java.util.LinkedHashMap;

public class LondonStockExchangeWebsiteTests extends BaseTest {

    private Ftse100Page ftse100Page;
    private ConstituentsTabPage constituentsTabPage;

    @Test
    public void findTopTenConstituentsHighestPercentageChange() {
        navigateToFtse100Page();
        Boolean isSorted = constituentsTabPage.sortColumn("Change %", "Highest – lowest");
        Assert.assertTrue("Column not sorted", isSorted);
        LinkedHashMap<String, String> results = constituentsTabPage.getTopTenNamesAndPercentageChange();
        Assert.assertEquals("Results do not contain 10 entries", 10, results.size());
        constituentsTabPage.displayResultsOnConsole(results, "Change %");
    }

    @Test
    public void findTopTenConstituentsLowestPercentageChange() {
        navigateToFtse100Page();
        Boolean isSorted = constituentsTabPage.sortColumn("Change %", "Lowest – highest");
        Assert.assertTrue("Column not sorted", isSorted);
        LinkedHashMap<String, String> results = constituentsTabPage.getTopTenNamesAndPercentageChange();
        Assert.assertEquals("Results do not contain 10 entries", 10, results.size());
        constituentsTabPage.displayResultsOnConsole(results, "Change %");
    }

    @Test
    public void findConstituentsMarketCapExceedsSevenBillion() {
        navigateToFtse100Page();
        Boolean isSorted = constituentsTabPage.sortColumn("Market Cap", "Highest – lowest");
        Assert.assertTrue("Column not sorted", isSorted);
        LinkedHashMap<String, String> results = constituentsTabPage.getAllNamesAndMarketCapExceeding("7,000.00");
        constituentsTabPage.displayResultsOnConsole(results, "Market Cap");
    }

    @Test
    public void findMonthWithLowestIndexValueInPastThreeYears() {
        navigateToFtse100Page();
        ftse100Page.openTab("Overview");
        Assert.assertEquals("FTSE 100 Overview Page Not displayed", "FTSE 100 FTSE overview | London Stock Exchange", driver.getTitle());
        OverviewTabPage overviewTabPage = new OverviewTabPage(driver, wait);
        Assert.assertTrue("From date field not displayed", overviewTabPage.isDateFieldDisplayed());
        overviewTabPage.setTimePeriod();
        Assert.assertTrue("Chart type drop down not displayed", overviewTabPage.isChartTypeDropDownDisplayed());
        overviewTabPage.setChartTypeToBar();
        Assert.assertTrue("Chart not displayed", overviewTabPage.isChartDisplayed());
        overviewTabPage.getLowestAverageValueMonthYear();
    }

    private void navigateToFtse100Page() {
        driver.navigate().to("https://www.londonstockexchange.com/");
        HomePage homePage = new HomePage(driver, wait);
        homePage.rejectCookies();
        Assert.assertEquals("London Stock Exchange page title not as expected", "London Stock Exchange homepage | London Stock Exchange", driver.getTitle());
        Assert.assertTrue("View FTSE 100 link not displayed", homePage.isViewFtse100LinkDisplayed());
        homePage.openFTSE100List();
        ftse100Page = new Ftse100Page(driver, wait);
        constituentsTabPage = new ConstituentsTabPage(driver, wait);
        Assert.assertEquals("FTSE 100 Constituents page title not as expected", "Table - FTSE 100 FTSE constituents | London Stock Exchange", driver.getTitle());
        Assert.assertTrue("Constituents table not displayed", constituentsTabPage.isConstituentsTableDisplayed());
    }
}