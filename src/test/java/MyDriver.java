import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyDriver {
    private WebDriver driver;

    public WebDriver getMyDrive() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
