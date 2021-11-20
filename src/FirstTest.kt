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
        clickByXpath("//*[contains(@text, 'Search Wikipedia')]", "Can't find Search Wikipedia input", 5)
        sendKeysById("search_src_text", "Java", "Can't find search input", 5)
        waitElementByXpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']",
                "Can't find Object-oriented programming language", 10)
    }

    @Test
    fun cancelSearchTest() {
        clickByXpath("//*[contains(@text, 'Search Wikipedia')]", "Can't find Search Wikipedia input", 5)
        closeSearch("search_close_btn", "Can't find close button", 5)
        hasNotCloseButton("search_close_btn", "There's search button", 5)
    }

    fun waitElementById(id: String, error: String, time: Long): WebElement {
        var wait = WebDriverWait(driver, time)
        wait.withMessage(error + "\n")
        var by: By = By.id(id)
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        )
    }

    fun waitElementById(id: String, error: String): WebElement {
        return waitElementById(id, error, 5)
    }

    fun waitElementByXpath(path: String, error: String, time: Long): WebElement {
        var wait = WebDriverWait(driver, time)
        wait.withMessage(error + "\n")
        var by: By = By.xpath(path)
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        )
    }

    fun waitElementByXpath(path: String, error: String): WebElement {
        return waitElementById(path, error, 5)
    }

    fun clickByXpath(path: String, error: String, time: Long): WebElement {
        val element: WebElement = waitElementByXpath(path, error, time)
        element.click()
        return element
    }

    fun clickById(id: String, error: String, time: Long): WebElement {
        val element: WebElement = waitElementById(id, error, time)
        element.click()
        return element
    }

    fun sendKeysByXpath(path: String, text: String, error: String, time: Long): WebElement {
        val element: WebElement = waitElementByXpath(path, error, time)
        element.sendKeys(text)
        return element
    }

    fun sendKeysById(id: String, text: String, error: String, time: Long): WebElement {
        val element: WebElement = waitElementById(id, error, time)
        element.sendKeys(text)
        return element
    }

    fun closeSearch(id: String, error: String, time: Long): WebElement {
        val element: WebElement = waitElementById(id, error, time)
        element.click()
        return element
    }

    fun hasNotCloseButton(id: String, error: String, time: Long): Boolean {
        var wait = WebDriverWait(driver, time)
        wait.withMessage(error + "\n")
        var by: By = By.id(id)
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        )
    }
}