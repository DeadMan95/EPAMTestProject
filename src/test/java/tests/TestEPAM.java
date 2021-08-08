package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EventsPage;
import pages.MainPage;
import utilities.Setup;

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
        int countOfUpcomingEvents = mainPage
                .open()
                .goToEventsPage()
                .getCountOfUpcomingEvents();
        int counterValue = eventsPage.getUpcomingEventsCounterValue();
        Assert.assertEquals(countOfUpcomingEvents, counterValue, "Cards quantity does not match!");
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


}
