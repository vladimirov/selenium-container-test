import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.VncRecordingContainer;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;

import static com.codeborne.selenide.Selenide.*;
import static org.rnorth.visibleassertions.VisibleAssertions.assertTrue;


public class SeleniumContainerTest {

    private static BrowserWebDriverContainer chrome = new BrowserWebDriverContainer<>()
            .withDesiredCapabilities(DesiredCapabilities.chrome())
            .withNetwork(Network.SHARED)
            .withNetworkAliases("vnchost")
            .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.SKIP, null);

    private static VncRecordingContainer vnc = new VncRecordingContainer(chrome);

    @BeforeMethod
    public static void setUp() {
        chrome.start();
        vnc.start();
        RemoteWebDriver driver = chrome.getWebDriver();
        WebDriverRunner.setWebDriver(driver);
    }

    @AfterMethod
    public static void tearDown(ITestResult result, Method m) {
        try {
            if (ITestResult.SUCCESS == result.getStatus()) {
                vnc.saveRecordingToFile(new File("target/PASSED-" + m.getName() + "-" + System.currentTimeMillis() + ".flv"));
            }
            if (ITestResult.FAILURE == result.getStatus()) {
                vnc.saveRecordingToFile(new File("target/FAILED-" + m.getName() + "-" + System.currentTimeMillis() + ".flv"));
            }
        } catch (Exception e) {
            System.out.println("Exception while taking video " + e.getMessage());
        }
        vnc.stop();
        chrome.stop();
    }

    @Test
    public void simpleTest() {
        open("https://wikipedia.org");
        $("input#searchInput").val("Eminem").submit();
        boolean expectedTextFound = $$("p")
                .stream()
                .anyMatch(element -> element.getText().contains("rapper"));
        assertTrue("The word 'rapper' is found on a page", expectedTextFound);
    }

}