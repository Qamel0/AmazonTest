package Objects;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void getPage() {
        driver.get("https://www.amazon.com/");
    }
}
