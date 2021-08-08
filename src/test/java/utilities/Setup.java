package utilities;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class Setup {

    private final Logger logger = LogManager.getLogger(Setup.class);
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass
    @Step("Initialize webdriver")
    public void startUp() {
        driver = WebDriverFactory.createDriver(WebDriverType.CHROME);
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        logger.info("Driver initialized!");
    }

    @AfterClass
    @Step("Close webdriver")
    public void closeUp() {
        if (driver != null) driver.quit();
        logger.info("Driver closed!");
    }

    @AfterMethod
    @Step("Clean browser cookies")
    public void clean() {
        driver.manage().deleteAllCookies();
    }

}
