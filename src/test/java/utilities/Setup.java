package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Setup {

    private final Logger logger = LogManager.getLogger(Setup.class);
    protected WebDriver driver;

    @BeforeMethod(description = "Initialize web driver")
    public void startUp() {
        driver = WebDriverFactory.createDriver(WebDriverType.CHROME);
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        logger.info("Driver initialized!");
    }

    @AfterMethod(description = "Close web driver")
    public void closeUp() {
        if (driver != null) driver.quit();
        logger.info("Driver closed!");
    }

}
