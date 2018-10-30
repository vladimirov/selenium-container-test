import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.VncRecordingContainer;
import org.testng.annotations.*;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static org.rnorth.visibleassertions.VisibleAssertions.assertTrue;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;


public class SeleniumContainerTest {

    public static BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
            .withDesiredCapabilities(DesiredCapabilities.chrome())
//            .withRecordingMode(RECORD_ALL, new File("target"));
            .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.SKIP, null);

    public static VncRecordingContainer vnc = new VncRecordingContainer(chrome);


    @BeforeClass
    public static void setUp() {
        chrome.start();
        vnc.start();
        RemoteWebDriver driver = chrome.getWebDriver();
        WebDriverRunner.setWebDriver(driver);
    }

    @AfterClass
    public static void tearDown() {
        vnc.stop();
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

        vnc.saveRecordingToFile(new File("target/" + System.currentTimeMillis() + ".flv"));
        vnc.stop();
    }

}