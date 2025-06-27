package Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class BookPage extends BasePage {
    private final By bookTitle = By.id("productTitle");
    private final By authors = By.xpath("//span[@class='author notFaded']//a");
    private final By price = By.xpath("//div[@id='tmm-grid-swatch-PAPERBACK']//span[@class='slot-price']" +
            "| //div[@id='tmm-grid-swatch-OTHER']//span[@class='slot-price']");
    private final By bestSeller =
            By.xpath("//ul[@class='a-unordered-list a-nostyle a-vertical a-spacing-none detail-bullet-list']" +
                    "//ul[@class='a-unordered-list a-nostyle a-vertical zg_hrsr']//span[@class='a-list-item']");

    public BookPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getAllBookData(String url) {
        driver.get(url);

        List<String> itemData = new ArrayList<>();

        itemData.add("Book name: " + driver.findElement(bookTitle).getText());
        List<WebElement> authorsList = driver.findElements(authors);

        int authorCounter = 1;
        for (WebElement author : authorsList) {
            itemData.add("Author " + authorCounter++ + ": " + author.getText());
        }

        itemData.add("Book price: " + driver.findElement(price).getText());
        List<Integer> bestSellsNums = new ArrayList<>();

        List<WebElement> bestSells = driver.findElements(bestSeller);

        for (WebElement element : bestSells) {
            bestSellsNums.add(Integer.parseInt(element.getText().replaceAll("[^0-9]", "")));
        }

        itemData.add("Bestseller: " + (bestSellsNums.stream().anyMatch(n -> n <= 100) ? "Yes" : "No"));

        return itemData;
    }
}
