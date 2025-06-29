
import org.junit.After;
import org.junit.Before;
import org.luxman.Utils.GetProperties;
import org.luxman.WebDriverManagerUtils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @Before
    public void setup() {
        GetProperties getProps = new GetProperties();
        driver = WebDriverFactory.getDriver(getProps.getBrowser());
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @After
    public void tearDown() {
        if(driver != null) {
            WebDriverFactory.quitDriver();
        }
    }
}
