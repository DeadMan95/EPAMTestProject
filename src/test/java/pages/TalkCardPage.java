package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class TalkCardPage extends AbstractPage {

    private final String talkDetailsContainerXPath = "//div[@class='evnt-card-cell details-cell']";

    public TalkCardPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = talkDetailsContainerXPath)
    WebElement talkDetailsContainer;

    public String getTalkDate() {
        WebElement dateContainer = talkDetailsContainer.findElement(By.xpath("//div[contains(@class,'date')]"));
        StringBuilder talksDateTime = new StringBuilder();
        for (WebElement elem : dateContainer.findElements(By.tagName("span"))) {
            talksDateTime.append(elem.getText());
        }
        return talksDateTime.toString();
    }

    public String getTalkLocation() {
        WebElement dateContainer = talkDetailsContainer.findElement(By.xpath("//div[contains(@class,'location')]"));
        return dateContainer.findElement(By.tagName("span")).getText();
    }

    public String getTalkLanguage() {
        WebElement dateContainer = talkDetailsContainer.findElement(By.xpath("//div[contains(@class,'language')]"));
        return dateContainer.findElement(By.tagName("span")).getText();
    }

    public String getTalkDuration() {
        WebElement dateContainer = talkDetailsContainer.findElement(By.xpath("//div[contains(@class,'duration')]"));
        return dateContainer.findElement(By.tagName("span")).getText();
    }

    public List<String> getTalkCategories() {
        WebElement dateContainer = talkDetailsContainer.findElement(By.xpath("//div[contains(@class,'topics')]"));
        List<String> topicList = new ArrayList<>();
        for (WebElement elem : dateContainer.findElements(By.xpath("./div/div"))) {
            topicList.add(elem.findElement(By.tagName("label")).getText());
        }
        return topicList;
    }

}
