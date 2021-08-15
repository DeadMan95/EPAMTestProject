package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory {

    public static WebDriver createDriver(WebDriverType webDriverType) {
        WebDriver driver = null;
        DesiredCapabilities capabilities;
        switch (webDriverType) {
            case CHROME:
                capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("chrome");
                capabilities.setVersion("91.0");
                capabilities.setPlatform(Platform.WIN10);
                try {
                    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return driver;
            case FIREFOX:
                capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("firefox");
                capabilities.setVersion("90.0.2");
                capabilities.setPlatform(Platform.WIN10);
                try {
                    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return driver;
            case OPERA:
                WebDriverManager.operadriver().setup();
                return new OperaDriver();
            default:
                return null;
        }
    }


}
