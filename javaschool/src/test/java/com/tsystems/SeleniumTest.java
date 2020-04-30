package com.tsystems;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumTest {

    //user details
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;

    //first cargo details
    private String first_cargo_name;
    private double first_cargo_weight;
    private String first_cargo_loadingAddress;
    private String first_cargo_unloadingAddress;

    //second cargo details
    private String second_cargo_name;
    private double second_cargo_weight;
    private String second_cargo_loadingAddress;
    private String second_cargo_unloadingAddress;

    private FirefoxDriver webDriver;

    @Before
    public void initData() {
        System.setProperty(
                "webdriver.gecko.driver", "C:\\Program Files (x86)\\Mozilla Firefox\\geckodriver.exe");
        webDriver = new FirefoxDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //user details
        username = "testSeleniumUser";
        password = "password";
        firstName = "Selenium";
        lastName = "User";
        phoneNumber = "+79999999999";
        email = "testSelenium@mail.ru";
        address = "Moscow";

        //first cargo
        first_cargo_name = "first_cargo_test";
        first_cargo_weight = 14;
        first_cargo_loadingAddress = "Moscow";
        first_cargo_unloadingAddress = "Saint Petersburg";

        //second cargo
        second_cargo_name = "second_cargo_test";
        second_cargo_weight = 88;
        second_cargo_loadingAddress = "Saint Petersburg";
        second_cargo_unloadingAddress = "Vologda";
    }

    @Test
    public void testApp() throws InterruptedException {
        webDriver.get("http://localhost:8081/registration");
        WebDriverWait wait = new WebDriverWait(webDriver,30);

        //sign up
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        webDriver.findElement(By.id("username")).sendKeys(username);
        webDriver.findElement(By.id("password")).sendKeys(password);
        webDriver.findElement(By.id("passwordConfirm")).sendKeys(password);
        webDriver.findElement(By.id("firstName")).sendKeys(firstName);
        webDriver.findElement(By.id("lastName")).sendKeys(lastName);
        webDriver.findElement(By.id("email")).sendKeys(email);
        webDriver.findElement(By.id("phoneNumber")).sendKeys(phoneNumber);
        webDriver.findElement(By.id("address")).sendKeys(address);
        Thread.sleep(3000);
        webDriver.findElement(By.id("address")).sendKeys(Keys.DOWN, Keys.ENTER);
        Thread.sleep(2000);
        webDriver.findElement(By.id("form_sign_up")).click();

        //login as admin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
        webDriver.findElement(By.id("logout")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
        webDriver.findElement(By.id("login")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        webDriver.findElement(By.id("username")).sendKeys("admin");
        webDriver.findElement(By.id("password")).sendKeys("admin");
        Thread.sleep(2000);
        webDriver.findElement(By.id("form_login")).click();


        //find selenium user and appoint as a manager
        {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cssTable")));
            WebElement table = webDriver.findElement(By.id("cssTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            rows.stream().skip(1).forEach(row -> {
                List<WebElement> cols = row.findElements(By.tagName("td"));
                if (cols.get(0).getText().equals(firstName)) {
                    Select select = new Select(row.findElement(By.className("form-control")));
                    select.selectByVisibleText("Manager");
                }
            });
            webDriver.findElement(By.id("logout")).click();
        }

        //login as selenium user
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
        webDriver.findElement(By.id("login")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        webDriver.findElement(By.id("username")).sendKeys(username);
        webDriver.findElement(By.id("password")).sendKeys(password);
        Thread.sleep(2000);
        webDriver.findElement(By.id("form_login")).click();

        //create new order
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("not_taken_orders")));
        webDriver.findElement(By.id("not_taken_orders")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add_new_order")));
        webDriver.findElement(By.id("add_new_order")).click();
        Thread.sleep(4000);

        //add cargo to order
        {
            WebElement table = webDriver.findElement(By.id("cssTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            rows.get(2).findElement(By.id("add_cargo")).click();
        }

        //create first cargo
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loadingAddress")));
        webDriver.findElement(By.id("name")).sendKeys(first_cargo_name);
        webDriver.findElement(By.id("weight")).sendKeys(Double.toString(first_cargo_weight));
        webDriver.findElement(By.id("loadingAddress")).sendKeys(first_cargo_loadingAddress);
        Thread.sleep(2000);
        webDriver.findElement(By.id("loadingAddress")).sendKeys(Keys.DOWN, Keys.ENTER);
        Thread.sleep(2000);
        webDriver.findElement(By.id("unloadingAddress")).sendKeys(first_cargo_unloadingAddress);
        Thread.sleep(2000);
        webDriver.findElement(By.id("unloadingAddress")).sendKeys(Keys.DOWN, Keys.ENTER);
        Thread.sleep(2000);
        webDriver.findElement(By.id("form_add_cargo")).click();

        //add cargo to order
        {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cssTable")));
            WebElement table = webDriver.findElement(By.id("cssTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            rows.get(2).findElement(By.id("add_cargo")).click();
        }

        //create second cargo
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loadingAddress")));
        webDriver.findElement(By.id("name")).sendKeys(second_cargo_name);
        webDriver.findElement(By.id("weight")).sendKeys(Double.toString(second_cargo_weight));
        webDriver.findElement(By.id("loadingAddress")).sendKeys(second_cargo_loadingAddress);
        Thread.sleep(2000);
        webDriver.findElement(By.id("loadingAddress")).sendKeys(Keys.DOWN, Keys.ENTER);
        Thread.sleep(2000);
        webDriver.findElement(By.id("unloadingAddress")).sendKeys(second_cargo_unloadingAddress);
        Thread.sleep(2000);
        webDriver.findElement(By.id("unloadingAddress")).sendKeys(Keys.DOWN, Keys.ENTER);
        Thread.sleep(2000);
        webDriver.findElement(By.id("form_add_cargo")).click();

        //delete order
        {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cssTable")));
            WebElement table = webDriver.findElement(By.id("cssTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            rows.get(2).findElement(By.id("delete_order")).click();
        }
        Thread.sleep(2000);
        webDriver.findElement(By.id("logout")).click();

        //login as admin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
        webDriver.findElement(By.id("login")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        webDriver.findElement(By.id("username")).sendKeys("admin");
        webDriver.findElement(By.id("password")).sendKeys("admin");
        Thread.sleep(2000);
        webDriver.findElement(By.id("form_login")).click();

        //delete selenium user
        {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cssTable")));
            WebElement table = webDriver.findElement(By.id("cssTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            rows.stream().skip(1).forEach(row -> {
                List<WebElement> cols = row.findElements(By.tagName("td"));
                if (cols.get(0).getText().equals(firstName)) {
                    row.findElement(By.id("delete_user")).click();
                }
            });
            Thread.sleep(2000);
            webDriver.findElement(By.id("logout")).click();
        }
    }

    @After
    public void tearDown() throws Exception {
        webDriver.quit();
    }
}
