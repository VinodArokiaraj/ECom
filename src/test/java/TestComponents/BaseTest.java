package TestComponents;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LandingPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

    public WebDriver driver;
    public LandingPage landingPage;

    public WebDriver initializeDriver () throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "//src//main//resources//GlobalData.properties"
        );
        prop.load(fis);
        String browserName = prop.getProperty("browser");

        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        else if (browserName.equalsIgnoreCase("safari")) {
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    @BeforeMethod
    public LandingPage launchApplication() throws IOException {
        driver = initializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goTo("https://rahulshettyacademy.com/client/");
        return landingPage;
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
