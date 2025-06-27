package Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

   public List<String> getPageProductsLinks() {
        List<WebElement> items = driver.findElements(By.cssSelector("div[data-component-type='s-search-result']"));
        List<String> links = new ArrayList<>();

        for (WebElement item : items) {
            WebElement linkElement = item.findElement(
                    By.xpath(".//a[@class='a-link-normal s-line-clamp-2 s-link-style a-text-normal']"));

            links.add(linkElement.getAttribute("href"));
        }

        return links;
   }
}
