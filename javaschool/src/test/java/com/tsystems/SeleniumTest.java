package com.tsystems;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

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

        //sign up
        Thread.sleep(2000);
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
        Thread.sleep(4000);

        //login as admin
        webDriver.findElement(By.id("logout")).click();
        Thread.sleep(3000);
        webDriver.findElement(By.id("login")).click();
        Thread.sleep(3000);
        webDriver.findElement(By.id("username")).sendKeys("admin");
        webDriver.findElement(By.id("password")).sendKeys("admin");
        Thread.sleep(2000);
        webDriver.findElement(By.id("form_login")).click();
        Thread.sleep(4000);

        //find selenium user and appoint as a manager
        {
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
            Thread.sleep(3000);
        }

        //login as selenium user
        webDriver.findElement(By.id("login")).click();
        Thread.sleep(3000);
        webDriver.findElement(By.id("username")).sendKeys(username);
        webDriver.findElement(By.id("password")).sendKeys(password);
        Thread.sleep(2000);
        webDriver.findElement(By.id("form_login")).click();
        Thread.sleep(4000);

        //create new order
        webDriver.findElement(By.id("not_taken_orders")).click();
        Thread.sleep(3000);
        webDriver.findElement(By.id("add_new_order")).click();
        Thread.sleep(2000);

        //add cargo to order
        {
            WebElement table = webDriver.findElement(By.id("cssTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            rows.get(2).findElement(By.id("add_cargo")).click();
        }
        Thread.sleep(3000);

        //create first cargo
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
        Thread.sleep(4000);

        //add cargo to order
        {
            WebElement table = webDriver.findElement(By.id("cssTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            rows.get(2).findElement(By.id("add_cargo")).click();
        }
        Thread.sleep(3000);

        //create second cargo
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
        Thread.sleep(4000);

        //delete order
        {
            WebElement table = webDriver.findElement(By.id("cssTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            rows.get(2).findElement(By.id("delete_order")).click();
        }
        Thread.sleep(3000);
        webDriver.findElement(By.id("logout")).click();
        Thread.sleep(3000);

        //login as admin
        webDriver.findElement(By.id("login")).click();
        Thread.sleep(3000);
        webDriver.findElement(By.id("username")).sendKeys("admin");
        webDriver.findElement(By.id("password")).sendKeys("admin");
        Thread.sleep(2000);
        webDriver.findElement(By.id("form_login")).click();
        Thread.sleep(4000);

        //delete selenium user
        {
            WebElement table = webDriver.findElement(By.id("cssTable"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            rows.stream().skip(1).forEach(row -> {
                List<WebElement> cols = row.findElements(By.tagName("td"));
                if (cols.get(0).getText().equals(firstName)) {
                    row.findElement(By.id("delete_user")).click();
                }
            });
            webDriver.findElement(By.id("logout")).click();
        }
    }
}
