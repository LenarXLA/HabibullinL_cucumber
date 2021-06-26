import io.cucumber.java.*;
import io.cucumber.java.ru.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import typeReg.ExpensiveCategory;
import typeReg.MainCategory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Epic("Test avito site")
public class StepDefsTest {
    MyDriver myDriver;
    WebDriver drv;

    @ParameterType(".*")
    public MainCategory mainCategory(String categ) {
        return MainCategory.valueOf(categ);
    }

    @ParameterType(".*")
    public ExpensiveCategory expensiveCategory(String exp) {
        return ExpensiveCategory.valueOf(exp);
    }

    @Before
    @BeforeTest
    public void getStart() {
        myDriver = new MyDriver();
        drv = myDriver.getDriver();
    }

    @After
    @AfterTest
    public void getQuit() {
        myDriver.quitConnection();
    }

    @Пусть("открыт ресурс Авито")
    @Test(description = "Старт приложения")
    public void openRecourse() {
        drv.get("https://www.avito.ru/");

        String curURL = drv.getCurrentUrl();
        Assert.assertEquals(curURL, "https://www.avito.ru/");
    }

    @И("в выпадающием списке категорий выбрана {mainCategory}")
    @Parameters("mainCategory")
    @Test(description = "в выпадающием списке категорий выбрана категория")
    public void selectCategory(@Optional("Оргтехника_и_расходники") MainCategory mainCategory) {
        Select selectCategory = new Select(drv.findElement(By.xpath("//select[@name='category_id']")));
        selectCategory.selectByValue(mainCategory.value);
    }

    @И("в поле поиска введено значение {word}")
    @Parameters("findStr")
    @Test(description = "в поле поиска введено значение")
    public void setValueFind(@Optional("принтер") String findStr) {
        WebElement findTool = drv.findElement(By.xpath("//input[@data-marker='search-form/suggest']"));
        findTool.sendKeys(findStr);
    }

    @Тогда("кликнуть по выпадающему списку региона")
    @Test(description = "клик по выпадающему списку региона")
    public void getRegion() {
        WebElement cityButton = drv.findElement(By.xpath("//div[@data-marker='search-form/region']"));
        cityButton.click();
    }

    // Заполним значением “Владивосток” поле город  в открывшемся окне и кликнем по первому предложенному варианту
    // Нажмем на кнопку “Показать объявления”
    @Тогда("в поле региона введено значение {word}")
    public void addValue(String city) {
        WebElement findCity = drv.findElement(By.xpath("//input[@data-marker='popup-location/region/input']"));
        findCity.sendKeys(city);
    }

    @И("нажата кнопка показать объявления")
    public void clickAdButton() {
        drv.findElement(By.xpath("//li[@data-marker='suggest(0)']")).click();
        drv.findElement(By.xpath("//button[@data-marker='popup-location/save-button']")).click();
    }

    @Тогда("открыласть страница результатов по запросу {word}")
    public void isNeedPage(String printer) {
        String str = drv.findElement(By.xpath("//h1[@data-marker='page-title/text']")).getText();
        if (str.contains(printer)) {
            System.out.println("open page contain printer");
        } else {
            System.out.println("open page NOT contain printer");
        }
    }


    @И("активирован чекбокс только с доставкой")
    public void checkCheckBox() {
        WebElement checkBox = drv.findElement(By.xpath("//div[@data-marker='delivery-filter/container']"));
        if (!checkBox.isSelected()) {
            checkBox.click();
        }
        drv.findElement(By.xpath("//button[@data-marker='search-filters/submit-button']")).click();
    }

    @И("в выпадающем списке сортировка выбрано значение {expensiveCategory}")
    public void changeFilter(ExpensiveCategory expensiveCategory) {
        Select selectFilter = new Select(drv.findElement(By.xpath("//div[@class='sort-select-3QxXG select-select-box-3LBfK select-size-s-2gvAy']/select")));
        selectFilter.selectByValue(expensiveCategory.value);
    }

    @И("в консоль выведены названия и цены {int} первых товаров")
    public void printResults(int number) {
        List<WebElement> printers = drv.findElements(By.xpath("//div[@data-marker='catalog-serp']/div[@data-marker='item']"));
        for (int i = 0; i < number; i++) {
            System.out.println(printers.get(i).findElement(By.xpath(".//h3[@itemprop='name']")).getText() +
                    " : " + printers.get(i).findElement(By.xpath(".//meta[@itemprop='price']")).getAttribute("content") + " руб.");
        }
    }

    @AfterMethod
    public void printResult() throws IOException {
        File scrFile = ((TakesScreenshot) drv).getScreenshotAs(OutputType.FILE);

        Path content = Paths.get(scrFile.getPath());
        try (InputStream is = Files.newInputStream(content)) {
            Allure.addAttachment("Screen", is);
        }
    }

}

