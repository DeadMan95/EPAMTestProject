package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends AbstractPage {

    private final String url = "https://events.epam.com/";
    private final String eventsButtonXPath = "//*[@href='/events']";
    private final String videoButtonXPath = "//*[contains(@href, '/video')]";

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = eventsButtonXPath)
    WebElement eventsButton;

    @FindBy(xpath = videoButtonXPath)
    WebElement videoButton;

    @Step("Open main page")
    public MainPage open() {
        driver.get(url);
        waitEndOfGlobalLoad();
        return new MainPage(driver);
    }

    @Step("Go to events page")
    public EventsPage goToEventsPage() {
        eventsButton.click();
        waitEndOfGlobalLoad();
        return new EventsPage(driver);
    }

    @Step("Go to video page")
    public VideoPage goToVideoPage() {
        videoButton.click();
        waitEndOfGlobalLoad();
        return new VideoPage(driver);
    }

}
