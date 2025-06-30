import Objects.BookPage;
import Objects.HomePage;
import Objects.SearchPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmazonTests {
    HomePage homePage;
    SearchPage searchPage;
    BookPage bookPage;

    Map<String, List<String>> booksData;

    @BeforeClass
    @Description("Web driver configuration")
    public void setUpDriver(){
        Allure.step("Driver created");
        WebDriver driver = new ChromeDriver();

        homePage = new HomePage(driver);
        searchPage = new SearchPage(driver);
        bookPage = new BookPage(driver);
    }

    @AfterClass
    public void quitDriver() {
        bookPage.quit();
    }

    @Test
    @Description("Checking whether the driver has been configured correctly")
    public void testIsDriverOk() {
        Assert.assertNotNull(homePage);
        Assert.assertNotNull(searchPage);
        Assert.assertNotNull(bookPage);
    }

    @Test(dependsOnMethods = "testIsDriverOk")
    @Description("Are we on the right page")
    public void testIsPageAvailable() {
        Allure.step("Get page");
        homePage.getPage();

        Allure.step("Checking if you are on the right page");
        /*Sometimes redirects to another version of the page during auto tests.
        Reloads the page to work with the correct version*/
        if (!homePage.isPageCorrect()) {
            if(homePage.getTitle().equals("Amazon.com")) {
                homePage.notARobot();
            }
            else {
                homePage.refreshPage();
            }
        }

        Assert.assertNotNull(homePage.getPageUrl(), "Page is not have url");
        Assert.assertTrue(homePage.isUrlContain("www.amazon.com"), "Wrong url");
        Assert.assertEquals(homePage.getTitle(), "Amazon.com. Spend less. Smile more.",
                "Wrong page title");
    }

    @Test(dependsOnMethods = "testIsPageAvailable")
    @Description("Correctness of category filter operation")
    public void testIsFilterChange() {
        Assert.assertTrue(homePage.changeFilter("Books"), "Option not found");
    }

    @Test(dependsOnMethods = "testIsFilterChange")
    @Description("Search capability")
    public void testIsSearchWork() {
        homePage.useSearch("Java");

        Assert.assertTrue(searchPage.getPageUrl().contains("Java"));
    }

    @Test(dependsOnMethods = "testIsSearchWork")
    @Description("Pulling book data from a page")
    public void testGetPageItemsData() {
        Allure.step("Pulling links to books");
        List<String> itemsLinks = searchPage.getPageProductsLinks();
        booksData = new HashMap<>();

        Allure.step("Pulling book data from previously pulled links");
        for (String link : itemsLinks) {
            List<String> book = bookPage.getAllBookData(link);
            booksData.put(book.getFirst().substring(11), book);

            //Output of each added book and its data
            /*for (Map.Entry<String, List<String>> entry : booksData.entrySet()) {
                System.out.println("\nKey: " + entry.getKey() + "\n");
                for (String data : entry.getValue()) {
                    System.out.println(data);
                }
            }*/

        }

        Assert.assertFalse(booksData.isEmpty());
    }

    @Test(dependsOnMethods = "testGetPageItemsData")
    @Description("Whether the book we need is in the list")
    public void testHasCerBook() {
        Assert.assertTrue(booksData.containsKey("Head First Java: A Brain-Friendly Guide"));
        Assert.assertEquals(booksData.get("Head First Java: A Brain-Friendly Guide"),
                (bookPage.getAllBookData("https://www.amazon.com/Head-First-Java-Brain-Friendly-Guide/dp/1491910771/")));
    }
}
