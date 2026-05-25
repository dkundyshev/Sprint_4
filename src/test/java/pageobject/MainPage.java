package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver driver;
    private final String baseUrl = "https://qa-scooter.praktikum-services.ru/";

    private By orderButtonTop = By.xpath("//div[contains(@class, 'Header_Nav')]/button[text()='Заказать']");
    private By orderButtonBottom = By.xpath("//div[contains(@class, 'Home_FinishButton')]/button[text()='Заказать']");

    private By faqHeading = By.xpath("//div[text()='Вопросы о важном']");

    private String questionIdTemplate = "accordion__heading-%d";
    private String answerPanelIdTemplate = "accordion__panel-%d";

    private String answerTextTemplate = "//div[@id='accordion__panel-%d']/p";

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(baseUrl);
    }


    public void clickOrderButton(OrderButton button) {
        if (button == OrderButton.TOP) {
            driver.findElement(orderButtonTop).click();
        } else {
            driver.findElement(orderButtonBottom).click();
        }
    }

    public void scrollToFAQ() {
        driver.findElement(faqHeading).isDisplayed(); // скролл произойдёт автоматически
    }

    public String getAnswerText(int index) {
        By questionArrow = By.id(String.format(questionIdTemplate, index));
        driver.findElement(questionArrow).click();

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id(String.format(answerPanelIdTemplate, index))));
        return driver.findElement(By.xpath(String.format(answerTextTemplate, index))).getText();
    }

    public enum OrderButton {
        TOP,
        BOTTOM
    }

    private By cookieConsentButton = By.xpath("//div[contains(@class, 'App_CookieConsent')]//button");

    public void closeCookieConsent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(cookieConsentButton));
            driver.findElement(cookieConsentButton).click();
        } catch (Exception e) {
        }
    }
}