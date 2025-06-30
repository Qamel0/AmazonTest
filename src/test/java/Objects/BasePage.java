package Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public abstract class BasePage {
    protected WebDriver driver;

    protected final By categorySelector = By.id("searchDropdownBox");
    protected final By searchBar = By.id("twotabsearchtextbox");
    protected final By languageOption = By.id("icp-nav-flyout");
    protected final By notARobotButton = By.className("a-button-text");

    public BasePage(WebDriver webDriver) {
        driver = webDriver;
    }

    public boolean isPageCorrect() {
        return !driver.findElements(languageOption).isEmpty();
    }

    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public boolean isUrlContain(String text) {
        return getPageUrl().contains(text);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public boolean changeFilter(String filterName) {
        WebElement selector = driver.findElement(categorySelector);
        selector.click();

        Select select = new Select(selector);

        for (WebElement element : select.getOptions()) {
            if(element.getText().equals(filterName)) {
                select.selectByVisibleText(filterName);
                return true;
            }
        }

        return false;
    }

    public void useSearch(String request) {
        WebElement search = driver.findElement(searchBar);

        search.sendKeys(request);

        search.submit();
    }

    public void quit() {
        driver.quit();
    }

    public void notARobot() {
        driver.findElement(notARobotButton).click();
    }
}
