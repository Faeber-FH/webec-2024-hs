package ch.fhnw.webec.contactlist.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Optional;

public class FormPage {
    public FormPage(WebDriver driver, int port) {
        driver.navigate().to("http://localhost:" + port + "/");
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "inches")
    private List<WebElement> inchesField;
    @FindBy(css = "input[type=submit]")
    private List<WebElement> submitButton;

    public Optional<WebElement> getInchesField() {
        return inchesField.stream().findFirst();
    }

}
