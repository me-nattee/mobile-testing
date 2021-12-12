package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import junit.framework.TestCase
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

open class CoreTestCase : TestCase() {
    var driver: AppiumDriver<MobileElement>? = this.getDriverForPlatform()
    var url = "http://127.0.0.1:4723/wd/hub"
    var ANDROID = "android"
    var IOS = "ios"

    override fun setUp() {
        super.setUp()
        this.driver

        val capabilities: DesiredCapabilities = this.getCapabilitiesByPlatformEnv()
        driver = AndroidDriver(URL(url), capabilities)
        driver!!.rotate(ScreenOrientation.PORTRAIT)
    }

    @Throws(Exception::class)
    override fun tearDown() {
        super.tearDown()

        driver?.quit() ?: throw Exception("Driver instance was unable to quit.")
    }

    private fun getCapabilitiesByPlatformEnv(): DesiredCapabilities {
        val platform = System.getenv("PLATFORM")
        val capabilities = DesiredCapabilities()

        if (platform.equals(ANDROID)) {
            capabilities.setCapability("platformName", "Android")
            capabilities.setCapability("deviceName", "AndroidTestDevice")
            capabilities.setCapability("automationName", "Appium")
            capabilities.setCapability("platformVersion", "8.0")
            capabilities.setCapability("appPackage", "org.wikipedia")
            capabilities.setCapability("appActivity", ".main.MainActivity")
            capabilities.setCapability("app", "/Users/nshakh/Desktop/Apps/org.wikipedia.apk")
        } else if (platform.equals(IOS)) {
            capabilities.setCapability("platformName", "iOS")
            capabilities.setCapability("deviceName", "iPhone 13")
            capabilities.setCapability("platformVersion", "15.0")
            capabilities.setCapability("app", "/Users/nshakh/Desktop/Apps/Wikipedia.app")
        } else {
            throw Exception("Cannot get run platform from env")
        }
        return capabilities
    }

    private fun getDriverForPlatform(): AppiumDriver<MobileElement>? {
        val platform = System.getenv("PLATFORM")
        val capabilities = DesiredCapabilities()

        if (platform.equals(ANDROID)) {
            driver = AndroidDriver(URL(url), capabilities)
        } else if (platform.equals(IOS)) {
            driver = IOSDriver(URL(url), capabilities)
        } else {
            throw Exception("Cannot get driver")
        }
        return driver
    }
}