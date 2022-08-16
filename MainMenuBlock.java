package lesson6;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainMenuBlock extends BasePage {
    public MainMenuBlock(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//li//a[@title='Women']")
    private WebElement womenButton;

    public WomenSuggestPage hoverWomenButton() {
        actions.moveToElement(womenButton)
                .build()
                .perform();
        return new WomenSuggestPage(driver);
    }
}
------------------------------------------
package lesson6;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    WebDriver driver;
    WebDriverWait webDriverWait;
    Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickSingInButton() {
    }
}
------------------------------------------
package lesson6;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BlousesPage extends BasePage {
    public BlousesPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[.='Size']/ancestor::div[@class='layered_filter']//a")
    private List<WebElement> sizesList;

    public BlousesPage selectSize(String size) {
        sizesList.stream().filter(s -> s.getText().contains(size)).findFirst().get().click();
        return this;
    }

    @FindBy(xpath = "//ul[@class='product_list grid row']/li")
    private WebElement productElement;

    @FindBy(xpath = "//span[.='Add to cart']")
    private WebElement addToCartButton;

    public SuccessAddToCartPage moveMouseToProductAndAddToCart() {
        actions.moveToElement(productElement)
                .build()
                .perform();
        addToCartButton.click();
        return new SuccessAddToCartPage(driver);
    }
}

----------------------------------------------
package lesson6;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final String emailIdLocator = "email";

    @FindBy(id = emailIdLocator)
    private WebElement emailField;

    @FindBy(id = "passwd")
    private WebElement passwordField;

    @FindBy(id = "SubmitLogin")
    private WebElement signInButton;

    public void login(String login, String password) {
        //webDriverWait.until(ExpectedConditions.visibilityOf(emailField));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(emailIdLocator)));
        emailField.sendKeys(login);
        passwordField.sendKeys(password);
        signInButton.click();
        new MainPage(driver);
    }
}

---------------------------------------
package lesson6;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {
    @FindBy(xpath = "//a[@class='login']")
    private WebElement signInButton;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void clickSignButton() {
        signInButton.click();
    }

}



-----------------------------------
package lesson6;

import org.asynchttpclient.util.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SuccessAddToCartPage extends BasePage {
    public SuccessAddToCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[@class='ajax_block_cart_total']")
    private WebElement totalSumma;

    private final String iconOkXpathLocator = "//i[@class='icon-ok']";

    @FindBy(xpath = iconOkXpathLocator)
    private WebElement iconOk;

    public SuccessAddToCartPage checkTotalSumma(String expectedSumma) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(iconOkXpathLocator)));
        Assertions.assertEquals(expectedSumma, totalSumma.getText());
        return this;
    }
}

------------------------------
package lesson6;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WomenSuggestPage extends BasePage {

    public WomenSuggestPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//ul[contains(@class,'submenu')]//a[@title='Blouses']")
    private WebElement blousesButton;

    public BlousesPage clickBlousesButton() {
        blousesButton.click();
        return new BlousesPage(driver);
    }
}

----------------------------
package lesson6;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PageObjectTest {
    WebDriver driver;

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void initDriver() {
        driver = new ChromeDriver();
        driver.get("http://automationpractice.com/index.php");
    }

    @Test
    void putBlousesToCartTest() throws InterruptedException {
        // MainPage mainPage = new MainPage(driver);
        // mainPage.clickSingInButton();
        // new LoginPage(driver).login("spartalex93@test.test", "123456");
        // new MainMenuBlock(driver).hoverWomenButton();
        // new WomenSuggestPage(driver).

        //new MainPage(driver).clickSingInButton();

        new MainPage(driver).clickSingInButton()
                .login("wewe@yu.ru", "12345")
                .mainMenuBlock.hoverWomenButton()
                .clickTShirtsButton()
                .selectSize("S")
                .moveMouseToProductAndAddToCart()
                .checkTotalSumma("$27.00");
    }

    @AfterEach
    void killBrowser() {
        driver.quit();
    }
}


----------------------------------
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>org.example</groupId>
  <artifactId>myProject</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>myProject</name>
  <url>http://maven.apache.org</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
      <version>LATEST</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/io.github.bonigarcia/webdrivermanager -->
    <dependency>
      <groupId>io.github.bonigarcia</groupId>
      <artifactId>webdrivermanager</artifactId>
      <version>5.2.3</version>
    </dependency>

    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>LATEST</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <version>RELEASE</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.9.0</version>
    <scope>test</scope>
  </dependency>
  </dependencies>
</project>

