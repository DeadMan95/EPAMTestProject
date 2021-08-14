package pages;

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
        return talksDateTime.toString();
    }

    public String getTalkLocation() {
        WebElement locationContainer = talkDetailsContainer.findElement(talkCardLocationContainerLocator);
        return locationContainer.findElement(By.tagName("span")).getText();
    }

    public String getTalkLanguage() {
        WebElement languageContainer = talkDetailsContainer.findElement(talkCardLanguageContainerLocator);
        return languageContainer.findElement(By.tagName("span")).getText();
    }

    public String getTalkDuration() {
        WebElement durationContainer = talkDetailsContainer.findElement(talkCardDurationContainerLocator);
        return durationContainer.findElement(By.tagName("span")).getText();
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
