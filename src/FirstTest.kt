import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.Assert
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
        click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        sendKeys(By.id("search_src_text"), "Java", "Can't find search input", 5)
        waitElement(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find Object-oriented programming language", 10)
    }

    @Test
    fun cancelSearchTest() {
        click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        sendKeys(By.id("search_src_text"), "Java", "Can't find search input", 5)
        clear(By.id("search_src_text"), "Can't find element for clearing", 5)
        click(By.id("search_close_btn"), "Can't find close button", 5)
        hasNotCloseButton(By.id("search_close_btn"), "There's search button", 5)
    }

    @Test
    fun compareTitleTest() {
        click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        sendKeys(By.id("search_src_text"), "Java", "Can't find search input", 5)
        click(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find the element", 5)
        val title = waitElement(By.id("view_page_title_text"), "No title", 10)
                .getAttribute("text")

        Assert.assertEquals("We see unexpected title", "Java (programming language)", title)

    }

    fun waitElement(by: By, error: String, time: Long): WebElement {
        var wait = WebDriverWait(driver, time)
        wait.withMessage(error + "\n")
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        )
    }

    fun click(by: By, error: String, time: Long): WebElement {
        val element: WebElement = waitElement(by, error, time)
        element.click()
        return element
    }


    fun sendKeys(by: By, text: String, error: String, time: Long): WebElement {
        val element: WebElement = waitElement(by, error, time)
        element.sendKeys(text)
        return element
    }


    fun hasNotCloseButton(by: By, error: String, time: Long): Boolean {
        var wait = WebDriverWait(driver, time)
        wait.withMessage(error + "\n")
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        )
    }

    fun clear(by: By, error: String, time: Long): WebElement {
        val element: WebElement = waitElement(by, error, time)
        element.clear()
        return element
    }
}