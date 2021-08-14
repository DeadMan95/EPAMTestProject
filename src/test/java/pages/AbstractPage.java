package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage {

    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitUntilBecomesVisible(WebElement element) {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitGlobalLoad() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .invisibilityOf(driver.findElement(By.xpath("//div[contains(@class,'loader')]"))));
    }

    protected void justWait(Long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void moveToElement(WebElement element) {
        new Actions(driver).moveToElement(element).build().perform();
    }

    protected void scrollDown() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,400)", "");
    }

}