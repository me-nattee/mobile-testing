import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.TouchAction
import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
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
        hasNotElement(By.id("search_close_btn"), "There's search button", 5)
    }

    @Test
    fun compareTitleTest() {
        click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        sendKeys(By.id("search_src_text"), "Java", "Can't find search input", 5)
        click(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find the element", 5)
        hasTitleOfResult(By.id("view_page_title_text"), "Java (programming language)", "No expected title")

    }

    @Test
    fun swipeTest() {
        click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        sendKeys(By.id("search_src_text"), "Appium", "Can't find search input", 5)
        click(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Can't find the element", 5)
        hasTitleOfResult(By.id("view_page_title_text"), "Appium", "No expected title")
        swipeToElement(By.xpath("//*[@text='View page in browser']"), "Cannot find the end of the article", 10)
    }

    @Test
    fun searchHasPlaceholder() {
        hasText(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[@text='Search Wikipedia']"),
                "Search Wikipedia", "No expected placeholder")
    }

    @Test
    fun searchAndClearResults() {
        click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        sendKeys(By.id("search_src_text"), "Java", "Can't find search input", 5)
        hasResults(By.id("page_list_item_container"), "Can't find any results of search")
        clear(By.id("search_src_text"), "Can't find element for clearing", 5)
        hasNotElement(By.id("page_list_item_container"), "There are results", 5)
    }

    @Test
    fun searchIsRelevant() {
        click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        sendKeys(By.id("search_src_text"), "Java", "Can't find search input", 5)
        resultsHasTitle(By.id("org.wikipedia:id/page_list_item_title"), "Java")

    }

    @Test
    fun saveArticleTiMyList() {
        val request = "Java"
        val article = "Java (programming language)"

        click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        sendKeys(By.id("search_src_text"), request, "Can't find search input", 5)
        click(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find the element", 5)
        hasTitleOfResult(By.id("view_page_title_text"), article, "No expected title")
        click(By.xpath("//android.widget.ImageView[@content-desc='More options']"), "Cannot find button to open More Options", 5)
        waitElement(By.xpath("//*[@text='Add to reading list']"), "Cannot find option in More Options", 5)
        click(By.xpath("//*[@text='Add to reading list']"), "Cannot find option in More Options", 5)
        click(By.id("org.wikipedia:id/onboarding_button"), "Cannot find 'Got it'", 5)
        clear(By.id("org.wikipedia:id/text_input"), "Cannot clear a field", 5)
        sendKeys(By.id("org.wikipedia:id/text_input"), article, "Cannot put text", 5)
        click(By.xpath("//*[@text='OK']"), "Cannot find OK button", 5)
        click(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"), "Cannot find cross button", 5)
        click(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"), "Cannot find button My lists", 5)
        click(By.xpath("//*[@text='$article']"), "Cannot find created list", 5)
        swipeLeft(By.xpath("//*[@text='$article']"), "Cannot swipe")
        hasNotElement(By.xpath("//*[@text='$article']"), "Element exist", 5)

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


    fun hasNotElement(by: By, error: String, time: Long): Boolean {
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

    fun hasText(by: By, text: String, error: String) {
        val element: WebElement = waitElement(by, error, 10)
        val actualText = element.getAttribute("text")

        Assert.assertEquals("We see unexpected text", text, actualText)
    }

    fun hasTitleOfResult(by: By, title: String, error: String) {
        val result = waitElement(by, error, 10)
                .getAttribute("text")

        Assert.assertEquals("We see unexpected title", title, result)
    }

    private fun hasResults(by: By, error: String) {
        waitElement(by, error, 5).isDisplayed
    }

    private fun resultsHasTitle(by: By, title: String) {
        val results: List<WebElement> = driver!!.findElements(by)
        for (result in results) {
            Assert.assertTrue("Results don't contain a title", result.getAttribute("text").contains(title))
        }
    }

    private fun swipeUp(timeOfSwipe: Int) {
        val action = TouchAction(driver)
        val size: Dimension = driver!!.manage().window().size
        val x: Int = size.width / 2
        val y: Int = (size.height * 0.8).toInt()
        val endY: Int = (size.height * 0.2).toInt()
        action.press(x, y).waitAction(timeOfSwipe).moveTo(x, endY).release().perform()
    }

    private fun swipeLeft(by: By, error: String) {
        val element = waitElement(by, error, 5)
        val leftX = element.location.getX()
        val rightX = leftX + element.size.getWidth()
        val upperY = element.location.getY()
        val lowerY = upperY + element.size.getHeight()
        val middle = (upperY + lowerY) / 2

        val action = TouchAction(driver)
        action.press(rightX, middle).waitAction(300).moveTo(leftX, middle).release().perform()
    }

    private fun swipeUpQuick() {
        swipeUp(200)
    }

    private fun swipeToElement(by: By, error: String, maxSwipes: Int) {
        var numberOfSwipe: Int = 0
        while (driver!!.findElements(by).size == 0) {
            if (numberOfSwipe > maxSwipes) {
                waitElement(by, "Cannt find element by swiping up" + error, 0)
                return
            }
            swipeUpQuick()
            numberOfSwipe++
        }
    }


}