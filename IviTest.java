package lesson5;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class IviTest {
    WebDriver driver;
    WebDriverWait webDriverWait;
    Actions actions;

    private final static String IVI_BASE_URL = "https://ivi.ru";

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }


    @BeforeEach
    void setupBrowser() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        actions = new Actions(driver);
        driver.get(IVI_BASE_URL);
    }

    @Test
    void likeMovieTest() {
        webDriverWait
                .until(d -> d.findElements(
                        By.xpath("//div[@data-test='collection_gallery_item']//span[@class='nbl-slimPosterBlock__titleText']")).size() > 0);
        List<WebElement> filmsList = driver.findElements(
                By.xpath("//div[@data-test='collection_gallery_item']//span[@class='nbl-slimPosterBlock__titleText']"));
        filmsList.stream().filter(f -> f.getText().contains("Помогите, я уменьшил своих друзей!")).findFirst().get().click();
        filmsList.get(0).click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='userButtons__hoard']")));
        driver.findElement(By.xpath("//div[@class='userButtons__hoard']")).click();

        driver.switchTo().frame(driver.findElement(By.xpath("div[@data-test='header_avatar']")));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("https://www.ivi.ru/profile")));
        Assertions.assertEquals(driver.findElement(By.className("https://www.ivi.ru/profile")).isDisplayed(), false);

    }



    @AfterEach
    void quitBrowser() {
        driver.quit();
    }




}
