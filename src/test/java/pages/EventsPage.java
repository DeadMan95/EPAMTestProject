package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

public class EventsPage extends AbstractPage {

    private final String eventCardsContainerXPath = "//div[@class='evnt-cards-container']";
    private final String eventCardsCounterXPath = "//span[contains(text(), 'Upcoming')]" +
            "/parent::*/span[contains(@class, 'evnt-label')]";
    private final String eventCardXPath = "//div[contains(@class, 'evnt-event-card')]";
    private final String pastEventButtonXPath = "//span[contains(text(), 'Past')]/parent::*";

    private final By eventCardLocator = By.xpath(eventCardXPath);

    public EventsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = eventCardsContainerXPath)
    WebElement eventCardsContainer;

    @FindBy(xpath = eventCardsCounterXPath)
    WebElement eventCardsCounter;

    @FindBy(xpath = pastEventButtonXPath)
    WebElement pastEventButton;

    @Step("Validate count of events")
    public int getUpcomingEventsCounterValue() {
        return Integer.parseInt(waitUntilBecomesVisible(eventCardsCounter).getText());
    }

    public int getCountOfUpcomingEvents() {
        List<WebElement> cardsList = getListOfCards();
        return cardsList.size();
    }

    @Step("Go to Past events page")
    public EventsPage pastEventClick() {
        waitUntilBecomesVisible(pastEventButton).click();
        waitGlobalLoad();
        return new EventsPage(driver);
    }

    public List<WebElement> getListOfCards() {
        return waitUntilBecomesVisible(eventCardsContainer).findElements(eventCardLocator);
    }

    @Step("Validate card's information")
    public List<String> getCardInfo(int cardNumber) {
        justWait(1000L);
        List<WebElement> elementList = getListOfCards();
        WebElement selectedElement = elementList.get(cardNumber);
        String eventLanguage = selectedElement
                .findElement(By.xpath(".//div[contains(@class,'language-cell')]"))
                .findElement(By.tagName("span"))
                .getText();
        String eventName = selectedElement
                .findElement(By.xpath(".//div[contains(@class,'evnt-event-name')]"))
                .findElement(By.tagName("span"))
                .getText();
        String eventDate = selectedElement
                .findElement(By.xpath(".//div[contains(@class,'evnt-dates-cell')]"))
                .findElement(By.xpath(".//span[@class='date']"))
                .getText();
        String eventStatus = selectedElement
                .findElement(By.xpath(".//div[contains(@class,'evnt-dates-cell')]"))
                .findElement(By.xpath(".//span[contains(@class,'status')]"))
                .getText();
        WebElement photo = selectedElement
                .findElement(By.xpath(".//div[@class='evnt-photo-wrapper']"));
        scrollDown();
        moveToElement(photo);
        String speakerName = driver
                .findElement(By.xpath("//div[@class='evnt-speaker-name']"))
                .getText();
        return Arrays.asList(eventLanguage, eventName, eventDate, eventStatus, speakerName);
    }


}
