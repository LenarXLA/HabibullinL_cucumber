import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.avito.ru/");

        // Выберем в выпадающем списке “категория”  значение оргтехника и расходники
        Select selectCategory = new Select(driver.findElement(By.xpath("//select[@name='category_id']")));
        selectCategory.selectByVisibleText("Оргтехника и расходники");

        // В поле поиск по объявлению введем значение “Принтер”
        WebElement findTool = driver.findElement(By.xpath("//input[@data-marker='search-form/suggest']"));
        findTool.sendKeys("Принтер");

        // Нажмем на поле город
        WebElement cityButton = driver.findElement(By.xpath("//div[@data-marker='search-form/region']"));
        cityButton.click();

        // Заполним значением “Владивосток” поле город  в открывшемся окне и кликнем по первому предложенному варианту
        // Нажмем на кнопку “Показать объявления”
        WebElement findCity = driver.findElement(By.xpath("//input[@data-marker='popup-location/region/input']"));

        String city = "Владивосток";

        findCity.sendKeys(city);

        WebElement dynamicElement = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@data-marker='suggest(0)']")));

        System.out.println(dynamicElement.getText());

        dynamicElement.click();

        driver.findElement(By.xpath("//button[@data-marker='popup-location/save-button']")).click();

        // Проверим, активирован ли чекбокс, и если не активирован – активируем и нажмем кнопку “Показать объявления”
        WebElement checkBox = driver.findElement(By.xpath("//input[@data-marker='delivery-filter/input']"));
        if (!checkBox.isSelected()) {
            driver.findElement(By.xpath("//span[@data-marker='delivery-filter/text']")).click();
        }

        driver.findElement(By.xpath("//button[@data-marker='search-filters/submit-button']")).click();

        // В выпадающем списке фильтрации выберем фильтрацию по убыванию цены.
        Select selectFilter = new Select(driver.findElement(By.xpath("//div[@class='sort-select-3QxXG select-select-box-3LBfK select-size-s-2gvAy']/select")));
        selectFilter.selectByVisibleText("Дороже");

        // Выведем в консоль название и стоимость 3х самых дорогих принтеров
        List<WebElement> printers = driver.findElements(By.xpath("//div[@data-marker='catalog-serp']/div[@data-marker='item']"));
        for (int i = 0; i < 3; i++) {
            System.out.println(printers.get(i).findElement(By.xpath(".//h3[@itemprop='name']")).getText() +
                    " : " + printers.get(i).findElement(By.xpath(".//meta[@itemprop='price']")).getAttribute("content") + " руб.");
        }


        // Делаю принудительное ожидание чтобы успеть увидеть работу программы
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}

