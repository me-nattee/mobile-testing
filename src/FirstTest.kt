
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL

open class FirstTest {

    var driver: AppiumDriver<MobileElement>? = null

    @Before
    open fun setUp() {
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability("deviceName", "AndroidTestDevice")
        capabilities.setCapability("automationName", "Appium")
        capabilities.setCapability("platformVersion", "8.0")
        capabilities.setCapability("appPackage", "org.wikipedia")
        capabilities.setCapability("appActivity", ".main.MainActivity")
        capabilities.setCapability("app", "/Users/nshakh/Desktop/Apps/org.wikipedia.apk")

        driver = AndroidDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)
    }

    @After
    fun tearDown() {
        driver?.quit() ?: throw Exception("Driver instance was unable to quit.")
    }

    @Test
    fun firstTest() {
        var search: WebElement = driver!!.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]")
        search.click()

        var enter: WebElement = waitElementById("search_src_text", "Can't find search input", 5)
        enter.sendKeys("Madonna")
    }

    fun waitElementById(id: String, error: String, time: Long): WebElement {
        var wait = WebDriverWait(driver, time)
        wait.withMessage(error + "\n")
        var by: By = By.id(id)
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        )
    }
}