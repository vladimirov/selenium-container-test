import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testng.annotations.Test;

import java.io.File;

import static org.rnorth.visibleassertions.VisibleAssertions.assertTrue;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

/**
 * Simple example of plain Selenium usage.
 */
public class SeleniumContainerTest {

//    @Rule
//    public BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
//            .withDesiredCapabilities(DesiredCapabilities.chrome())
//            .withRecordingMode(RECORD_ALL, new File("target"));

    @Test
    public void simplePlainSeleniumTest() {
        BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
            .withDesiredCapabilities(DesiredCapabilities.chrome())
            .withRecordingMode(RECORD_ALL, new File("target"));
        chrome.start();

        RemoteWebDriver driver = chrome.getWebDriver();
        driver.get("https://wikipedia.org");
        WebElement searchInput = driver.findElementByName("search");
        searchInput.sendKeys("Eminem");
        searchInput.submit();
        boolean expectedTextFound = driver.findElementsByCssSelector("p")
                .stream()
                .anyMatch(element -> element.getText().contains("rapper"));
        assertTrue("The word 'rapper' is found on a page", expectedTextFound);
    }
}
