package pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoPage extends AbstractPage {

    private final String moreFiltersButtonXPath = "//span[text()='More Filters']";
    private final String eventCardsContainerXPath = "//div[contains(@class,'evnt-cards-container')]";
    private final String searchInputXPath = "//div[contains(@class,'evnt-search-filter')]" +
            "//input[contains(@class,'evnt-search')]";

    private final By eventCardLocator = By.xpath("//div[contains(@class, 'evnt-talk-card')]");
    private final By talkCardLocator = By.className("evnt-talk-card");
    private final By talkCardNameLocator = By.xpath("//div[@class='evnt-talk-name']//span");
    private final By filtersValueLocator = By.xpath(".//div[@class='evnt-filter-item']");
    private final By filtersValueContainerLocator = By.xpath("./following-sibling::div");

    private final Logger logger = LogManager.getLogger(VideoPage.class);

    public VideoPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = searchInputXPath)
    WebElement searchInput;

    @FindBy(xpath = eventCardsContainerXPath)
    WebElement eventCardsContainer;

    @FindBy(id = "filter_category")
    WebElement categoryFilterElement;

    @FindBy(id = "filter_media")
    WebElement mediaFilterElement;

    @FindBy(id = "filter_location")
    WebElement locationFilterElement;

    @FindBy(id = "filter_speaker")
    WebElement speakerFilterElement;

    @FindBy(id = "filter_language")
    WebElement languageFilterElement;

    @FindBy(id = "filter_talk_level")
    WebElement talkLevelFilterElement;

    @FindBy(xpath = moreFiltersButtonXPath)
    WebElement moreFiltersButton;


    @Step("Insert value \"{0}\" into search field")
    public VideoPage searchEvents(String searchValue) {
        waitUntilBecomesVisible(searchInput).sendKeys(searchValue);
        logger.info("Searching talks by " + searchValue);
        waitStartOfGlobalLoad();
        waitEndOfGlobalLoad();
        return new VideoPage(driver);
    }

    public List<WebElement> getListOfCards() {
        return waitUntilBecomesVisible(eventCardsContainer).findElements(eventCardLocator);
    }

    @Step("Validate talks search result")
    public boolean checkCardsName(String expectedValue) {
        for (WebElement elem : getListOfCards()
        ) {
            if (!elem.findElement(talkCardNameLocator).getText().contains(expectedValue))
                return false;
        }
        return true;
    }

    @Step("Click \"More filters\"")
    public VideoPage moreFiltersClick() {
        waitEndOfGlobalLoad();
        moreFiltersButton.click();
        return new VideoPage(driver);
    }

    public Map<String, WebElement> getFilterMap() {
        List<WebElement> listElement = Arrays.asList(
                categoryFilterElement,
                mediaFilterElement,
                locationFilterElement,
                speakerFilterElement,
                languageFilterElement,
                talkLevelFilterElement);
        Map<String, WebElement> filterMap = new HashMap<>();
        for (WebElement elem : listElement) {
            filterMap.put(elem.findElement(By.tagName("span")).getText(), elem);
        }
        return filterMap;
    }

    public Map<String, WebElement> getFilterValuesMap(WebElement element) {
        List<WebElement> filterValuesList = element
                .findElement(filtersValueContainerLocator)
                .findElements(filtersValueLocator);
        Map<String, WebElement> filterValuesMap = new HashMap<>();
        for (WebElement elem : filterValuesList) {
            WebElement filterValue = elem.findElement(By.tagName("label"));
            filterValuesMap.put(filterValue.getText(), filterValue);
        }
        return filterValuesMap;
    }

    @Step("Apply {0} filter with value \"{1}\"")
    public VideoPage applyFilter(String filterType, String value) {
        Map<String, WebElement> filterMap = getFilterMap();
        WebElement filterTypeElement = filterMap.get(filterType);
        filterTypeElement.click();
        Map<String, WebElement> filterValuesMap = getFilterValuesMap(filterTypeElement);
        filterValuesMap.get(value).click();
        waitEndOfGlobalLoad();
        filterTypeElement.click();
        logger.info("Apply " + filterType + " filter with value " + value);
        return new VideoPage(driver);
    }

    public List<WebElement> getTalksCards() {
        return driver.findElements(talkCardLocator);
    }

    @Step("Go to Talks card number {0}")
    public TalkCardPage goToTalkCard(int cardNumber) {
        getTalksCards().get(cardNumber - 1).click();
        waitEndOfGlobalLoad();
        logger.info("Go to talk number " + cardNumber);
        return new TalkCardPage(driver);
    }

}
