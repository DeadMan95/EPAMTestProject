package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EventsPage extends AbstractPage {

    private final String eventCardsContainerXPath = "//div[@class='evnt-cards-container']";
    private final String upcomingEventCardsCounterXPath = "//span[contains(text(), 'Upcoming')]" +
            "/parent::*/span[contains(@class, 'evnt-label')]";
    private final String pastEventCardsCounterXPath = "//span[contains(text(), 'Past')]" +
            "/parent::*/span[contains(@class, 'evnt-label')]";
    private final String eventCardXPath = "//div[contains(@class, 'evnt-event-card')]";
    private final String pastEventButtonXPath = "//span[contains(text(), 'Past')]/parent::*";

    private final By eventCardLocator = By.xpath(eventCardXPath);

    public EventsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = eventCardsContainerXPath)
    WebElement eventCardsContainer;

    @FindBy(xpath = upcomingEventCardsCounterXPath)
    WebElement upcomingEventCardsCounter;

    @FindBy(xpath = pastEventCardsCounterXPath)
    WebElement pastEventCardsCounter;

    @FindBy(xpath = pastEventButtonXPath)
    WebElement pastEventButton;

    @Step("Validate count of upcoming events")
    public int getUpcomingEventsCounterValue() {
        return Integer.parseInt(waitUntilBecomesVisible(upcomingEventCardsCounter).getText());
    }

    @Step("Validate count of past events")
    public int getPastEventsCounterValue() {
        return Integer.parseInt(waitUntilBecomesVisible(pastEventCardsCounter).getText());
    }

    public int getCountOfEventsOnPage() {
        List<WebElement> cardsList = getListOfCards();
        return cardsList.size();
    }

    @Step("Go to Past events page")
    public EventsPage pastEventClick() {
        waitUntilBecomesVisible(pastEventButton).click();
        waitGlobalLoad();
        //justWait(1000L);
        return new EventsPage(driver);
    }

    public List<WebElement> getListOfCards() {
        return waitUntilBecomesVisible(eventCardsContainer).findElements(eventCardLocator);
    }

    private String getEventLanguage(WebElement element) {
        return element
                .findElement(By.xpath(".//div[contains(@class,'language-cell')]"))
                .findElement(By.tagName("span"))
                .getText();
    }

    private String getEventName(WebElement element) {
        return element
                .findElement(By.xpath(".//div[contains(@class,'evnt-event-name')]"))
                .findElement(By.tagName("span"))
                .getText();
    }

    public String getEventDate(WebElement element) {
        return element
                .findElement(By.xpath(".//div[contains(@class,'evnt-dates-cell')]"))
                .findElement(By.xpath(".//span[@class='date']"))
                .getText();
    }

    private String getEventStatus(WebElement element) {
        return element
                .findElement(By.xpath(".//div[contains(@class,'evnt-dates-cell')]"))
                .findElement(By.xpath(".//span[contains(@class,'status')]"))
                .getText();
    }

    private String getEventSpeakerName(WebElement element) {
        WebElement photo = element
                .findElement(By.xpath(".//div[@class='evnt-photo-wrapper']"));
        scrollDown();
        moveToElement(photo);
        return driver
                .findElement(By.xpath("//div[@class='evnt-speaker-name']"))
                .getText();
    }

    @Step("Validate card's information")
    public List<String> getCardInfo(int cardNumber) {
        List<WebElement> elementList = getListOfCards();
        WebElement selectedElement = elementList.get(cardNumber);
        String eventLanguage = getEventLanguage(selectedElement);
        String eventName = getEventName(selectedElement);
        String eventDate = getEventDate(selectedElement);
        String eventStatus = getEventStatus(selectedElement);
        String speakerName = getEventSpeakerName(selectedElement);
        return Arrays.asList(eventLanguage, eventName, eventDate, eventStatus, speakerName);
    }

    public static LocalDate[] parseEventDate(String unformattedDate) {
        String[] arr = unformattedDate.split(" - ");
        String[] dateEndArray = arr[1].split(" ");
        String[] dateStartArray = arr[0].split(" ");

        if (dateEndArray[0].length() == 1) dateEndArray[0] = "0" + dateEndArray[0];
        if (dateStartArray[0].length() == 1) dateStartArray[0] = "0" + dateStartArray[0];

        String dateStart = "";
        if (dateStartArray.length == 3)
            dateStart = dateStartArray[0] + " " + dateStartArray[1] + " " + dateStartArray[2];
        else if (dateStartArray.length == 2)
            dateStart = dateStartArray[0] + " " + dateStartArray[1] + " " + dateEndArray[2];
        else if (dateStartArray.length == 1)
            dateStart = dateStartArray[0] + " " + dateEndArray[1] + " " + dateEndArray[2];
        String dateEnd = dateEndArray[0] + " " + dateEndArray[1] + " " + dateEndArray[2];

        DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("dd MMM u", Locale.ENGLISH);
        LocalDate[] dates = new LocalDate[2];
        dates[0] = LocalDate.parse(dateStart, dateFormatter2);
        dates[1] = LocalDate.parse(dateEnd, dateFormatter2);
        return dates;
    }

    @Step("Comparing event date")
    public static boolean checkDate(LocalDate[] eventDate) {
        LocalDate dateNow = LocalDate.now();
        return (eventDate[0].isBefore(dateNow) || (eventDate[0].isEqual(dateNow)))
                && (eventDate[1].isAfter(dateNow) || (eventDate[1].isEqual(dateNow)));
    }

    public EventsPage selectLocation(String location) {
        WebElement locationFilter = driver.findElement(By.id("filter_location"));
        locationFilter.click();
        locationFilter.findElement(By.xpath("./following-sibling::div//input[contains(@class,'evnt-text-fields')]")).sendKeys(location);
        driver.findElement(By.xpath("//span[text()='" + location + "']")).click();
        waitGlobalLoad();
        //justWait(1000L);
        return new EventsPage(driver);
    }


}
