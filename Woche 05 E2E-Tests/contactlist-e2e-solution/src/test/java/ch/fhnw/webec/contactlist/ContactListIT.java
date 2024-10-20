package ch.fhnw.webec.contactlist;

import ch.fhnw.webec.contactlist.pageobjects.ContactsPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ContactListIT {

    @LocalServerPort
    int port;

    WebDriver driver;

    private ContactsPage contactsPage;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setupTest() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        contactsPage = new ContactsPage(driver);
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testContactListAndDetails() {
        driver.get("http://localhost:" + port + "/contacts");

        // contact list
        var entryCount = contactsPage.getContactEntries().size();
        assertEquals(30, entryCount);

        // details
        contactsPage.getContactEntries().getFirst().click();
        var details = contactsPage.getContactDetails().getText();
        assertEquals("First name Mabel\n" +
                "Last name Guppy\n" +
                "Phone numbers\n" +
                "405-580-6403", details);
    }

    @Test
    public void testSearch() {
        driver.get("http://localhost:" + port + "/contacts");

        // search -> 2 results are listed
        contactsPage.getSearchQuery().sendKeys("cat" + Keys.RETURN); // submit using enter key
        assertEquals(2, contactsPage.getContactEntries().size());

        // click first entry -> contact details are shown, search persists
        contactsPage.getContactEntries().getFirst().click();
        assertEquals("cat", contactsPage.getSearchQuery().getAttribute("value"));
        assertEquals(2, contactsPage.getContactEntries().size());
        var details = contactsPage.getContactDetails().getText();
        assertEquals("First name Aileen\n" +
                "Last name Cattrell\n" +
                "Email addresses\n" +
                "mstaves0@opensource.org\n" +
                "vmash1@patch.com\n" +
                "Phone numbers\n" +
                "525-477-4251\n" +
                "331-888-1254\n" +
                "657-694-9841", details);

        // search something with 0 results -> message "no results" is shown
        contactsPage.getSearchReset().click();
        contactsPage.getSearchQuery().sendKeys("dog");
        contactsPage.getSearchSubmit().click(); // this time use button instead of enter
        assertEquals(0, contactsPage.getContactEntries().size());
        assertTrue(contactsPage.getNoResultsMessage().isDisplayed());

        // reset -> everything is shown
        contactsPage.getSearchReset().click();
        assertEquals(30, contactsPage.getContactEntries().size());
    }
}
