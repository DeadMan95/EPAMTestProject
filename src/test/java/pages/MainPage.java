package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends AbstractPage {

    private final String url = "https://events.epam.com/";
    private final By eventsButtonLocator = By.xpath("//*[@href='/events']");
    private final By videoButtonLocator = By.xpath("//*[contains(@href, '/video')]");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open main page")
    public MainPage open() {
        driver.get(url);
        return new MainPage(driver);
    }

    @Step("Go to events page")
    public EventsPage goToEventsPage() {
        driver.findElement(eventsButtonLocator).click();
        return new EventsPage(driver);
    }

    @Step("Go to video page")
    public VideoPage goToVideoPage() {
        driver.findElement(videoButtonLocator).click();
        waitGlobalLoad();
        return new VideoPage(driver);
    }

}
