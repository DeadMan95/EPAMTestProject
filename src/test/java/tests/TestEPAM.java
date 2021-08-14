package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EventsPage;
import pages.MainPage;
import pages.TalkCardPage;
import utilities.Setup;

import java.time.LocalDate;
import java.util.List;

@Epic("EPAM")
@Feature("View events")
public class TestEPAM extends Setup {


    private final String testLocationCanada = "Canada";
    private final String testLocationBelarus = "Belarus";
    private final String testLanguage = "ENGLISH";
    private final String testTopic = "Testing";
    private final String testSearchValue = "QA";
    private final String testFilterTypeCategory = "Category";
    private final String testFilterTypeLanguage = "Language";
    private final String testFilterTypeLocation = "Location";
    private final int testCardNumber = 2;


    @Test(description = "View upcoming events")
    @Story("View upcoming events")
    @Description("Test view upcoming events and comparing counter value with real count of events cards")
    public void testViewUpcomingEvents() {
        MainPage mainPage = new MainPage(driver);
        EventsPage eventsPage = new EventsPage(driver);
        int countOfEventsOnPage = mainPage
                .open()
                .goToEventsPage()
                .getCountOfEventsOnPage();
        int counterValue = eventsPage.getUpcomingEventsCounterValue();
        Assert.assertEquals(countOfEventsOnPage, counterValue, "Cards quantity does not match!");
    }

    @Test(description = "View past events")
    @Story("View past events")
    @Description("Test view past events and validate event card's info")
    public void testViewPastEvents() {
        MainPage mainPage = new MainPage(driver);
        List<String> cardInfoList = mainPage
                .open()
                .goToEventsPage()
                .pastEventClick()
                .getCardInfo(testCardNumber);
        Assert.assertEquals(cardInfoList.size(), 5, "There is not enough information on the card!");
    }

    @Test(description = "Upcoming events date")
    @Story("View upcoming events")
    @Description("Test events date")
    public void testEventDate() {
        MainPage mainPage = new MainPage(driver);
        EventsPage eventsPage = new EventsPage(driver);
        List<WebElement> cardList = mainPage
                .open()
                .goToEventsPage()
                .getListOfCards();
        Assert.assertTrue(EventsPage.checkDate(EventsPage.parseEventDate(eventsPage.getEventDate(cardList.get(0)))));
    }

    @Test(description = "Search events by location")
    @Story("Search events")
    @Description("Search past events by location and validate event info")
    public void testEventLocation() {
        MainPage mainPage = new MainPage(driver);
        EventsPage eventsPage = new EventsPage(driver);
        int countOfEventsOnPage = mainPage
                .open()
                .goToEventsPage()
                .pastEventClick()
                .selectLocation(testLocationCanada)
                .getCountOfEventsOnPage();
        int counterValue = eventsPage.getPastEventsCounterValue();
        Assert.assertEquals(countOfEventsOnPage, counterValue, "Cards quantity does not match!");
        LocalDate[] dates = EventsPage.parseEventDate(eventsPage.getEventDate(eventsPage.getListOfCards().get(0)));
        Assert.assertTrue(dates[1].isBefore(LocalDate.now()));
    }

    @Test(description = "Filtering events by several parameters")
    @Story("Search talks")
    @Description("Filtering talks by several parameters and validate event info")
    public void testFilterEvent() {
        MainPage mainPage = new MainPage(driver);
        TalkCardPage talkCardPage = mainPage.open()
                .goToVideoPage()
                .moreFiltersClick()
                .applyFilter(testFilterTypeCategory, testTopic)
                .applyFilter(testFilterTypeLocation, testLocationBelarus)
                .applyFilter(testFilterTypeLanguage, testLanguage)
                .goToTalkCard(testCardNumber);
        String talkLocation = talkCardPage.getTalkLocation();
        String talkLanguage = talkCardPage.getTalkLanguage();
        List<String> talkCategories = talkCardPage.getTalkCategories();
        Assert.assertTrue(talkLocation.contains(testLocationBelarus));
        Assert.assertTrue(talkLanguage.contains(testLanguage));
        Assert.assertTrue(talkCategories.contains(testTopic));
    }

    @Test(description = "Search talks")
    @Story("Search talks")
    @Description("Test talks search")
    public void testEventSearch() {
        MainPage mainPage = new MainPage(driver);
        Assert.assertTrue(mainPage
                .open()
                .goToVideoPage()
                .searchEvents(testSearchValue)
                .checkCardsName(testSearchValue), "Card names do not match search value!");
    }


}
