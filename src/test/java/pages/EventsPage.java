package pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private final Logger logger = LogManager.getLogger(EventsPage.class);

    private final String locationFilterId = "filter_location";
    private final String eventCardsContainerXPath = "//div[@class='evnt-cards-container']";
    private final String pastEventButtonXPath = "//span[contains(text(), 'Past')]/parent::*";
    private final String upcomingEventCardsCounterXPath = "//span[contains(text(), 'Upcoming')]" +
            "/parent::*/span[contains(@class, 'evnt-label')]";
    private final String pastEventCardsCounterXPath = "//span[contains(text(), 'Past')]" +
            "/parent::*/span[contains(@class, 'evnt-label')]";

    private final By eventCardLocator = By.xpath("//div[contains(@class, 'evnt-event-card')]");
    private final By speakerPhotoLocator = By.xpath(".//div[@class='evnt-photo-wrapper']");
    private final By eventCardLanguageLocator = By.xpath(".//div[contains(@class,'language-cell')]");
    private final By eventCardNameLocator = By.xpath(".//div[contains(@class,'evnt-event-name')]");
    private final By eventDateContainerLocator = By.xpath(".//div[contains(@class,'evnt-dates-cell')]");
    private final By eventDateLocator = By.xpath(".//span[@class='date']");
    private final By eventStatusLocator = By.xpath(".//span[contains(@class,'status')]");
    private final By speakerNameLocator = By.xpath("//div[@class='evnt-speaker-name']");
    private final By searchLocationFieldLocator = By.xpath("./following-sibling::div" +
            "//input[contains(@class,'evnt-text-fields')]");

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

    @FindBy(id = locationFilterId)
    WebElement locationFilter;

    @Step("Validate count of upcoming events")
    public int getUpcomingEventsCounterValue() {
        int count = Integer.parseInt(waitUntilBecomesVisible(upcomingEventCardsCounter).getText());
        logger.info("Count of upcoming events is " + count);
        return count;
    }

    @Step("Validate count of past events")
    public int getPastEventsCounterValue() {
        int count = Integer.parseInt(waitUntilBecomesVisible(pastEventCardsCounter).getText());
        logger.info("Count of past events is " + count);
        return count;
    }

    public int getCountOfEventsOnPage() {
        int count = getListOfCards().size();
        logger.info("Count of events on page is " + count);
        return count;
    }

    @Step("Go to Past events page")
    public EventsPage pastEventClick() {
        waitUntilBecomesVisible(pastEventButton).click();
        waitEndOfGlobalLoad();
        return new EventsPage(driver);
    }

    public List<WebElement> getListOfCards() {
        return waitUntilBecomesVisible(eventCardsContainer).findElements(eventCardLocator);
    }

    private String getEventLanguage(WebElement element) {
        String lang = element
                .findElement(eventCardLanguageLocator)
                .findElement(By.tagName("span"))
                .getText();
        logger.info("Language of event is " + lang);
        return lang;
    }

    private String getEventName(WebElement element) {
        String name = element
                .findElement(eventCardNameLocator)
                .findElement(By.tagName("span"))
                .getText();
        logger.info("Name of event is " + name);
        return name;
    }

    public String getEventDate(WebElement element) {
        String date = element
                .findElement(eventDateContainerLocator)
                .findElement(eventDateLocator)
                .getText();
        logger.info("Date of event is " + date);
        return date;
    }

    private String getEventStatus(WebElement element) {
        String status = element
                .findElement(eventDateContainerLocator)
                .findElement(eventStatusLocator)
                .getText();
        logger.info("Status of event is " + status);
        return status;
    }

    private String getEventSpeakerName(WebElement element) {
        scrollDown();
        moveToElement(element.findElement(speakerPhotoLocator));
        String speakerName = driver.findElement(speakerNameLocator).getText();
        logger.info("Speaker name of event is " + speakerName);
        return speakerName;
    }

    @Step("Validate card number {0} information")
    public List<String> getCardInfo(int cardNumber) {
        List<WebElement> elementList = getListOfCards();
        WebElement selectedElement = elementList.get(cardNumber - 1);
        return Arrays.asList(getEventLanguage(selectedElement),
                getEventName(selectedElement),
                getEventDate(selectedElement),
                getEventStatus(selectedElement),
                getEventSpeakerName(selectedElement));
    }

    /**
     * Вычисление даты из строки
     * (вышел достаточно страшный метод, но он позволяет взять дату с карточки, не заходя внутрь)
     * @param unformattedDate Строка вида 1 - 4 Jan 2021
     * @return массив LocalDate из 2-х дат
     */
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

    /*@Step("Comparing event date")
    public static boolean checkDate(LocalDate[] eventDate) {
        LocalDate dateNow = LocalDate.now();
        return (eventDate[1].isAfter(dateNow) || (eventDate[1].isEqual(dateNow)));
    }*/

    @Step("Select {0} location")
    public EventsPage selectLocation(String location) {
        locationFilter.click();
        locationFilter.findElement(searchLocationFieldLocator).sendKeys(location);
        driver.findElement(By.xpath("//span[text()='" + location + "']")).click();
        logger.info("Select location filter with value = " + location);
        waitEndOfGlobalLoad();
        return new EventsPage(driver);
    }


}
