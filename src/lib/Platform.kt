package lib

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

open class Platform {
    var ANDROID = "android"
    var IOS = "ios"
    var urlAppium = "http://127.0.0.1:4723/wd/hub"

    open fun getDriver() {
        val url: URL = URL(urlAppium)
        if (isAndroid()) {
            return AndroidDriver(url, this.getAndroidDesiredCapabilities())
        } else if (isIos()) {
            return IOSDriver(url, this.getIosDesiredCapabilities())
        } else {
            throw Exception("Cannot detect type of driver")
        }
    }

    open fun isAndroid(): Boolean {
        return isPlatform(ANDROID)
    }

    open fun isIos(): Boolean {
        return isPlatform(IOS)
    }

    private fun getAndroidDesiredCapabilities(): DesiredCapabilities {
        val capabilities = DesiredCapabilities()

        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability("deviceName", "AndroidTestDevice")
        capabilities.setCapability("automationName", "Appium")
        capabilities.setCapability("platformVersion", "8.0")
        capabilities.setCapability("appPackage", "org.wikipedia")
        capabilities.setCapability("appActivity", ".main.MainActivity")
        capabilities.setCapability("app", "/Users/nshakh/Desktop/Apps/org.wikipedia.apk")

        return capabilities
    }

    private fun getIosDesiredCapabilities(): DesiredCapabilities {
        val capabilities = DesiredCapabilities()

        capabilities.setCapability("platformName", "iOS")
        capabilities.setCapability("deviceName", "iPhone 11")
        capabilities.setCapability("platformVersion", "15.0")
        capabilities.setCapability("app", "/Users/nshakh/Desktop/Wikipedia.app")

        return capabilities
    }

    private fun getPlatformVar(): String {
        return System.getenv("PLATFORM")
    }

    private fun isPlatform(my_platform: String): Boolean {
        val platform = this.getPlatformVar()
        return my_platform == platform
    }
}