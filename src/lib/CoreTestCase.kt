package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import junit.framework.TestCase
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

open class CoreTestCase : TestCase() {
    var driver: AppiumDriver<MobileElement>? = null
    var url = "http://127.0.0.1:4723/wd/hub"

    override fun setUp() {
        super.setUp()
        this.driver

        val capabilities = DesiredCapabilities()
        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability("deviceName", "AndroidTestDevice")
        capabilities.setCapability("automationName", "Appium")
        capabilities.setCapability("platformVersion", "8.0")
        capabilities.setCapability("appPackage", "org.wikipedia")
        capabilities.setCapability("appActivity", ".main.MainActivity")
        capabilities.setCapability("app", "/Users/nshakh/Desktop/Apps/org.wikipedia.apk")

        driver = AndroidDriver(URL(url), capabilities)
        driver!!.rotate(ScreenOrientation.PORTRAIT)
    }

    @Throws(Exception::class)
    override fun tearDown() {
        super.tearDown()

        driver?.quit() ?: throw Exception("Driver instance was unable to quit.")
    }
}