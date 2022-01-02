import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.emulation.Emulation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.Optional;

public class GeoLocationTests {

    protected static ChromeDriver driver;
    WebDriverWait webDriverWait;

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void geoLocationTest() {
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        //Pass latitude,longitude and accuracy
        devTools.send(Emulation.setGeolocationOverride(Optional.of(25.199514), Optional.of(55.277397), Optional.of(1)));
        driver.navigate().to("https://where-am-i.org/what-city-am-i-in.php");
        driver.manage().window().maximize();
        String address = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("address"))).getText();
        Assert.assertTrue(address.contains("Dubai"));
    }

    @AfterTest
    public void tearDown() {
        if(driver!=null) {
            driver.quit();
        }
    }

}
