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
@Story("View events")
public class TestEPAM extends Setup {

    @Test
    @Feature("View upcoming events")
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

    @Test
    @Feature("View past events")
    @Description("Test view past events and validate event card's info")
    public void testViewPastEvents() {
        MainPage mainPage = new MainPage(driver);
        List<String> cardInfoList = mainPage
                .open()
                .goToEventsPage()
                .pastEventClick()
                .getCardInfo(2);
        Assert.assertEquals(cardInfoList.size(), 5, "There is not enough information on the card!");
    }

    @Test
    @Feature("Upcoming events date")
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

    @Test
    @Feature("Search events by location")
    @Description("Search past events by location and validate event info")
    public void testEventLocation() {
        MainPage mainPage = new MainPage(driver);
        EventsPage eventsPage = new EventsPage(driver);
        int countOfEventsOnPage = mainPage
                .open()
                .goToEventsPage()
                .pastEventClick()
                .selectLocation("Canada")
                .getCountOfEventsOnPage();
        int counterValue = eventsPage.getPastEventsCounterValue();
        Assert.assertEquals(countOfEventsOnPage, counterValue, "Cards quantity does not match!");
        LocalDate[] dates = EventsPage.parseEventDate(eventsPage.getEventDate(eventsPage.getListOfCards().get(0)));
        Assert.assertTrue(dates[1].isBefore(LocalDate.now()));
    }

    @Test
    @Feature("Filtering events by several parameters")
    @Description("Filtering events by several parameters and validate event info")
    public void testFilterEvent() {
        MainPage mainPage = new MainPage(driver);
        TalkCardPage talkCardPage = mainPage.open()
                .goToVideoPage()
                .moreFiltersClick()
                .applyFilter("Category", "Testing")
                .applyFilter("Location", "Belarus")
                .applyFilter("Language", "ENGLISH")
                .goToTalkCard(2);
        String talkLocation = talkCardPage.getTalkLocation();
        String talkLanguage = talkCardPage.getTalkLanguage();
        List<String> talkCategories = talkCardPage.getTalkCategories();
        Assert.assertTrue(talkLocation.contains("Belarus"));
        Assert.assertTrue(talkLanguage.contains("ENGLISH"));
        Assert.assertTrue(talkCategories.contains("Testing"));
    }


    @Test
    @Feature("Search talks")
    @Description("Test talks search")
    public void testEventSearch() {
        MainPage mainPage = new MainPage(driver);
        Assert.assertTrue(mainPage
                .open()
                .goToVideoPage()
                .searchEvents("QA")
                .checkCardsName("QA"), "Card names do not match search value!");
    }


}
