package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.ios.IOSDriver
import junit.framework.TestCase
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

open class IOSTestCase : TestCase() {
    var driver: AppiumDriver<MobileElement>? = null
    var url = "http://127.0.0.1:4723/wd/hub"

    override fun setUp() {
        super.setUp()
        this.driver

        val capabilities = DesiredCapabilities()
        capabilities.setCapability("platformName", "iOS")
        capabilities.setCapability("deviceName", "iPhone 13")
        capabilities.setCapability("platformVersion", "15.0")
        capabilities.setCapability("app", "/Users/nshakh/Desktop/Apps/Wikipedia.app")

        driver = IOSDriver(URL(url), capabilities)
        driver!!.rotate(ScreenOrientation.PORTRAIT)
    }

    @Throws(Exception::class)
    override fun tearDown() {
        super.tearDown()

        driver?.quit() ?: throw Exception("Driver instance was unable to quit.")
    }
}