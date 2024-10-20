package ch.fhnw.webec.contactlist.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Optional;

public class FormPage {
    public FormPage(WebDriver driver, int port) {
        driver.navigate().to("http://localhost:" + port + "/contacts");
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[data-se=contact-entry]")
    private List<WebElement> linkEntries;
    @FindBy(css = "[data-se=contact-details]")
    private WebElement contactDetails;
    @FindBy(css = "[data-se=search-field]")
    private WebElement searchField;
    @FindBy(css = "[data-se=search-button]")
    private WebElement searchButton;


    public List<WebElement> getLinkEntries() {
        return linkEntries;
    }

    public WebElement getContactDetails() {
        return contactDetails;
    }

    public WebElement getSearchButton() {
        return searchButton;
    }

    public WebElement getSearchField() {
        return searchField;
    }
}
