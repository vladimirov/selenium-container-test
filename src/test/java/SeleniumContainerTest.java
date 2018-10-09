import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static org.rnorth.visibleassertions.VisibleAssertions.assertTrue;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;


public class SeleniumContainerTest {

    public static BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
            .withDesiredCapabilities(DesiredCapabilities.chrome())
            .withRecordingMode(RECORD_ALL, new File("target"));

    @BeforeClass
    public static void setUp() {
        chrome.start();
        RemoteWebDriver driver = chrome.getWebDriver();
        WebDriverRunner.setWebDriver(driver);
    }

    @AfterClass
    public static void tearDown() {
        chrome.stop();
    }

    @Test
    public void test() {
        Selenide.open("https://wikipedia.org");
        $("input#searchInput").val("Eminem").submit();
        boolean expectedTextFound = Selenide.$$("p")
                .stream()
                .anyMatch(element -> element.getText().contains("rapper"));
        assertTrue("The word 'rapper' is found on a page", expectedTextFound);

    }

//    @Test
//    public void simplePlainSeleniumTest() {
//        RemoteWebDriver driver = chrome.getWebDriver();
//        driver.get("https://wikipedia.org");
//        WebElement searchInput = driver.findElementByName("search");
//        searchInput.sendKeys("Eminem");
//        searchInput.submit();
//        boolean expectedTextFound = driver.findElementsByCssSelector("p")
//                .stream()
//                .anyMatch(element -> element.getText().contains("rapper"));
//        assertTrue("The word 'rapper' is found on a page", expectedTextFound);
//    }

}