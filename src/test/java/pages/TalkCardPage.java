package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class TalkCardPage extends AbstractPage {

    private final String talkDetailsContainerXPath = "//div[@class='evnt-card-cell details-cell']";

    private final By talkCardCategoryContainerLocator = By.xpath("//div[contains(@class,'topics')]");
    private final By talkCardDurationContainerLocator = By.xpath("//div[contains(@class,'duration')]");
    private final By talkCardLanguageContainerLocator = By.xpath("//div[contains(@class,'language')]");
    private final By talkCardLocationContainerLocator = By.xpath("//div[contains(@class,'location')]");
    private final By talkCardDateContainerLocator = By.xpath("//div[contains(@class,'date')]");

    private final Logger logger = LogManager.getLogger(TalkCardPage.class);

    public TalkCardPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = talkDetailsContainerXPath)
    WebElement talkDetailsContainer;

    public String getTalkDate() {
        WebElement dateContainer = talkDetailsContainer.findElement(talkCardDateContainerLocator);
        StringBuilder talksDateTime = new StringBuilder();
        for (WebElement elem : dateContainer.findElements(By.tagName("span"))) {
            talksDateTime.append(elem.getText());
        }
        String date = talksDateTime.toString();
        logger.info("Talk date is " + date);
        return date;
    }

    public String getTalkLocation() {
        WebElement locationContainer = talkDetailsContainer.findElement(talkCardLocationContainerLocator);
        String location = locationContainer.findElement(By.tagName("span")).getText();
        logger.info("Talk location is " + location);
        return location;
    }

    public String getTalkLanguage() {
        WebElement languageContainer = talkDetailsContainer.findElement(talkCardLanguageContainerLocator);
        String lang = languageContainer.findElement(By.tagName("span")).getText();
        logger.info("Talk language is " + lang);
        return lang;
    }

    public String getTalkDuration() {
        WebElement durationContainer = talkDetailsContainer.findElement(talkCardDurationContainerLocator);
        String duration = durationContainer.findElement(By.tagName("span")).getText();
        logger.info("Talk duration is " + duration);
        return duration;
    }

    public List<String> getTalkCategories() {
        WebElement categoryContainer = talkDetailsContainer.findElement(talkCardCategoryContainerLocator);
        List<String> topicList = new ArrayList<>();
        for (WebElement elem : categoryContainer.findElements(By.xpath("./div/div"))) {
            topicList.add(elem.findElement(By.tagName("label")).getText());
        }
        return topicList;
    }

}
