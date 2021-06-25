import io.cucumber.java.*;
import io.cucumber.java.ru.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import typeReg.ExpensiveCategory;
import typeReg.MainCategory;

import java.util.List;

public class StepDefs {
    MyDriver myDriver;
    WebDriver drv;

    @ParameterType(".*")
    public MainCategory mainCategory(String categ) {
        return MainCategory.valueOf(categ);
    }

    @ParameterType(".*")
    public ExpensiveCategory expensiveCategory(String valueCat) {
        return ExpensiveCategory.valueOf(valueCat);
    }

    @Before
    public void getStart() {
        myDriver = new MyDriver();
        drv = myDriver.getDriver();
    }

    @After
    public void getQuit() {
        myDriver.quitConnection();
    }

    @Пусть("открыт ресурс Авито")
    public void openRecourse() {
        drv.get("https://www.avito.ru/");
    }

    @И("в выпадающием списке категорий выбрана {mainCategory}")
    public void selectCategory(MainCategory mainCategory) {
        Select selectCategory = new Select(drv.findElement(By.xpath("//select[@name='category_id']")));
        selectCategory.selectByValue(mainCategory.value);
    }

    @И("в поле поиска введено значение {word}")
    public void setValueFind(String findStr) {
        WebElement findTool = drv.findElement(By.xpath("//input[@data-marker='search-form/suggest']"));
        findTool.sendKeys(findStr);
    }


    @Тогда("кликнуть по выпадающему списку региона")
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
    public void changeFilter(ExpensiveCategory exp) {
        Select selectFilter = new Select(drv.findElement(By.xpath("//div[@class='sort-select-3QxXG select-select-box-3LBfK select-size-s-2gvAy']/select")));
        selectFilter.selectByValue(exp.value);
    }

    @И("в консоль выведены названия и цены {int} первых товаров")
    public void printResults(int number) {
        List<WebElement> printers = drv.findElements(By.xpath("//div[@data-marker='catalog-serp']/div[@data-marker='item']"));
        for (int i = 0; i < number; i++) {
            System.out.println(printers.get(i).findElement(By.xpath(".//h3[@itemprop='name']")).getText() +
                    " : " + printers.get(i).findElement(By.xpath(".//meta[@itemprop='price']")).getAttribute("content") + " руб.");
        }
    }
}

