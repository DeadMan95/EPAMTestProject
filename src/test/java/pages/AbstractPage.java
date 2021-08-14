package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage {

    private final String globalLoaderXPath = "//div[contains(@class,'loader')]";

    private final Logger logger = LogManager.getLogger(AbstractPage.class);
    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Ожидание видимости элемента 10 сек
     * @param element веб элемент, появление которого нужно ждать
     */
    protected WebElement waitUntilBecomesVisible(WebElement element) {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Ожидание исчезновения лоадера, т.е. ожидание окончания загрузки страницы
     */
    protected void waitGlobalLoad() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .invisibilityOf(driver.findElement(By.xpath(globalLoaderXPath))));
    }

    /**
     * Функция наводит указаьтель мыши на передаваемый элемент
     * @param element веб элемент, на который требуется навести указаьтель мыши
     */
    protected void moveToElement(WebElement element) {
        logger.info("Move cursor to element");
        new Actions(driver).moveToElement(element).build().perform();
    }

    /**
     * Функция прокручивает страницу вниз на 400 точек
     */
    protected void scrollDown() {
        logger.info("Scrolling page down");
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,400)", "");
    }

}