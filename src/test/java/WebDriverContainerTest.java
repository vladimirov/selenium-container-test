//import org.openqa.selenium.By;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.testcontainers.containers.BrowserWebDriverContainer;
//import org.testcontainers.containers.Network;
//import org.testcontainers.containers.VncRecordingContainer;
//import org.testng.annotations.*;
//
//import java.io.File;
//
//import static org.testng.AssertJUnit.assertEquals;
//
//
//public class WebDriverContainerTest {
//
//    File recordingDir = new File("build/recording-" + System.currentTimeMillis());
//
//    public BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
//            .withDesiredCapabilities(DesiredCapabilities.chrome())
//            .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.SKIP, null);
//
//    public VncRecordingContainer vnc = new VncRecordingContainer(chrome);
//
//    @BeforeClass
//    public void setUp() {
//        chrome.start();
//        vnc.start();
//    }
//
//    @AfterClass
//    public void tearDown() {
//        chrome.stop();
//    }
//
//    @Test
//    public void searchForTestcontainersOnGoogle() {
//        RemoteWebDriver driver = chrome.getWebDriver();
//        String name = "testcontainers";
//        driver.get("http://www.google.com");
//        driver.findElement(By.name("q")).sendKeys(name);
//        driver.findElement(By.name("q")).submit();
//        assertEquals(name, driver.findElement(By.name("q")).getAttribute("value"));
//
//        vnc.saveRecordingToFile(new File(recordingDir, name + ".flv"));
//        vnc.stop();
//    }
//
//}