import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class MyDriver {
    private WebDriver driver;

    public WebDriver getMyDrive() {
        System.setProperty("webdriver.chrome.driver", ".\\src\\test\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver getDriver() {
        if (driver != null){
            return driver;
        }
        return getMyDrive();
    }

    public void quitConnection() {
        driver.quit();
    }
}
