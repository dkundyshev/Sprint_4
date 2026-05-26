package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageobject.MainPage;
import pageobject.OrderFormPage;
import pageobject.RentFormPage;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;
    private final MainPage.OrderButton button;
    private final String name, surname, address, metro, phone;
    private final String date, rentalPeriod, color, comment;

    public OrderTest(MainPage.OrderButton button, String name, String surname, String address, String metro, String phone,
                     String date, String rentalPeriod, String color, String comment) {
        this.button = button;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "Кнопка {0}, клиент {1} {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        MainPage.OrderButton.TOP,
                        "Александр", "Пушкин", "Москва, Арбат, 10", "Арбатская", "+79161234567",
                        "25.06.2026", "двое суток", "чёрный жемчуг", "Оставьте у двери"
                },
                {
                        MainPage.OrderButton.BOTTOM,
                        "Мария", "Кюри", "Санкт-Петербург, Невский, 5", "Невский проспект", "+79219876543",
                        "26.06.2026", "трое суток", "серая безысходность", "Позвонить за час"
                }
        });
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void orderScooterPositiveFlow() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.closeCookieConsent();
        mainPage.clickOrderButton(button);

        OrderFormPage orderForm = new OrderFormPage(driver);
        orderForm.fillFirstForm(name, surname, address, metro, phone);
        orderForm.clickNext();

        RentFormPage rentForm = new RentFormPage(driver);
        rentForm.fillRentForm(date, rentalPeriod, color, comment);
        rentForm.clickOrder();
        rentForm.confirmOrder();

        boolean isSuccess = rentForm.isOrderSuccessMessageDisplayed();
        assertTrue("Сообщение об успешном заказе не появилось", isSuccess);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}