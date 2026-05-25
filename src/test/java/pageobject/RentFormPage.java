package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RentFormPage {
    private WebDriver driver;

    private By datePicker = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rentalPeriodDropdown = By.className("Dropdown-placeholder"); // или By.xpath("//span[text()='* Срок аренды']")
    private By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private By orderButton = By.xpath("//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");
    private By confirmButton = By.xpath("//button[text()='Да']"); // в модалке подтверждения
    private By successMessage = By.xpath("//div[contains(@class, 'Order_ModalHeader') and text()='Заказ оформлен']");

    public RentFormPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillRentForm(String date, String rentalPeriod, String color, String comment) {

        driver.findElement(datePicker).click();
        driver.findElement(datePicker).clear();
        driver.findElement(datePicker).sendKeys(date);
        driver.findElement(datePicker).sendKeys(org.openqa.selenium.Keys.ENTER);


        driver.findElement(rentalPeriodDropdown).click();
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='Dropdown-menu']/div[text()='" + rentalPeriod + "']")));
        driver.findElement(By.xpath("//div[@class='Dropdown-menu']/div[text()='" + rentalPeriod + "']")).click();


        driver.findElement(By.xpath("//label[text()='" + color + "']")).click();


        driver.findElement(commentField).sendKeys(comment);
    }

    public void clickOrder() {
        driver.findElement(orderButton).click();
    }

    public void confirmOrder() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.elementToBeClickable(confirmButton));
        driver.findElement(confirmButton).click();
    }

    public boolean isOrderSuccessMessageDisplayed() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        return driver.findElement(successMessage).isDisplayed();
    }
}