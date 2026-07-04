package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Order {

    @Test
    public void Dayes() throws InterruptedException{

        WebDriver driver = new ChromeDriver();
        String password = getPassword(driver);
        driver.get("http://Rahulshettyacademy.com/locatorspractice");
        driver.findElement(By.id("inputUsername")).sendKeys("Rahul");
        driver.findElement(By.name("inputPassword")).sendKeys(password);
        driver.findElement(By.className("signInBtn")).click();
     /*   driver.findElement(By.partialLinkText("Forgot your password?")).click();
        Thread.sleep(2000);
        String text = driver.findElement(By.tagName("h2")).getText();
        System.out.println(text);
        driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys("Rahul");
        driver.findElement(By.cssSelector("input[type='text']:nth-child(3)")).sendKeys("Rahul@yopmail.com");
        driver.findElement(By.xpath("//form/input[3]")).sendKeys("9087654321");
        driver.findElement(By.cssSelector(".reset-pwd-btn")).click();
        String pwd = driver.findElement(By.cssSelector("form p")).getText();
        String[] passwords = pwd.split("'");
        String password = passwords[1];
        driver.findElement(By.cssSelector(".go-to-login-btn")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("inputUsername")).sendKeys("Rahul");
        driver.findElement(By.name("inputPassword")).sendKeys(password);
        driver.findElement(By.className("signInBtn")).click();
*/
        driver.close();
    }


    public static String getPassword(WebDriver driver) throws InterruptedException{
        driver = new ChromeDriver();
        driver.get("http://Rahulshettyacademy.com/locatorspractice");
        driver.findElement(By.partialLinkText("Forgot your password?")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".reset-pwd-btn")).click();
        String pwd = driver.findElement(By.cssSelector("form p")).getText();
        String[] passwords = pwd.split("'");
        String password = passwords[1];
        return password;
    }
}