package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Setup {

    private final Logger logger = LogManager.getLogger(Setup.class);

    @BeforeMethod(description = "Initialize web driver")
    public WebDriver startUp() {
        WebDriver driver = WebDriverFactory.createDriver(WebDriverType.CHROME);
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        logger.info("Driver initialized!");
        return driver;
    }

    @AfterMethod(description = "Close web driver")
    public void closeUp(WebDriver driver) {
        if (driver != null) driver.quit();
        logger.info("Driver closed!");
    }

}
