package ch.fhnw.webec.contactlist.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ContactsPage {

    private WebDriver driver;

    public ContactsPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getContactEntries() {
        return driver.findElements(By.cssSelector("[data-se=contact-entry]"));
    }

    public WebElement getContactDetails() {
        return driver.findElement(By.cssSelector("[data-se=contact-details]"));
    }

    public WebElement getSearchQuery() {
        return driver.findElement(By.cssSelector("[data-se=search-query]"));
    }

    public WebElement getSearchSubmit() {
        return driver.findElement(By.cssSelector("[data-se=search-submit]"));
    }

    public WebElement getSearchReset() {
        return driver.findElement(By.cssSelector("[data-se=search-reset]"));
    }

    public WebElement getNoResultsMessage() {
        return driver.findElement(By.cssSelector("[data-se=no-results]"));
    }

}
