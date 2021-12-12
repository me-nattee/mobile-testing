package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.touch.WaitOptions.waitOptions
import io.appium.java_client.touch.offset.PointOption
import org.junit.Assert
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.util.regex.Pattern

open class MainPageObject(open val driver: AppiumDriver<MobileElement>?) {

    open fun waitElement(by: By, error: String, time: Long): WebElement {
        var wait = WebDriverWait(driver, time)
        wait.withMessage(error + "\n")
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        )
    }

    open fun click(by: By, error: String, time: Long): WebElement {
        val element: WebElement = waitElement(by, error, time)
        element.click()
        return element
    }


    open fun sendKeys(by: By, text: String, error: String, time: Long): WebElement {
        val element: WebElement = waitElement(by, error, time)
        element.sendKeys(text)
        return element
    }


    open fun hasNotElement(by: By, error: String, time: Long): Boolean {
        var wait = WebDriverWait(driver, time)
        wait.withMessage(error + "\n")
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        )
    }

    open fun clear(by: By, error: String, time: Long): WebElement {
        val element: WebElement = waitElement(by, error, time)
        element.clear()
        return element
    }

    open fun hasText(by: By, text: String, error: String) {
        val element: WebElement = waitElement(by, error, 10)
        val actualText = element.getAttribute("text")

        Assert.assertEquals("We see unexpected text", text, actualText)
    }

    open fun hasTitleOfResult(by: By, title: String, error: String) {
        val result = waitElement(by, error, 10)
                .getAttribute("text")

        Assert.assertEquals("We see unexpected title", title, result)
    }

    open fun hasResults(by: By, error: String) {
        waitElement(by, error, 5).isDisplayed
    }

    open fun resultsHasTitle(by: By, title: String) {
        val results: List<WebElement> = driver!!.findElements(by)
        for (result in results) {
            Assert.assertTrue("Results don't contain a title", result.getAttribute("text").contains(title))
        }
    }

    open fun swipeUp(timeOfSwipe: Int) {
        val action = PlatformTouchAction(driver as AppiumDriver)
        val size: Dimension = driver!!.manage().window().size
        val x: Int = size.width / 2
        val y: Int = (size.height * 0.8).toInt()
        val endY: Int = (size.height * 0.2).toInt()
        action.press(PointOption.point(x, y))
                .waitAction(waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(x, endY)).release().perform()
    }

    open fun swipeLeft(by: By, error: String) {
        val element = waitElement(by, error, 5)
        val leftX = element.location.getX()
        val rightX = leftX + element.size.getWidth()
        val upperY = element.location.getY()
        val lowerY = upperY + element.size.getHeight()
        val middle = (upperY + lowerY) / 2

        val action = PlatformTouchAction(driver as AppiumDriver)
        action.press(PointOption.point(rightX, middle)).waitAction(waitOptions(Duration.ofMillis(300)))
                .moveTo(PointOption.point(leftX, middle)).release().perform()
    }

    open fun swipeUpQuick() {
        swipeUp(200)
    }

    open fun swipeToElement(by: By, error: String, maxSwipes: Int) {
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

    @Throws(AssertionError::class)
    open fun getNotElement(by: By, error: String) {
        val amountOfElements = getAmountOfElements(by)
        if (amountOfElements > 0) {
            val defaultMessage = "An element + ${by.toString()} suppoused to be not present" // как добавить исключение? (defaultMessage + error)
            throw AssertionError("$defaultMessage $error")
        }
    }

    open fun getAmountOfElements(by: By): Int {
        val elements: List<WebElement> = driver!!.findElements(by)
        return elements.size
    }

    open fun getAttribute(by: By, attribute: String, error: String, timeout: Long): String {
        val element: WebElement = waitElement(by, error, timeout)
        return element.getAttribute(attribute)
    }

    fun getLocatorString(locator_with_type: String): By {
        val exploaded_locator: List<String> = locator_with_type.split(Pattern.quote(":"), ignoreCase = false, limit = 2)
        val by_type: String = exploaded_locator[0]
        val locator: String = exploaded_locator[1]

        if (by_type.equals("xpath")) {
            return By.xpath(locator)
        } else if (by_type.equals("id")) {
            return By.id(locator)
        } else {
            throw IllegalAccessError("Cannot get type of locator")
        }
    }

}